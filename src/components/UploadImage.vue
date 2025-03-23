<template>  
  <el-upload  
    class="avatar-uploader"  
    action="/api/common/upload"   
    :show-file-list="false"  
    :on-success="handleAvatarSuccess"  
    :before-upload="beforeAvatarUpload"  
    :data="uploadData"
    :headers="{ token: userStore.token }"    
  >  
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />  
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>  
  </el-upload>  
</template>  

<script lang="ts" setup>  
import { ref } from 'vue'  
import { ElMessage } from 'element-plus'  
import { Plus } from '@element-plus/icons-vue'  
import type { UploadProps } from 'element-plus'
import { useUserStore } from "~/store/user";
const userStore = useUserStore();


const emit = defineEmits(['imageChange']); // 定义 `imageChange` 事件  

const imageUrl = ref('')  
const uploadData = ref({})  // 上传数据  

const handleAvatarSuccess: UploadProps['onSuccess'] = (response) => {  
  // 这里可以处理成功后的逻辑  
  imageUrl.value = response.data; // 接口返回结构调整 
  emit('imageChange', imageUrl.value); // 触发事件，将新图片 URL 传递到父组件  
}  

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {  
  const isJpgOrPng = rawFile.type === 'image/jpeg' || rawFile.type === 'image/png';  
  if (!isJpgOrPng) {  
    ElMessage.error('图片必须是 JPG 或 PNG 格式！');  
    return false;  
  } else if (rawFile.size / 1024 / 1024 > 2) {  
    ElMessage.error('图片大小不能超过 2MB！')  
    return false;  
  }  
  return true;  
}  
</script>  

<style scoped>  
.avatar-uploader .avatar {  
  width: 178px;  
  height: 178px;  
  display: block;  
}  
</style>  

<style>  
.avatar-uploader .el-upload {  
  border: 1px dashed var(--el-border-color);  
  border-radius: 6px;  
  cursor: pointer;  
  position: relative;  
  overflow: hidden;  
  transition: var(--el-transition-duration-fast);  
}  

.avatar-uploader .el-upload:hover {  
  border-color: var(--el-color-primary);  
}  

.el-icon.avatar-uploader-icon {  
  font-size: 28px;  
  color: #8c939d;  
  width: 178px;  
  height: 178px;  
  text-align: center;  
}  
</style>