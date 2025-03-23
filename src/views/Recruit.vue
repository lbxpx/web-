<template>  
    <div class="project-page">  
        <CommonHeader />  
        <div class="content" style="margin-top: 20px;">  
            <h1>发布招募</h1>  
            <div class="input-group">  
                <label for="title">标题:</label>  
                <input id="title" v-model="project.title" placeholder="请输入项目标题" class="input-field" />  
            </div>  
            <div class="category-section input-group">  
                <label for="category">选择分类:</label>  
                <el-select id="category" v-model="selectedCategories" class="select-field" multiple filterable clearable>  
                    <el-option v-for="tag in tags" :key="tag.id" :value="tag.id" :label="tag.name"></el-option>  
                </el-select>  
            </div>
            <div class="upload-image-container">
                <upload-image 
                          @imageChange="imageChange" style="margin-right: 10px;">
                </upload-image>
                <div class="avatar-uploader-tip">
                + 添加优质首图更吸引人~
                </div>
            </div>
            <div class="input-group">  
                <label for="description">内容详情:</label>  
                <textarea id="description" v-model="project.description" placeholder="请输入内容详情、活动官方链接等信息" class="textarea-despField"></textarea> 
            </div>  
            <button @click="submitProject">发布招募</button>  
        </div>  
    </div>  
</template>  

<script setup lang="ts">  
import { ElMessage } from 'element-plus';
import { marked } from 'marked';
import { ref, onMounted } from 'vue';   
import { addProject, fetchTagList } from '~/api/recruit';
import router from '~/router';
import { useUserStore } from "~/store/user";
const userStore = useUserStore();
const userId = userStore.userId;
const username = userStore.username;

interface ProjectItem {  
    title: string;  
    description: string;  
    publisherName: string; // 用于存储从用户获取的用户名  
    image:string;
    link:string;
    userId:Number;
}  

// 存储项目数据  
const project = ref<ProjectItem>({  
    title: '',  
    description: '',  
    publisherName: '', // 在此处设置为当前用户的用户名 
    image:'',
    link:'',
    userId
});  
const imageChange = (imageUrl: any) =>{
    project.value.image = imageUrl; // 更新 userData.image  
}
// 存储当前用户的用户名，可以根据实际情况从用户验证中获取  
project.value.publisherName = username; // 替换为实际的用户名获取逻辑 
project.value.userId = userId; 

// 存储选择的分类  
const selectedCategories = ref<Number[]>([]);  

// 存储从后端获取的分类标签  
const tags = ref<{ id: number; name: string }[]>([]);  

// 获取标签的函数  
const fetchTags = async () => {  
    try {  
        const response = await fetchTagList(); // 替换为你的标签 API 地址  
        tags.value = response.data.data; // 假设返回的数据是一个标签数组  
    } catch (error) {  
        console.error('无法获取标签:', error);  
    }  
};  

// 提交项目的函数  
const submitProject = async () => { 
    const rawContent = project.value.description;

    // Crucial: Sanitize the markdown before sending
    const sanitizedContent = marked(rawContent);

    if(sanitizedContent === '' || sanitizedContent === undefined) {
        ElMessage({ message: '无法解析Markdown', type: 'error' });
        return;
    } 
    try {  
        const payload = {  
            title: project.value.title,
            description: sanitizedContent,
            publisherName: project.value.publisherName,
            image: project.value.image,
            userId: project.value.userId,  
            categoryIds: selectedCategories.value,  
        };  
        await addProject(payload) // 替换为你的项目发布 API 地址
        .then(res=>{
            if(res.data.code===1){
                alert('招募成功发布！');
                router.push('/index'); 
            }
        })  
    } catch (error) {  
        console.error('发布失败:', error);  
        alert('发布失败，请重试。');  
    }  
};  

// 当组件挂载时获取标签列表  
onMounted(() => {  
    fetchTags();  
});  
</script>  

<style scoped>  
.project-page {  
    background-image: url('../assets/img/nake.jpg');  
    background-size: cover;  
    background-attachment: fixed;  
    min-height: 100vh;  
    padding: 20px;  
}  

.content {  
    display: flex;  
    flex-direction: column; /* 设为列方向以适应表单布局 */  
    justify-content: flex-start;  
    max-width: 1380px; /* 最大宽度限制 */  
    margin: 0 auto; /* 水平居中 */  
    gap: 15px; /* 表单项间距 */  
    background: rgba(255, 255, 255, 0.9); /* 半透明背景让输入内容更清晰 */  
    padding: 20px; /* 内边距 */  
    border-radius: 8px; /* 圆角 */  
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 阴影效果 */  
}  

.input-group {  
    display: flex;  
    flex-direction: column; /* 让标签和输入框垂直排列 */  
}  

.input-field {  
    width: 50%;  
    padding: 12px;  
    margin-top: 8px; /* 标签和输入框上边距 */  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    font-size: 1em; /* 字体大小 */  
}  

.select-field {  
    width: 25%;  
    padding: 12px;  
    margin-top: 8px; /* 标签和选择框上边距 */  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    font-size: 1em; /* 字体大小 */  
}  

.textarea-despField {  
    width: 90%;  
    padding: 12px;  
    margin-top: 8px; /* 标签和文本区域上边距 */  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    font-size: 1em; /* 字体大小 */  
    min-height: 200px; /* 设置文本区域的最小高度 */  
}  

.textarea-linkField {  
    width: 90%;  
    padding: 12px;  
    margin-top: 8px; /* 标签和文本区域上边距 */  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    font-size: 1em; /* 字体大小 */  
    min-height: 20px; /* 设置文本区域的最小高度 */  
}  

button {  
    margin: 0 auto;  
    width: 100px;  
    background-color: #28a745; /* 发布按钮颜色 */  
    color: white;  
    padding: 10px;  
    border: none;  
    border-radius: 4px;  
    cursor: pointer;  
    font-size: 1em; /* 字体大小 */  
}  

button:hover {  
    background-color: #218838; /* 鼠标悬停时变色 */  
}  

.category-section {  
    display: flex;  
    flex-direction: column; /* 垂直对齐类和标签 */  
    gap: 10px; /* 间距 */  
}  
.upload-image-container {
  display: flex;
  margin-bottom: 10px; 
}

.avatar-uploader-tip {
  align-self: center;
  font-size: 15px;
  color: #8c939d;
}
</style>