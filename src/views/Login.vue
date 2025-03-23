<template>
  <div>
    <section id="img-one" class="img">
    <!-- 最外层的大盒子 -->
      <div class="box">
        <!-- 滑动盒子 -->
        <div class="pre-box"
        :style="{ transform: `translateX(${translateX}%)`, 
          backgroundColor: boxBackgroundColor }">
            <p>WELCOME</p>
            <p>JOIN US!</p>
            <div class="img-box">
                <img :src="loginImg" alt="">
            </div>
        </div>
        <!-- 注册盒子 -->
        <div class="register-form"  v-if="pageType == 1">
            <!-- 标题盒子 -->
            <div class="title-box">
              <span>注册</span>
            </div>
            <!-- 输入框盒子 -->
            <el-form
            ref="registerFormRef"
            :model="registerForm"
            :rules="rules"
            status-icon
            @submit.native.prevent>
                <el-form-item label="" prop="username">
                    <el-input type="text" placeholder="用户名" clearable :suffix-icon="User" v-model="registerForm.username"></el-input>
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input type="password" placeholder="密码" clearable :suffix-icon="Lock" show-password v-model="registerForm.password"></el-input>
                </el-form-item>
                <el-form-item label="" prop="confirmPassword">
                    <el-input type="password" placeholder="确认密码" clearable :suffix-icon="Lock" show-password v-model="registerForm.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item label="" prop="email">
                    <el-input  placeholder="请输入邮箱" maxlength="20" clearable v-model="registerForm.email"/>
                </el-form-item>
                <el-form-item label="" prop="verifyCode">
                    <el-input style="width: 129px;"  placeholder="请输入验证码" maxlength="6" clearable  v-model="registerForm.verifyCode"/>
                    <el-button size="small" type="primary" @click="GetVerifyCode(registerForm.email)" :disabled="sented">  
                        <span v-if="sented">{{ countdown }}秒后可重发</span>  
                        <span v-else>获取验证码</span> 
                    </el-button>
                </el-form-item>
            </el-form>
            <!-- 按钮盒子 -->
            <div class="btn-box">
                <el-button class="btn" type="primary" @click="Register" >注册</el-button>
                <!-- 绑定点击事件 -->
                <p @click="mySwitch()">已有账号?去登录</p>
            </div>
        </div>
        <!-- 忘记密码盒子 -->
        <div class="register-form"  v-if="pageType == 2">
            <!-- 标题盒子 -->
            <div class="title-box">
              <span>密码重置</span>
            </div>
            <!-- 输入框盒子 -->
            <el-form
            ref="registerFormRef"
            :model="registerForm"
            :rules="rules"
            status-icon
            @submit.native.prevent>
                <el-form-item label="" prop="username">
                    <el-input type="text" placeholder="用户名" clearable :suffix-icon="User" v-model="registerForm.username"></el-input>
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input type="password" placeholder="请输入新密码" clearable :suffix-icon="Lock" show-password v-model="registerForm.password"></el-input>
                </el-form-item>
                <el-form-item label="" prop="confirmPassword">
                    <el-input type="password" placeholder="请确认新密码" clearable :suffix-icon="Lock" show-password v-model="registerForm.confirmPassword"></el-input>
                </el-form-item>
                <el-form-item label="" prop="email">
                    <el-input  placeholder="请输入邮箱" maxlength="20" clearable v-model="registerForm.email"/>
                </el-form-item>
                <el-form-item label="" prop="verifyCode">
                    <el-input style="width: 129px;"  placeholder="请输入验证码" maxlength="6" clearable  v-model="registerForm.verifyCode"/>
                    <el-button size="small" type="primary" @click="GetVerifyCode(registerForm.email)" :disabled="sented">  
                        <span v-if="sented">{{ countdown }}秒后可重发</span>  
                        <span v-else>获取验证码</span> 
                    </el-button>
                </el-form-item>
            </el-form>
            <!-- 按钮盒子 -->
            <div class="btn-box">
                <el-button class="btn" type="primary"  @click="UpdatePassword" >修改密码</el-button>
                <!-- 绑定点击事件 -->
                <p @click="mySwitch()">记得密码?去登录</p>
            </div>
        </div>
        <!-- 登录盒子 -->
        <div class="login-form">
            <!-- 标题盒子 -->
            <div class="title-box">
                <span>登录</span>
            </div>
            <!-- 输入框盒子 -->
            <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="rules"
            status-icon
            @submit.native.prevent
            >
                <el-form-item label="" prop="username">
                    <el-input type="text" placeholder="用户名" clearable :suffix-icon="User" v-model="loginForm.username"></el-input>
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input type="password" placeholder="密码" clearable :suffix-icon="Lock" show-password v-model="loginForm.password"></el-input>
                </el-form-item>
                <el-form-item label="" prop="verifyCode">
                    <el-input style="width: 150px;"  placeholder="请输入验证码" maxlength="4" clearable  v-model="loginForm.verifyCode"/>
                    <img
                      @click="changeValiCode()"
                      style="margin-left: auto;"
                      referrerpolicy="no-referrer"
                      :src="img"
                    />
                </el-form-item>
            </el-form>
            <!-- 按钮盒子 -->
            <div class="btn-box">
                <el-button class="btn" type="primary" @click.native.prevent="Login" >
                  <span v-if="!loading">登录</span>
                  <span v-else>登录中...</span>
                </el-button>
                <!-- 绑定点击事件 -->
                 <div class="pageChange">
                  <p @click="(pageType = 1, mySwitch())">没有账号?去注册</p>
                  <p @click="(pageType = 2, mySwitch())">忘记密码</p>
                 </div>
            </div>
        </div>
        
      </div>
    </section>
    <section class="content-one">
      <div class="text-box">
          <div class="cnt">
          <h2>THE TIME</h2>
          <p>
              Time would heal almost all wounds . If your wounds have not been healed up , please wait for a short while.
              Life isn't about waiting for the storm to pass . it's about learning to dance in the rain.
          </p>
          <p>
              <a href="#" class="btn">TAKE <strong>CARE OF</strong><em>YOURSEFL</em></a>
          </p>
      </div>
      </div>
    </section>
    <section class="content-two">
        <div class="text-box">
            <div class="cnt">
            <h2>LIVING</h2>
            <p>
                Some of us get dipped in flat, some in satin, some in gloss. But every once in a while you find someone who"s iridescent, and when you do, nothing will ever compare.
                We accept both the good and the bad, andwithout uttering a sound, we continue living.
            </p>
            <p>
                <a href="#" class="btn">BEST <strong>WISHS</strong><em>FOR YOU</em></a>
            </p>
        </div>
        </div>
    </section>
    <section class="content-three">
        <div class="text-box">
            <div class="cnt">
            <h2>HAPPY ENDING</h2>
            <p>
                Nobody can go back and start a new beginning , but anyone can start today and make a new ending.
                The minute you think of giving up , think of the reason why you held on so long .
            </p>
            <p>
                <a href="#" class="btn">TAKE <strong>CARE OF</strong><em>YOURSEFL</em></a>
            </p>
        </div>
        </div>
    </section>
    <section id="img-two" class="img">
        <h1>THOUGHTS</h1>
    </section>
    <section id="footer">FOOTER</section>
  </div>
  
