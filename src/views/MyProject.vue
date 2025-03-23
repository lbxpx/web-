<template>  
    <div class="project-page">  
        <CommonHeader />  
        <div class="content" style="margin-top: 20px;">  
            <div class="sidebar">  
                <h2>我的项目</h2>  
                <ul>  
                    <li  
                        v-for="(project, index) in projects"  
                        :key="index"  
                        :class="{ 'active': selectedProject === project }"  
                        @click="selectProject(project)"  
                    >  
                        {{ project.title }}  
                    </li>  
                </ul>  
            </div>  
            <div class="main-content">  
                <h2>项目详情及我的任务</h2>  
                <div class="project-details" v-if="selectedProject">  
                    <h3>{{ selectedProject.title }}</h3>  
                    <p>{{ selectedProject.assign }}</p> 
                </div>  
                <div v-else>  
                    <p>请选择左侧项目以查看详细信息</p>  
                </div>  
            </div>  
        </div>  
        <div class="team-info">  
            <h2>团队信息</h2>
            <ul>  
                <li v-for="member in teamMembers" :key="member.name">  
                    <img :src="member.image" alt="Team member image" class="team-member-image" />  
                    <span class="member-info">{{ member.name }}</span>  
                    <span class="member-info">角色: {{ member.role }}</span>
                    <span class="member-info">任务: {{ member.assign }}</span> 
                </li>  
            </ul>     
            <div class="buttons">   
                <template v-if="selectedProject">  
                    <template v-if="selectedProject.role === '队长'">  
                        <button @click="endProject">结束项目</button>  
                    </template>  
                    <template v-else-if="selectedProject.role === '成员'">  
                        <button @click="exitTeam">退出团队</button>  
                    </template>  
                </template>    
            </div>  
        </div>  
    </div>  
</template>  

<script setup lang="ts">  
import { onMounted, ref } from 'vue';  
import { useUserStore } from "~/store/user";
import { endPro, existPro, getProjectTeamMembers, getUserProject } from "~/api/myproject"
import { ElMessage, ElMessageBox } from 'element-plus';
const userStore = useUserStore();
const userId = userStore.userId;
const selectedProject = ref();  
// 定义团队信息接口  
interface teamMember {  
    name: string;
    image: string;  
    assign: string;
    role: string;  
}

const teamMembers= ref<teamMember[]>([]);

interface ProjectItem {  
    title: string;  
    projectId: Number;  
    userId:Number;
    assign:string;
    role:string;
}

// 存储项目数据  
const projects = ref<ProjectItem[]>([]);

// 新增选择项目方法  
const selectProject = async (project:any) => {  
    selectedProject.value = project; // 更新所选项目  
     // 获取项目团队成员  
     await getProjectTeamMembers(project.projectId).then(res => {  
        if (res.data.code === 1) {
        teamMembers.value = res.data.data; 
        } else {  
            ElMessage.error(res.data.msg);  
        }  
    }).catch(err => {  
        ElMessage.error(err.message);  
    });  
}; 

const init = async ()=>{
    await getUserProject(userId)
    .then(res=>{
        if(res.data.code ===1 ){
            projects.value=res.data.data
        }else{
            ElMessage(res.data.msg)
        }
    }).catch(error=>{
        ElMessage.error(error.message);
    })
}


