<template>  
    <div class="blog-detail-page">  
        <div class="content">  
            <div class="post-detail">  
                <h1 class="post-title">{{ article.title }}</h1>  
                <span class="post-date">{{ article.date }}</span>  
                <div class="post-author">作者: {{ article.author }}</div>   
                <div class="tags">  
                    <span v-for="tag in article.tags" :key="tag" class="tag">{{ tag }}</span>  
                </div> 
                <div class="post-body" >
                    <div class="preview">  
                        <h3>内容预览</h3>  
                        <div v-html="compiledContent"></div> <!-- 此部分用于显示解析后的 Markdown -->  
                    </div>
                </div>   
            </div>  
            <div class="comments-section">  
                <h2>评论</h2>  
                <div v-for="comment in comments" :key="comment.id" class="comment">  
                    <strong>{{ comment.author }}:</strong> {{ comment.content }}  
                </div>  
                <textarea v-model="newComment" placeholder="写下你的评论..." class="comment-input"></textarea>  
                <button @click="addComment">发布评论</button>  
            </div>  
        </div>  
    </div>  
</template>  

<script setup lang="ts">  
import { ElMessage } from 'element-plus';
import { marked } from 'marked';
import { ref, onMounted, computed } from 'vue';   
import { useRoute } from 'vue-router';  
import { getBlogsDetail } from '~/api/blog';
import { addComments, getComments } from '~/api/comments';
import { useUserStore } from "~/store/user";  

const userStore = useUserStore(); 
const username=userStore.username;
const route = useRoute();   
const blogId = route.query.id;  
// 定义文章和评论的类型  
interface Article {  
    title: string;  
    date: string;  
    author: string;  
    content: string;  
    tags: string[];  
}  

interface Comment {  
    id: number;  
    author: string;  
    content: string;  
}  

const article = ref<Article>({  
    title: '',  
    date: '',  
    author: '',  // 添加作者字段  
    content: '',  
    tags: []  
});  

const comments = ref<Comment[]>([]);  
const newComment = ref('');  

const fetchArticle = async () => {  
    await getBlogsDetail(blogId)
    .then(res=>{
        if(res.data.code===1){
            const data = res.data.data;
            // 将新数据添加到项目列表  
            article.value.title = data.title;  
            article.value.date = data.createdAt;  
            article.value.author = data.authorName;  
            article.value.content = data.content;  
            article.value.tags = data.tags ? data.tags.split(',').map((tag: string) => tag.trim()) : [] // 拆分tags并去掉空格  
            }else{
            ElMessage(res.data.msg);}
    })
    .catch(error=>{
        ElMessage.error("请求出错了："+error.message);
    })
};  

const fetchComments = async () => {  
    await getComments(blogId)
    .then(res=>{
        if(res.data.code===1){
            comments.value=res.data.data;
        }
    }).catch(error=>{
        ElMessage.error(error);
    })
};  

const addComment = async () => {  
    if (newComment.value) {  
        comments.value.push({ id: comments.value.length + 1, author: username, content: newComment.value });
        await addComments(blogId,{author: username, content:newComment.value})
        .then(res=>{
            if(res.data.code===1){
                ElMessage("发布成功");
            }
        }).catch(error=>{
            ElMessage.error(error);
        }) 
        newComment.value = ''; // 清空输入框  
    }  
};  
const compiledContent = computed(() => {  
    return marked(article.value.content); // 将 Markdown 内容转换为 HTML  
});
onMounted(() => {  
    fetchArticle();
    fetchComments();
});  
</script>  

<style scoped>  
.blog-detail-page {  
    background-image: url('../assets/img/nake.jpg');  
    background-size: cover;  
    background-attachment: fixed;  
    min-height: 100vh;  
    padding: 20px;
    padding: 20px;  
}  

.content {  
    max-width: 1200px;  
    margin: 0 auto;  
}  

.post-detail {  
    background: rgba(255, 255, 255, 0.9);  
    border-radius: 8px;  
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);  
    padding: 20px;  
    margin-bottom: 20px;  
}  

.post-title {  
    font-size: 2em;  
    margin-bottom: 10px;  
}  

.post-date {  
    color: #888;  
    margin-bottom: 10px;  
}  

.post-author {  
    font-weight: bold;  
    margin-bottom: 10px;  
}  

.post-body {  
    margin: 20px 0;  
}  

.tags {  
    margin: 20px 0;  
}  

.tag {  
    display: inline-block;  
    background-color: #e0e0e0;  
    padding: 5px 10px;  
    margin-right: 5px;  
    border-radius: 4px;  
}  

.comments-section {  
    margin-top: 40px;  
    background: rgba(255, 255, 255, 0.9); /* 评论区背景 */  
    padding: 20px;  
    border-radius: 8px;  
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);  
}  

.comment {  
    margin-bottom: 10px;  
}  

.comment-input {  
    width: 100%;  
    height: 50px;  
    margin-top: 10px;  
    padding: 10px;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
}  

button {  
    padding: 10px 15px;  
    background-color: #007BFF;  
    color: white;  
    border: none;  
    border-radius: 4px;  
    cursor: pointer;  
    margin-top: 10px;  
}  

button:hover {  
    background-color: #0056b3;  
}  
</style>