</template>

<script setup lang="ts">
import {User, Lock} from '@element-plus/icons-vue'
import utils from '~/utils/utils';
import { ref, computed, reactive, onBeforeUnmount, onMounted  } from 'vue';
import { ElMessage, FormInstance } from 'element-plus';
import { isEmail } from '~/utils/validate';
import { checkVerifyCode, getImageCode, getVerifyCode, register, testImageCode, updatePassword } from '~/api/users';
import {useUserStore} from '~/store/user'
import router from '~/router';
const userStore = useUserStore();
let loading = ref(false);
  // 定义响应式数据
  const translateX = ref(0); // 滑动盒子的位置
  const boxBackgroundColor = ref('#6d9f7a'); // 滑动盒子的背景颜色
  const imgSrc = ref("girl1.jpg"); // 图片的路径
  const isRegisteredFormVisible = ref(true); // 控制注册/登录表单的显示
  //计算属性，根据 imgSrc 动态获取 loginImg 的值
  const loginImg = computed(() => utils.getAssetsFile(imgSrc.value)); 
  const pageType = ref (1);
  // 切换函数
  const mySwitch = () => {
    if (translateX.value === 0) {
      translateX.value = 100; // 更新滑动盒子的位置
      boxBackgroundColor.value = '#c9e0ed'; // 更新背景颜色
      imgSrc.value = "girl2.jpg"; // 更新图片路径
    } else {
      translateX.value = 0;
      boxBackgroundColor.value = '#6d9f7a';
      imgSrc.value = "girl1.jpg";
    }
    isRegisteredFormVisible.value = !isRegisteredFormVisible.value; // 切换注册/登录表单的显示
  };