const exitTeam = async () => { 
    ElMessageBox.confirm('确认退出吗, 是否继续?', '确认退出', {
      confirmButtonText: '退出',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async ()=>{
        await existPro(userId,selectedProject.value.projectId)
        .then(res=>{
            if(res.data.code===1){
                ElMessage("退出成功")
                init();
            }else{
                ElMessage("退出失败")
            }
        })
    }).catch(error=>{
        ElMessage.error(error.message)
    })
};  

const endProject = async () => { 
    await endPro(selectedProject.value.projectId)
    .then(res=>{
        if(res.data.code === 1) {
            ElMessage("恭喜你完成项目！")
            init();
        }
    }).catch(error=>{
        ElMessage(error)
    }) 
};  



onMounted(()=>{
    init();
})

</script>  

<style scoped>  
.project-page {  
    background-image: url('../assets/img/nake.jpg'); /* 替换为你的背景图像 */  
    background-size: cover; /* 背景图像覆盖整个页面 */  
    background-attachment: fixed; /* 背景图像固定 */  
    min-height: 100vh;  
    padding: 20px; /* 页面边距 */  
}  

.content {  
    display: flex;  
    justify-content: space-between;  
    max-width: 1200px; /* 最大宽度以保持内容集中 */  
    margin: 0 auto; /* 水平居中 */  
    gap: 20px; /* 左右模块间距离 */  
}  

.sidebar {  
    flex: 1;  
    background-color: rgba(249, 249, 249, 0.8); /* 调整为有透明度的背景 */  
    padding: 15px;  
    border-radius: 8px;  
}  

.sidebar h2 {  
    margin-bottom: 10px;  
}  

.sidebar ul {  
    list-style: none;  
    padding: 0;  
}  

.sidebar li {  
    cursor: pointer;  
    padding: 10px;  
    border-radius: 5px;  
    transition: background-color 0.3s;  
}  

.sidebar li:hover {  
    background-color: #e0e0e0;  
}  

.sidebar li.active {  
    background-color: #d1e7dd; /* 激活状态的背景 */  
    font-weight: bold;  
}  

.main-content {  
    flex: 2;  
    padding: 15px;  
    background-color: rgba(255, 255, 255, 0.8); /* 调整为有透明度的背景 */  
    border-radius: 8px;  
}  

.project-details {  
    margin-top: 20px;  
}  

.project-details h3 {  
    margin: 0;  
}  

.project-details p {  
    margin: 10px 0;  
}  

.team-info {  
    width: 90%; /* 确保宽度为视口宽度的90% */  
    max-width: 1380px; /* 最大宽度设置为1380px */  
    margin: 20px auto; /* 垂直和水平居中，设置顶部间距 */  
    text-align: center; /* 内容居中 */  
    background-color: rgba(255, 255, 255, 0.8); /* 可选，给项目进度模块设置背景以保证清晰度 */  
    padding: 15px;  
    border-radius: 8px;  
}  
 
.team-info h2 {  
    margin-bottom: 10px;  
}  
 
.team-info ul {  
    list-style: none;  
    padding: 0;  
}  

.team-info li {  
    display: flex;  
    align-items: center;  
    margin-bottom: 15px;  
}  

.team-member-image {  
    width: 50px; /* 头像大小 */  
    height: 50px;  
    border-radius: 50%; /* 头像圆形 */  
    margin-right: 20px; /* 头像与文本的间距 */  
}  

.member-info {  
    margin-right: 20px; /* 每个信息字段之间的间距 */  
    font-size: 20px; /* 信息字段字体大小 */  
    color: #555; /* 信息文本颜色 */  
} 

.buttons {  
    display: flex; /* 使用flex布局将按钮横向排列 */  
    gap: 10px; /* 按钮之间的间距 */  
    justify-content: center; /* 让按钮在父元素内水平居中 */  
}  

.buttons button {  
    padding: 10px 15px; /* 按钮的内边距 */  
    border: none;  
    border-radius: 5px; /* 按钮的圆角 */  
    background-color: #007bff; /* 按钮背景色 */  
    color: white; /* 按钮文本颜色 */  
    cursor: pointer; /* 鼠标悬停时为手型*/
    transition: background-color 0.2s; /* 背景色变化的过渡 */  
}  

.buttons button:hover {  
    background-color: #0056b3; /* 悬停时的背景色变化 */  
} 


</style>


<!-- //const onInputChange = () => {  
    //     if (saveTimeout) {  
    //         clearTimeout(saveTimeout);  
    //     }  
    //     saveTimeout = setTimeout(() => {  
    //         saveTaskNotes();   
    //     }, 2000);  
    // };  
    
    // const saveTaskNotes = async () => {  
    //     try {  
    //         await saveNotesApiCall({assign:taskNotes.value,userId:userId,projectId:selectedProject.value.projectId})
    //         .then(res=>{
    //             if(res.data.code===1){
    //                 ElMessage.success("任务已保存！");  
    //             }
    //         })  
    //     } catch (error) {  
    //         ElMessage.error("保存失败，请重试");  
    //     }  
    // }; -->