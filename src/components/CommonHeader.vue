<template>
    <el-header>
              <el-row :gutter="20">
                  <el-col :span="2" style="text-align: right;">
                      <img src="../assets/kon.png" class="logo" alt="交易平台" />
                  </el-col>
                  <el-col :span="4" class="col1">
                      weGo
                  </el-col>
                  <el-col :span="12" class="col1">
                    <div class="search-bar">  
                        <input  
                            type="text"  
                            placeholder="搜索..."  
                            v-model="searchQuery"  
                            class="search-input"
                            @keydown.enter="performSearch"  
                        />  
                        <button class="search-button" @click="performSearch">搜索</button>  
                    </div> 
                  </el-col>
                  <el-col :span="4">
                    <el-dropdown>
                      <span class="el-dropdown-link" >
                          <img class="user" :src="getImgSrc()" alt="头像" />
                          <!-- 动态绑定用户名 -->
                          <span class="user">{{userStore.username}}</span>
                      </span>
                      <template #dropdown>
                          <el-dropdown-menu>
                          <el-dropdown-item @click="goprofile">个人中心</el-dropdown-item>
                          <el-dropdown-item @click="changeAccount">切换账号</el-dropdown-item>
                          <el-dropdown-item divided @click="Logout">退出登录</el-dropdown-item>
                          </el-dropdown-menu>
                      </template>
                    </el-dropdown>
                  </el-col>
              </el-row>
          </el-header>  
</template>


<script lang="ts" setup>
import{ ref, onMounted, watch } from 'vue';
import PubSub from 'pubsub-js';
import router from "~/router";
import { ElMessage, ElMessageBox } from "element-plus";
import { useUserStore } from "~/store/user";
const userStore = useUserStore();
const img = ref(userStore.image);
const searchQuery = ref('')    
const getImgSrc = () => {  
    // console.log("当前图片URL:", img.value); // 添加调试信息  
    if (img.value === null || img.value === '')  
        return new URL("~/assets/0002.jpg", import.meta.url).href;  
    else return img.value;  
};

// 监测头像的变化  
watch(() => userStore.image, (newValue) => {  
    img.value = newValue;  
});

//   const menuWidth = ref("200px");
//   onMounted(()=>{
//       //消费者 订阅消息
//       PubSub.subscribe('menuopenorclose', (topic: String, data: any) => {
//           menuWidth.value = data ==true ? "64px":"200px"
//       })
//   })
const performSearch = () => {  
    if (searchQuery.value.trim() !== '') {  
        // 跳转到搜索结果页面，并传递查询参数  
        router.push({ name: 'search', query: { q: searchQuery.value } });  
    } else {  
        // 可以显示一个提示，告知用户请输入搜索词  
        ElMessage.error('请输入搜索词');  
    }  
}; 
  
const Logout = ()=> {
      ElMessageBox.confirm('确认退出吗, 是否继续?', '确认退出', {
        confirmButtonText: '退出',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async ()=>{
          await userStore.LogOut()
          .then(res=>{
              if(res.code === 1) {
                  router.push('/login')
              } else {
                  ElMessage.error("退出失败")
              }
          })
          .catch(error=>{
              ElMessage.error("退出失败："+error.message)
          })
      })
  }
  
const goprofile = () =>{
    router.push({name: 'profile'})
}

let flag = 1; // flag记住当前用户  
const user1 = { username: "john_doe", password: "123456" };  
const user2 = { username: "admin", password: "123456" };  

const changeAccount = async () => {  
    try {  
        // 根据flag决定使用哪个用户  
        const currentUser = flag === 1 ? user1 : user2;  
        const response = await userStore.Login(currentUser);  

        if (response.code === 1) {  
            // 切换用户后更新flag  
            flag = flag === 1 ? 2 : 1; // toggle flag between 1 and 2  
            router.push("/index");  
        }  
    } catch (error) {  
        ElMessage.error('请求错误：' + (error as Error).message);  
    }  
};

 </script>
  
<style lang="less" scoped>
.el-row {
    align-items: center;
    vertical-align: middle;
    padding-top: 10px;
}
.el-header {
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */  
}
.logo {
    width: 40px;
    height: 40px;
    border-radius: 50%;
//   margin-right: 10px;
}
.col1 {
    color: black;
}
.userinfo {
    text-align: right;
}

.el-dropdown-link {
    color: black;
    .user {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
            // line-height: 40px;
            vertical-align:middle;
        }
}

.search-bar {  
    display: flex;  
    justify-content: center;  
    margin-bottom: 10px;  
}  
    
.search-input {  
    padding: 6px;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    width: 350px;  
    font-size: 16px;  
}  

.search-button {  
    padding: 8px 12px;  
    background-color: transparent;  
    color: black;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    cursor: pointer;  
    margin-left: 5px;  
    transition: background-color 0.3s;  
}  

.search-button:hover {  
    background-color: rgba(0, 0, 0, 0.1);  
}

/* 鼠标悬浮效果样式 */  
.user.hover {  
    border: 1px solid #007BFF;  
    filter: brightness(1.2);  
} 

</style>