<template>  
  <div class="user-container">  
    <h2 class="title">个人资料</h2>  
    <div  
      class="description"  
      :contenteditable="editing"  
      @input="onDescriptionChange"  
      :class="{ editable: editing }"  
      v-text="userData.description"  
    ></div> 

    <div class="user-content">  
      <div class="avatar">  
        <img :src="userData.image" alt="用户头像" @click="editing ?dialogFormVisible = true:false"/>  
        <el-dialog v-model="dialogFormVisible" title="修改头像" width="550">
          <upload-image 
              @imageChange="imageChange" style="margin-right: 10px;">
          </upload-image> 
          <span style=" color:grey">图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片 </span>
        </el-dialog>
      </div>  

      <div class="user-info">  
        <div class="form-group">  
          <label for="username">用户名:</label>  
          <input v-model="userData.username" type="text" id="username" :disabled="!editing" />  
        </div>  

        <div class="form-group">  
          <label>性别:</label>  
          <select v-model="userData.sex" :disabled="!editing">  
            <option value="male">男</option>  
            <option value="female">女</option>  
            <option value="other">其他</option>  
          </select>  
        </div>  

        <div class="form-group">  
          <label for="school">学校:</label>  
          <input v-model="userData.school" type="text" id="school" :disabled="!editing" />  
        </div>  
      </div>  

      <div class="right-info">  
        <div class="form-group">  
          <label for="email">邮箱:</label>  
          <input v-model="userData.email" type="email" id="email" :disabled="!editing" />  
        </div>  

        <div class="form-group">  
          <label for="password">密码:</label>  
          <input v-model="userData.password" type="password" id="password" :disabled="!editing" />  
        </div>  
      </div>  
    </div>  

    <div class="button-group">  
      <button type="button" @click="onEdit" class="btn">{{ editing ? '取消' : '修改' }}</button>  
      <button type="submit" v-if="editing" @click="saveuser" class="btn primary">保存</button>  
    </div>  
  </div>  
</template>  

<script lang="ts" setup>  
import { ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';  
import { editUserData, getUserData } from '~/api/users';
import { useUserStore } from "~/store/user";
const userStore = useUserStore();
const userId = userStore.userId; 
const dialogFormVisible = ref(false);
const editing = ref(false);  
const userData = reactive({  
  username: '',  
  image: '', // 默认头像  
  password: '',  
  email: '',  
  sex: '',  
  description: '这是一个描述信息，您可以根据需要进行编辑。',  
  school: '',  
});  

  const onEdit = () => {  
    editing.value = !editing.value;  
  };   

  const saveuser = async () => {  
    // const dataToSend = { userId, ...userData.value };  
    await editUserData(userId,userData)
    .then(res=>{
      if(res.data.code === 1) {
        ElMessage('用户信息更新成功:')
      }
    }).catch(error=>{
      ElMessage.error(error);
    }) 
    editing.value = false; // 保存后返回非编辑状态  
  };  

  const onDescriptionChange = (event: Event) => {  
    const target = event.target as HTMLDivElement;  
    userData.description = target.innerText;  // 更新描述  
  };  
  const imageChange = (imageUrl: any) =>{
    userData.image = imageUrl; // 更新头像
  };

  onMounted(async () => {  
    try {  
        const res = await getUserData(userId)
        Object.assign(userData,{ ...res.data.data })
    } catch (error) {  
        console.error('获取用户数据失败:', error);  
    }  
  });
</script>  

<style scoped>  
.user-container { 
  height: 600px; /* 设置聊天框的高度 */  
  margin: auto;  
  padding: 20px;  
  border: 1px solid #ddd;  
  border-radius: 8px;  
  background-color: #ffffff;  
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);  
}  

.title {  
  text-align: center;  
  font-size: 24px;  
  margin: 20 0; 
  color: #333;  
}  

.description {  
  text-align: center;  
  margin: 40px 0;  
  color: #666;  
  border: none; /* 没有边框 */  
  outline: none; /* 去掉点击后的轮廓 */  
  padding: 5px; /* 添加一些填充 */  
  cursor: text; /* 文本光标 */  
}  

.description.editable {  
  border: 1px solid #007bff;   
  background-color: #e8f1fa;   
  border-radius: 6px;   
  padding: 6px;   
  transition: all 0.3s ease;   
}  

.description.editable:focus {  
  outline: none;   
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.5);  
}  

.user-content {
  margin-top: 50px;  
  display: flex; /* 使用 Flexbox 布局 */  
  justify-content: space-between; /* 设置内容之间的间距 */
  align-items: center;   
}  

.avatar,  
.user-info,  
.right-info {  
  flex: 1; /* 使所有列占据相同的空间 */  
  margin-right: 20px; /* 右侧间距 */  
}  

.avatar { 
  display: flex;  
  flex-direction: column;  
  align-items: center;  
 
}  

.avatar img {  
  width: 200px;  
  height: 200px;  
  border-radius: 50%;  
  object-fit: cover;  
  margin-bottom: 10px;  
}  

.form-group {  
  margin-bottom: 15px;  
}  

label {  
  font-weight: bold;  
  margin-bottom: 5px;  
  display: block;  
}  

input[type='text'],  
input[type='email'],  
input[type='password'],  
textarea,  
select {  
  width: 100%;  
  max-width: 300px;  
  padding: 8px;  
  border: 1px solid #ccc;  
  border-radius: 4px;  
  font-size: 16px;  
}  

.button-group {  
  display: flex;  
  justify-content: flex-end;  
  margin-top: 50px; /* 设置按钮组的顶部间距 */  
  margin-right: 250px;
}  

.btn {  
  padding: 10px 15px;  
  border: none;  
  border-radius: 4px;  
  cursor: pointer;  
  margin-left: 5px;  
}  

.btn.primary {  
  background-color: #007bff;  
  color: white;  
}  

.btn:hover {  
  opacity: 0.9;  
}  
</style>