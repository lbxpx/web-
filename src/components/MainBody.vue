<template>  
    <div class="home-page">  
        <div class="content">  
            <h1>欢迎来到我们的主页</h1>  
            <p>这里是主要内容部分，您可以在此展示文章、项目等信息。</p>  
            <p>  
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus  
                lacinia odio vitae vestibulum. Proin in libero id felis cursus  
                dignissim vitae ut est.  
            </p>  

            <div class="grid-container" ref="projectGrid">
                <Carousel class="carousel-item"></Carousel>  
                <div v-for="project in projects" 
                :key="project.id" 
                class="grid-item"
                @click="goToProject(project.id)"   
                >  
                    <h3 class="title">{{ project.title }}</h3>  
                    <div class="project-content">  
                        <div class="project-info">  
                            <p class="publisher-name">{{ project.publisherName }}</p>  
                            <div class="categories">  
                                <span v-for="category in project.categories.slice(0, 2)" :key="category.id" class="categories-label">  
                                    {{ category.name }}  
                                </span>  
                            </div>  
                        </div>  
                        <img :src="project.image" alt="项目缩略图" class="thumbnail" />  
                    </div>  
                </div>  
                <div v-if="loading" class="loading">加载中...</div>  
            </div>  
        </div>  
    </div>  
</template>  

<script lang="ts" setup>  
import { ElMessage } from 'element-plus';
import { ref, onMounted, onBeforeUnmount } from 'vue';  
import { getProjectsPage } from '~/api/mainbody';
import router from '~/router';
interface Category {  
    id: number;  
    name: string;  
}
interface project {
    title: string,
    publisherName: string,
    image: string,
    id: string
    categories: [Category];
}

const projects = ref<project[]>([]);  // projects，表示项目列表  
const loading = ref(false);
const noMoreProjects = ref(false)  
const page = ref(1); // 页码，用于每次请求不同的数据  
const pageSize = ref(10);

const fetchProjects = async () => {   
    loading.value = true;
    try {   
        await getProjectsPage({page:page.value,pageSize:pageSize.value})
        .then(res=>{
            if(res.data.code===1){
                // 将新数据添加到项目列表  
                const newRecords = res.data.data.records.map((record:any) => ({  
                    title: record.title,  
                    publisherName: record.publisherName, // 映射返回数据中的发布者名称  
                    image: record.image || '', // 确保 image 有默认值  
                    id: record.id,  
                    categories: record.categories || [] // 使用默认空数组  
                }));  
                if (newRecords.length > 0) {  
                    projects.value = projects.value.concat(newRecords); // 追加到现有列表  
                    page.value++; // 增加页码，准备下一次的请求  
                } else {  
                // 如果没有新数据，设置一个标志以停止后续请求  
                noMoreProjects.value = true;   
                }  
            }
            else{
                ElMessage.error(res.data.msg);
            }
        })
    } catch (error) {  
        console.error('Error fetching projects:', error);  // 修改错误信息  
    } finally {  
        loading.value = false;  
    }  
};  

// 监听滚动事件  
const loadMore = () => {  
    const windowHeight = window.innerHeight;  
    const { scrollTop, scrollHeight } = document.documentElement;  

    if (windowHeight + scrollTop >= scrollHeight - 100 && !loading.value&& !noMoreProjects.value) {  
        fetchProjects(); // 接近底部加载更多项目  
    } 
    // else{
    //     ElMessage("无更多数据");
    // } 
};  

// 路由跳转方法  
const goToProject = (projectId: string) => {  
    router.push({ name: 'join', params: { id: projectId } }); // 使用params传递projectId  
};  


onMounted(() => {  
    window.addEventListener('scroll', loadMore);  
    fetchProjects(); // 初始加载项目  
});  

// 销毁时移除事件监听  
onBeforeUnmount(() => {  
    window.removeEventListener('scroll', loadMore);  
});  
</script>

<style scoped>   
.home-page {  
    position: relative;  
    min-height: 100vh; /* 确保最小高度为视口高度 */  
    display: flex;  
    justify-content: center; /* 中心对齐内容 */  
    overflow: hidden; /* 隐藏超出边界的内容 */  
}  

.content {  
    position: relative; /* 相对定位以便在背景之上显示 */  
    background-color: rgba(255, 255, 255, 0.8); /* 半透明背景色 */  
    padding: 40px;  
    border-radius: 8px; /* 圆角样式 */  
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); /* 阴影效果 */  
    width: 90%; /* 确保宽度为视口宽度的90% */  
    max-width: 1380px; /* 最大宽度设置为1380px */  
    text-align: center; /* 内容居中 */  
}  

.grid-container {  
    display: grid;  
    grid-template-columns:  repeat(5, 1fr); /* 创建自适应列 repeat(auto-fill, minmax(200px, 1fr));*/  
    gap: 16px; /* 网格间距 */  
    margin-top: 20px; /* 与上方内容的间距 */  
}  
.carousel-item {  
    grid-column: span 2; /* 占据3列 */  
    grid-row: span 2; /* 占据2行 */  
    width: 100%; /* 确保宽度适应 */  
} 
.grid-item {  
    background-color: rgba(200, 200, 200, 0.5); /* 半透明背景 */  
    border: 1px solid #ccc; /* 边框样式 */  
    border-radius: 8px; /* 圆角 */  
    padding: 20px; /* 内边距 */  
    text-align: center; /* 文本居中 */  
    min-height: 150px; /* 最小高度 */  
}  

.title {  
    margin-bottom: 10px; /* 与后续内容的间距 */  
    text-align: left; /* 标题左对齐 */  
}  

.project-content {  
    display: flex; /* 使用 flexbox 布局 */  
    align-items: center; /* 垂直居中对齐 */  
}  

.project-info {  
    flex: 1; /* 让项目信息块占据可用空间 */  
    margin-right: 10px; /* 与图片之间的间距 */  
    text-align: left; /* 文字左对齐 */  
}  

.publisher-name {  
    font-size: 0.9em; /* 设定较小的字体大小 */  
    color: #555; /* 设定颜色 */  
}  

.categories {  
    margin-top: 5px; /* 上边距 */  
}  

.categories-label {  
    background-color: #e0e0e0; /* 标签背景色 */  
    padding: 3px 6px; /* 内边距 */  
    border-radius: 12px; /* 圆角 */  
    margin-right: 5px; /* 右边距 */  
    font-size: 0.8em; /* 标签字体 */  
}  

.thumbnail {  
    max-width: 100px; /* 图片最大宽度 */  
    height: auto; /* 高度自动以保持比例 */  
}  

.loading {  
    text-align: center;  
    margin-top: 20px;  
}  
</style>