const img = ref('');
const valiCode= ref('');
const changeValiCode = async () =>{
  await getImageCode()
      .then(res =>{
        if(res.data.code===1){
          const data = res.data.data
          img.value = data.imgCode
          valiCode.value = data.identity
        }else{
          ElMessage("刷新失败")
        }
      }).catch(error=>{
        ElMessage.error("请求失败" + error.message)
      })
}

const loginForm = reactive({
  username: 'admin',
  password: '123456',
  verifyCode:'',
})
const loginFormRef = ref<FormInstance | null>(null);
const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword:'',
  verifyCode:'',
  email:'',
})
const registerFormRef = ref<FormInstance>()
    const checkEmail= (rule:any, value:any, callback:any) => {  
        if (!value) {  
            callback(new Error(`请填写 ${rule.field} 字段`)); // 使用 rule.field 生成多语言或动态错误消息  
        } else if (!isEmail(value)) {  
            callback(new Error(`请输入有效的 ${rule.field} 地址`)); // 动态生成错误消息  
        } else {  
            callback(); // 验证通过  
        }  
    }
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 15, message: '用户名长度应在2~5个字符之间', trigger: 'blur' },
  ],
  password: [
  { required: true, message: '请输入密码', trigger: 'blur' },
  { min: 6, message: '密码长度不应小于6个字符', trigger: 'blur' },
  ],
  confirmPassword: [  
        { required: true, message: '请确认密码', trigger: 'blur' },  
        { validator: (rule: any, value: string, callback: any) => {  
            if (value !== registerForm.password) {  
                callback(new Error('两次输入的密码不一致'));  
            } else {  
                callback();  
            }  
        }, trigger: 'blur' }  
    ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {validator: checkEmail,trigger: 'blur'}
  ],
})

const Register = async () => {
  try {
    // 验证表单  
    const isFormValid = await registerFormRef.value!.validate();  
    if (!isFormValid) {  
      return; // 如果表单无效，直接返回  
    } 
    
    // 检查验证码  
    const verifyResponse = await checkVerifyCode({  
      email: registerForm.email,  
      code: registerForm.verifyCode,  
    });

    if (verifyResponse.data.code !== 1) {  
      ElMessage(verifyResponse.data.msg);  
      return; // 如果验证码验证失败，返回  
    }  

    //注册
    const registerResponse  = await register({
      username: registerForm.username, 
      password: registerForm.password, 
      email: registerForm.email,
    });

    if( registerResponse.data.code === 1) {
      ElMessage("注册成功！");
      mySwitch();
    } else {  
      ElMessage(`注册请求失败：${registerResponse.data.msg}`);  
    }  
  } catch (error) {
    ElMessage(`请求失败：${(error as Error).message}`);
  }
}
const Login = async () => {  
    try {  
    // 验证表单  
    const valid = await loginFormRef.value!.validate();  
    if (!valid) {  
      return; // 如果表单无效，直接返回  
    }  

    // 检查验证码  
    const res = await testImageCode({ identity: valiCode.value, verifycode: loginForm.verifyCode });  
    if (res.data.code !== 1) {  
      ElMessage(res.data.msg);  
      return; // 如果验证码验证失败，返回  
    }  

    loading.value = true;  

    // 登录操作  
    const loginResponse = await userStore.Login({ username: loginForm.username, password: loginForm.password });  
    
    if (loginResponse.code === 1) {  
    //   登录成功，跳转到系统首页  
      router.push('/index');  
    } else {   
      loading.value = false;   
      ElMessage.error('登录失败，账号密码错误：', loginResponse.msg);  
    }  
  } catch (error) {  
    loading.value = false; // 在发生错误时确保loading状态被重置  
    ElMessage.error('请求错误：' + (error as Error).message);  
  }   
};

const backCode = ref('');
const countdown = ref(60); // 倒计时初始值  
const sented = ref(false); // 用于标记验证码是否已发送  
const timer = ref<ReturnType<typeof setInterval> | null>(null); // 定时器引用  

const GetVerifyCode = async (email: string) => {
  if (sented.value) return;
  if (isEmail(email)) {
    try {
      const res = await getVerifyCode({ email });
      if (res.data.code === 1) {
        ElMessage("验证码已成功发送，请注意查收！");
        backCode.value = res.data;
        sented.value = true; // 设置已发送状态  
        startCountdown(); // 开始倒计时 
      } else {
        ElMessage.error("验证码发送失败！");
      }
    } catch (error) {
      ElMessage.error("获取验证码失败：" + (error as Error).message);
    }
  } else {
    ElMessage.error("邮箱错误!");
  }
}

