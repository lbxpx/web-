<template>  
    <div class="project-page">  
        <BackToIndex/>
        <div class="content" style="margin-top: 20px;">  
            <div class="post" v-for="article in articles" :key="article.id">  
                <div class="post-content" @click="blogDetail(article.id)">  
                    <h2 class="post-title">{{ article.title }}</h2>  
                    <p class="post-summary">{{ article.author }}</p>  
                    <span class="post-date">{{ article.date }}</span>
                    <div class="post-tags">  
                        <span v-for="(tag, index) in article.tags" :key="index" class="tag">{{ tag }}</span>  
                    </div>    
                </div>  
                <img :src="article.image" alt="文章图片" class="post-image" />  
            </div>  
            <div ref="loadingIndicator" class="loading" v-if="loading">加载中...</div>  
        </div>  
    </div>  
</template>  

<script setup lang="ts">  
import { ElMessage } from 'element-plus';
import { ref, onMounted, onBeforeUnmount } from 'vue';  
import { getBlogsPage } from '~/api/blog';
import router from '~/router';  

// 文章列表和加载状态  
const articles = ref<any[]>([]);  
const loading = ref(false);  
const noMoreBlogs = ref(false)  
const page = ref(1); // 页码，用于每次请求不同的数据  
const pageSize = ref(10); 

// 从后端 API 获取文章数据  
const fetchProjects = async () => {   
    loading.value = true;
    try {  
        await getBlogsPage({page:page.value,pageSize:pageSize.value})
        .then(res=>{
            if(res.data.code===1){
                // 将新数据添加到项目列表  
                const newRecords = res.data.data.records.map((record:any) => ({  
                    title: record.title,  
                    author: record.author, // 映射返回数据中的发布者名称  
                    image: record.image || '', // 确保 image 有默认值  
                    id: record.id,  
                    category: record.categories || [],// 使用默认空数组  
                    date: record.date,
                    tags: record.tags ? record.tags.split(',').map((tag: string) => tag.trim()) : [] // 拆分tags并去掉空格  
                }));  
                if (newRecords.length > 0) {  
                    articles.value = articles.value.concat(newRecords); // 追加到现有列表  
                    page.value++; // 增加页码，准备下一次的请求  
                } else {  
                // 如果没有新数据，设置一个标志以停止后续请求  
                noMoreBlogs.value = true; 
                }  
            }
            else{
                ElMessage.error(res.data.msg);
            }
        })
    } catch (error) {  
        console.error('Error fetching blogs:', error);  // 修改错误信息  
    } finally {  
        loading.value = false;  
    }  
};  

// 监听滚动事件  
const loadMore = () => {  
    const windowHeight = window.innerHeight;  
    const { scrollTop, scrollHeight } = document.documentElement;  

    if (windowHeight + scrollTop >= scrollHeight - 100 && !loading.value&& !noMoreBlogs.value) {  
        fetchProjects(); // 接近底部加载更多项目  
    } 
    // else{
    //     ElMessage("无更多数据");
    // } 
};  

// 路由跳转方法  
const blogDetail = (id: string) => {  
    router.push({ name: 'blogdetail', query: { id: id } }); // 使用params传递Id  
    
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
.project-page {  
    background-image: url('../assets/img/nake.jpg');  
    background-size: cover;  
    background-attachment: fixed;  
    min-height: 100vh;  
    padding: 20px;  
}  

.content {  
    max-width: 1200px;  
    margin: 0 auto;  
}  

.post {  
    display: flex;  
    align-items: flex-start;  
    background: rgba(255, 255, 255, 0.9);  
    border-radius: 8px;  
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);  
    padding: 20px;  
    margin-bottom: 20px;  
}  

.post-content {  
    flex: 1;  
    margin-right: 20px;  
}  

.post-image {  
    max-width: 200px;  
    height: auto;  
    border-radius: 4px;  
}  

.post-title {  
    font-size: 1.5em;  
    margin: 0 0 10px 0;  
}  

.post-author {  
    font-size: 1em;  
    color: #555;  
}  

.post-date {  
    font-size: 0.9em;  
    color: #888;  
}  

.loading {  
    text-align: center;  
    padding: 20px;  
    font-size: 1.2em;  
}  
.post-tags {  
    margin-top: 10px; /* 为标签和其他内容增加一些间距 */  
}  

.tag {  
    display: inline-block;  
    background-color: #e0e0e0; /* 标签背景色 */  
    color: #333; /* 标签文字颜色 */  
    border-radius: 4px;   
    padding: 5px 10px; /* 标签内边距 */  
    margin-right: 5px; /* 标签之间的间隔 */  
    font-size: 0.9em; /* 标签字体大小 */  
}  
</style>