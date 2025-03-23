<template>  
    <div class="project-page">  
        <CommonHeader />  
        <div class="content" style="margin-top: 20px;">   
            <h1 class="project-title">{{ project?.title }}</h1>  
            <div class="project-meta">  
                <span class="publisher">{{ project?.publisherName }}</span>
                <div>
                    <span v-for="category in project?.categories" :key="category.id" class="categories-tag">{{ category.name }}  </span> 
                </div>      
            </div>  
            <div class="project-detail">
                <div v-html="compiledContentWithLinks"></div> <!--此部分用于显示解析后的 Markdown --> 
                
            </div>

            <div v-if="project?.image" >  
                <img :src="project.image" alt="Project Image" class="project-image"/>  
            </div> 
        </div> 
        <div class="status-container" v-if="project?.status">  
            <p class="status-text">{{ project?.status }}</p>  
        </div>
        <div class="buttons" v-if="status">   
            <button class="apply-button"  @click="showModal = true" v-if="project?.status=='rejected'">重新申请</button>
            <button class="apply-button"  @click="showModal = true" v-if="!project?.status">申请加入</button>   
        </div>
        <el-dialog v-model="showModal" title="确认加入" @close="resetForm">  
            <div>  
                <p>确认加入 {{ project?.title }} 吗？</p>  
                <el-input v-model="joiningReason" placeholder="请输入加入理由" style="margin-bottom: 15px; border: 1px solid #dcdfe6; border-radius: 4px;" ></el-input>  
                <el-input v-model="userStrength" placeholder="请输入自身优势" style="margin-bottom: 15px; border: 1px solid #dcdfe6; border-radius: 4px;"></el-input>  
            </div>  
            <span slot="footer" class="dialog-footer">  
                <el-button @click="resetForm">取消</el-button>  
                <el-button type="primary" @click="applyForProject">加入</el-button>  
            </span>  
        </el-dialog>  
    </div>  
</template>  

<script setup lang="ts">  
import { ElMessage } from 'element-plus';
import { marked } from 'marked';
import { computed, onMounted, ref } from 'vue';  
import { useRoute } from 'vue-router';  
import { applyToJoinProject, getProjectDetail } from '~/api/join';
import { useUserStore } from "~/store/user";
const userStore = useUserStore();
const userId = userStore.userId;

const route = useRoute();  
const projectId = route.params.id; // 或者是 route.query.id  
const showModal = ref(false);  
const joiningReason = ref('');  
const userStrength = ref('');
interface Category {  
    id: number;  
    name: string;  
}
interface projectItem {  
    title: string;  
    description: string;  
    publisherName: string;  
    image:string;

    categories: [Category]; // 使用 string[] 以支持多个分类标签
    publisherId: Number;
    status:string;  
}   
const project = ref<projectItem>(); // 初始化为 null  

const status=ref(true);

// 获取项目详细信息的方法  
const fetchProjectDetails = async () => {  
    await getProjectDetail(projectId,userId)
    .then(res=>{
        if(res.data.code===1){
            project.value=res.data.data;
            if(project.value?.publisherId===userId){
                status.value=false;
            }
        }else{
            ElMessage(res.data.msg)
        }
    })
    .catch(error=>{
        ElMessage.error("请求出错了："+error.message);
    }) 
};  
const compiledContent = computed(() => {  
    if (project.value?.description) {
    return marked(project.value.description);
    } else {
        return '无详细信息'; // or some other default value, like "No description provided"
    } // 将 Markdown 内容转换为 HTML  
});

// 在组件挂载时调用获取数据的方法  
onMounted(() => {  
    fetchProjectDetails();  
}); 

const compiledContentWithLinks = computed(() => {
  if (!project.value?.description) {
    return '无详细信息';
  }
  let htmlContent = project.value.description;
  const urlPattern = /(https?:\/\/[^\s]+)/g;
  htmlContent = htmlContent.replace(urlPattern, (url: string) => {
    const encodedUrl = encodeURIComponent(url);
    return `<a href="${encodedUrl}" target="_blank" rel="noopener noreferrer">${url}</a>`;
  });
  return htmlContent;
});

const applyForProject = async () => {  
    // 这里可以添加申请加入的逻辑
    await applyToJoinProject({senderId:userId, reason:joiningReason.value, strengths:userStrength.value,projectId:Number(projectId),receiverId:Number(project.value?.publisherId)})
    .then(res=>{
        if(res.data.code===1){
            resetForm() ;
            ElMessage.success("申请已提交！"); 
            fetchProjectDetails(); 
        }
    })
    .catch(error=>{
        ElMessage.error(error);
    }) 
     
};
// Reset the modal form  
const resetForm = () => {  
    showModal.value = false;  
    joiningReason.value = '';  
    userStrength.value = '';  
};

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
    flex-direction: column; /* 设为列方向以适应标题和标签布局 */  
    justify-content: flex-start;  
    max-width: 1200px;  
    margin: 0 auto;  
    gap: 15px; /* 增加间距 */  
}  

.project-title {  
    font-size: 2.5em;  
    font-weight: bold;  
    color: #ffffff; /* 可以自定义颜色 */  
}  

.project-meta {  
    display: flex;  
    justify-content: space-between;  
    align-items: center;  
}  

.publisher {  
    font-size: 1.2em;  
    color: #ffffff;  
}  

.categories-tag {  
    background-color: gray; /* 标签背景颜色 */  
    color: white;
    margin: 0 5px;  
    padding: 5px 10px;  
    border-radius: 5px;  
    font-size: 1.1em;  
}  

.project-detail {
    padding: 10px 10px;
    min-height: 200px;  
    font-size: 1.2em;
    background: rgba(255, 255, 255, 0.9); /* 半透明背景让输入内容更清晰 */  
    color: black; /* 细节文本颜色 */  
    line-height: 1.5;  
}  

.buttons {  
    display: flex;  
    justify-content: center; /* 按钮居中对齐 */  
    margin-top: 20 auto; /* 使按钮获得剩余空间 */  
    padding-bottom: 20px; /* 可选，增加底部空间 */  
}  

.apply-button {  
    padding: 10px 20px;  
    font-size: 1.2em;  
    color: white;  
    background-color: #007bff; /* 申请按钮背景颜色 */  
    border: none;  
    border-radius: 5px;  
    cursor: pointer;  
    transition: background-color 0.3s ease;  
}  

.apply-button:hover {  
    background-color: #0056b3; /* 悬停时颜色 */  
} 
.project-image {
    width: 300px;

}

.status-container {  
  display: flex;                  /* 使用弹性布局 */  
  justify-content: center;        /* 水平居中对齐 */  
  align-items: center;            /* 垂直居中对齐 */  
}  

.status-text {  
  font-size: 24px;                /* 设置字体大小 */  
  font-weight: bold;              /* 设置字体加粗 */  
  color: #333;                    /* 字体颜色 */  
  padding: 15px 25px;             /* 设置内边距 */ 
  background-color: #f9f9f9;   
  border: 1px solid black;      /* 添加边框 */  
  border-radius: 5px;             /* 圆角边框 */  
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */  
}
</style>