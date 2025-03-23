<template>  
    <div class="blog-page">  
        <div class="content" style="margin-top: 20px;">  
            <div class="input-group">  
                <textarea  
                    autocomplete="off"  
                    id="txtTitle"  
                    placeholder="请输入文章标题（5～100个字）"  
                    maxlength="100"  
                    v-model="blogPost.title"  
                    @input="checkTitleLength"  
                    class="el-textarea__inner"  
                    style="resize: none; min-height: 32px; width: 100%;"  
                ></textarea>  
                <div v-if="titleError" class="error-message">{{ titleError }}</div> 
                <hr class="separator" />  
            </div>   
            <div class="tag-section input-group">  
                <label for="tags">选择标签:</label>  
                <input   
                    id="tags"   
                    v-model="tagInput"   
                    placeholder="输入标签，以逗号分隔"   
                    class="input-field"   
                    @input="updateTags"  
                />  
            </div>  
            <div class="upload-image-container">  
                <upload-image @imageChange="imageChange" style="margin-right: 10px;"></upload-image>  
                <div class="avatar-uploader-tip">+ 添加封面图吸引更多读者~</div>  
            </div>  
            <div class="input-group">  
                <label for="description">内容详情:</label> 
                <textarea   
                    id="description"   
                    v-model="blogPost.content"   
                    placeholder="请输入博客内容"   
                    class="textarea-field"  
                ></textarea> 
            </div>  
            <button @click="submitBlogPost">发布博客</button>  
        </div>  
    </div>  
</template>
<script setup lang="ts">  
import { ElMessage } from 'element-plus';
import { marked } from 'marked';
import { ref } from 'vue';  
import { addBlogPost } from '~/api/blog';  
import router from '~/router';
import { useUserStore } from "~/store/user";  

const userStore = useUserStore();  
interface BlogPostItem {  
    title: string;  
    content: string;  
    authorName: string;  
    image: string;  
    authorId: Number;
    tags: string;  
}  

const blogPost = ref<BlogPostItem>({  
    title: '',  
    content: '',  
    authorName: userStore.username,  
    image: '',  
    authorId: userStore.userId,  
    tags: '', 
});  

const imageChange = (imageUrl: any) => {  
    blogPost.value.image = imageUrl;  
};  

const titleError = ref('');  

const checkTitleLength = () => {  
    if (blogPost.value.title.length < 5) {  
        titleError.value = '标题至少需要 5 个字。';  
    } else if (blogPost.value.title.length > 100) {  
        titleError.value = '标题不能超过 100 个字。';  
    } else {  
        titleError.value = ''; // 清除错误信息  
    }  
};
const tagInput = ref<string>(''); // 用于输入标签  
// const selectedTags = ref<string[]>([]); // 存储选中的标签  

const updateTags = () => {  
    // selectedTags.value = tagInput.value.split(',').map(tag => tag.trim()).filter(tag => tag !== '');
    blogPost.value.tags= tagInput.value 
};  

const submitBlogPost = async () => {  
    const rawContent = blogPost.value.content;

    // Crucial: Sanitize the markdown before sending
    const sanitizedContent = marked(rawContent);

    if(sanitizedContent === '' || sanitizedContent === undefined) {
        ElMessage({ message: '无法解析Markdown', type: 'error' });
        return;
    }

    await addBlogPost({
        title: blogPost.value.title,
        content:sanitizedContent,
        authorName:blogPost.value.authorName,
        image:blogPost.value.image,
        authorId:blogPost.value.authorId,
        tags:blogPost.value.tags
    })
    .then(res=>{
        if(res.data.code===1){
            ElMessage("发布成功");
            router.push('/index');
        }
    }).catch(error=>{
        ElMessage.error(error);
    })
};  

</script>
<style scoped>  
.blog-page {  
    background-image: url('../assets/img/nake.jpg');  
    background-size: cover;  
    background-attachment: fixed;  
    min-height: 100vh;  
    padding: 20px;  
}  

.content {  
    display: flex;  
    flex-direction: column;  
    justify-content: flex-start;  
    max-width: 1380px;  
    margin: 0 auto;  
    gap: 15px;  
    background: rgba(255, 255, 255, 0.9);  
    padding: 20px;  
    border-radius: 8px;  
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);  
}  

.el-textarea__inner {  
    font-size: 24px;
    text-align: center;
    font-weight: bold;
    height: 70px;  
    padding: 10px;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    outline: none;  
}  

.error-message {  
    color: red;  
    font-size: 12px;  
    margin-top: 5px;  
}  
.separator {  
    margin: 8px 0;  
      
    border-top: 3px solid black;  
}  

/* 自定义样式 */  
.preview {  
    margin-top: 20px;  
    padding: 10px;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    background: #f9f9f9;  
} 
.input-group {  
    display: flex;  
    flex-direction: column;  
}  

.input-field {  
    width: 30%; /* 设置输入框宽度 */  
    padding: 12px;  
    margin-top: 8px;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    font-size: 1em;  
}  

.textarea-field {  
    width: 95%;  
    padding: 10px;  
    margin-top: 8px auto;  
    border: 1px solid #ccc;  
    border-radius: 4px;  
    font-size: 1.4em;  
    min-height: 700px; /* 确保 textarea 有最小高度 */  
    resize: vertical; /* 允许垂直调整大小 */  
}  

button {  
    margin: 20px auto;   
    width: 120px;  
    background-color: #28a745;   
    color: white;  
    padding: 10px;  
    border: none;  
    border-radius: 4px;  
    cursor: pointer;  
    font-size: 1em;  
}  

button:hover {  
    background-color: #218838;   
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

/* 适应移动端 */  
@media (max-width: 768px) {  
    .input-field, .textarea-field {  
        width: 100%;   
    }  

    button {  
        width: 80%;  
    }  

    .content {  
        padding: 10px;  
    }  
}  
</style>