const startCountdown = () => {
  countdown.value = 60; // 重置倒计时  
  timer.value = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--; // 倒计时递减  
    } else {
      clearInterval(timer.value!); // 清除定时器  
      timer.value = null; // 重置定时器引用
      sented.value = false; // 复原状态  
    }
  }, 1000); // 每秒执行一次  
}

const UpdatePassword = async () => { 
  try {
    // 验证表单  
    const isFormValid = await registerFormRef.value!.validate();  
    if (!isFormValid) {  
      return; // 如果表单无效，直接返回  
    }  

    // 检查验证码  
    const verifyResponse = await checkVerifyCode({  
      email: registerForm.email,  
      code: registerForm.verifyCode,  
    });

    if (verifyResponse.data.code !== 1) {  
      ElMessage(verifyResponse.data.msg);  
      return; // 如果验证码验证失败，返回  
    }   

    // 更新密码  
    const updateResponse = await updatePassword({  
      username: registerForm.username,  
      password: registerForm.password,  
      email: registerForm.email,  
    });  

    if (updateResponse.data.code === 1) {  
      ElMessage("修改密码成功！");  
      // pageType.value = 1; // 更新页面状态  
    } else {  
      ElMessage(`修改密码请求失败：${updateResponse.data.msg}`);  
    }  
    
  } catch (error) {
    ElMessage(`请求失败：${(error as Error).message}`);
  }  
    
};

onBeforeUnmount(() => {
  if (timer.value) {
    clearInterval(timer.value);
    timer.value = null;
  }
});

onMounted(async ()=>{
  await changeValiCode();
})
</script>

<style scoped lang="scss">
* {
    /* 去除浏览器默认内外边距 */
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* 最外层的大盒子 */
.box {
    width: 750px;
    height: 450px;
    display: flex;
    /* 相对定位 */
    position: relative;
    z-index: 2;
    margin: auto;
    /* 设置圆角 */
    border-radius: 8px;
    /* 设置边框 */
    border: 1px solid rgba(255, 255, 255, .6);
    /* 设置盒子阴影 */
    box-shadow: 2px 1px 19px rgba(0, 0, 0, .1);
}

/* 滑动的盒子 */
.pre-box {
    /* 宽度为大盒子的一半 */
    width: 50%;
    /* width: var(--width); */
    height: 100%;
    /* 绝对定位 */
    position: absolute;
    /* 距离大盒子左侧为0 */
    left: 0;
    /* 距离大盒子顶部为0 */
    top: 0;
    z-index: 99;
    border-radius: 4px;
    background-color: #6d9f7a;
    box-shadow: 2px 1px 19px rgba(0, 0, 0, .1);
    /* 动画过渡，先加速再减速 */
    transition: 0.5s ease-in-out;
    display: flex;
    flex-direction: column;
}

/* 滑动盒子的文字 */
.pre-box p {
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 25px;
    margin-top: 20px;
    /* 禁止选中 */
    user-select: none;
    font-weight: bold;
    color: white;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
}

/* 图片盒子 */
.img-box {
    width: 200px;
    height: 200px;
    margin: 20px auto;
    /* 设置为圆形 */
    border-radius: 50%;
    /* 设置用户禁止选中 */
    user-select: none;
    overflow: hidden;
    box-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
}

/* 图片 */
.img-box img {
    width: 100%;
    transition: 0.5s;
}

/* 登录和注册盒子 */
.login-form,
.register-form {
    display: flex;
    flex-direction: column;
    flex: 1;
    height: 100%;
}

/* 标题盒子 */
.title-box {
    height: 100px;
    line-height: 100px;
    align-self: center;
    margin-bottom: 25px;
}

/* 标题 */
.title-box span {
    text-align: center;

    color: white;
    /* 禁止选中 */
    user-select: none;
    letter-spacing: 5px;
    font-size: 50px;
    text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);

}

/* 输入框盒子 */
.el-form {
    display: flex;
    /* 纵向布局 */
    flex-direction: column;
    /* 水平居中 */
    align-items: center;
}
.el-form-item{
    width: 65%;
    outline: none;
    // width: 60%;
    // height: 40px;
    margin-bottom: 20px;
    text-indent: 10px;
    // border: 1px solid #fff;
    // background-color: rgba(255, 255, 255, 0.3);
    // border-radius: 120px;
    /* 增加磨砂质感 */
    // backdrop-filter: blur(10px);
}
// /* 输入框 */
// input {
//     outline: none;
//     // width: 60%;
//     height: 40px;
//     margin-bottom: 20px;
//     text-indent: 10px;
//     border: 1px solid #fff;
//     background-color: rgba(255, 255, 255, 0.3);
//     border-radius: 120px;
//     /* 增加磨砂质感 */
//     backdrop-filter: blur(10px);
// }

// .el-form-item:focus {
//     /* 光标颜色 */
//     color: #b0cfe9;

// }

// /* 聚焦时隐藏文字 */
// input:focus::placeholder {
//     opacity: 0;
// }

/* 按钮盒子 */
.btn-box {
    display: flex;
    justify-content: center;
}

/* 按钮 */
button {
    width: 100px;
    height: 30px;
    margin: 0 7px;
    line-height: 30px;
    border: none;
    border-radius: 4px;
    background-color: #69b3f0;
    color: white;
}

/* 按钮悬停时 */
button:hover {
    /* 鼠标小手 */
    cursor: pointer;
    /* 透明度 */
    opacity: .8;
}

/* 按钮文字 */
.btn-box p {
    height: 30px;
    line-height: 30px;
    /* 禁止选中 */
    user-select: none;
    font-size: 14px;
    color: white;

}

.btn-box p:hover {
    cursor: pointer;
    border-bottom: 1px solid white;
}



section,
#footer {
    height: 100vh;
    position: relative;
    color: rgba(255, 255, 255, .8);
    font-size: 10vh;
    /* 设置字体 */
    font-family: 'modern_no._20regular',
        serif;
    /* 文字阴影 */
    text-shadow: 4px 4px rgb(46, 43, 43);
    /* 弹性布局 */
    display: flex;
    /* 水平居中 */
    justify-content: center;
    /* 垂直居中 */
    align-items: center;
}

#footer {
    background-color: #c0b5aa;
    height: 50vh;
    color: #fff;
    font-size: 20vh;
}

.img {
    overflow: hidden;
    /* 背景图片不重复 */
    background-repeat: no-repeat;
    /* 背景图片位置：居中 */
    background-position: center;
    /* 背景图片固定 */
    background-attachment: fixed;
    background-size: cover;
    /* 禁止用户误选中 */
    user-select: none;

}


#img-one {
    background-image: url(~/assets/img/img2.jpg);
    background-size: cover;
}

#img-two {
    background-image: url(~/assets/img/img4.jpg);
}

.content-one {
    background: url(~/assets/img/img1.jpg) no-repeat fixed center;
    background-size: cover;
}

.content-two {
    background: url(~/assets/img/img2.jpg) no-repeat fixed center;
    background-size: cover;
}

.content-three {
    background: url(~/assets/img/img2.jpg) no-repeat fixed center;
    background-size: cover;
}

.text-box {
    width: 100%;
    height: 50%;
    /* 绝对定位 */
    position: absolute;
    bottom: 25%;
    /* 文字居中 */
    text-align: center;
    background-color: rgba(0, 0, 0, .3);
    /* 旋转中心 */
    transform-origin: center bottom;
    /* 扭曲斜切 */
    transform: skew(0, -8deg);

}

.text-box .cnt {
    display: inline-block;
    text-align: right;
    margin: 40px 0 10px;
    width: 90%;
    max-width: 990px;
    transform-origin: right bottom;
    transform: skew(0, 8deg);
}

.cnt h2 {
    margin-top: 100px;
    font-size: 36px;
    font-family: 'modern_no._20regular',
        serif;
    text-shadow: 1px 0px 0px #323232;
    letter-spacing: 0.3em;
    /* 大写字母 */
    text-transform: uppercase;
    color: #fff;
    margin-bottom: 5px;
}

.cnt p {
    font-size: 20px;
    line-height: 1.7;
    margin-bottom: 10px;
    color: #fff;
}

.cnt a.btn {
    font-size: 90%;
    font-family: 'modern_no._20regular',
        serif;
    letter-spacing: 0.3em;
    text-shadow: 1px 0px 0px black;
    line-height: 2.8;
    display: inline-block;
    background-color: #eaeaea;
    color: #000;
    border: 3px solid #000;
    white-space: nowrap;
    text-decoration: none;
    padding: 0 20px;
}

.pageChange {
  transform: translateY(-20%);
  display: flex;
  flex-direction: column;
  justify-content: start;
}
</style>