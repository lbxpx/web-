<template>  
    <div class="contact-page">  
        <div class="image-container">  
            <img src="../assets/img/miku.jpg" alt="左侧图片" />  
        </div>  

        <div class="content">  
            <div class="image-wrapper upper">  
                <img src="../assets/img/longnai.jpg" alt="上方图片" />  
            </div>  
            <div class="text-wrapper">  
                <h1>联系我们</h1>  
                <p>我们乐意回答您的任何问题或疑虑。</p>  
                <p>请通过以下方式与我们联系：</p>  
                <ul>  
                    <li>电子邮件: support@example.com</li>  
                    <li>电话: (114) 514-1919810</li>  
                    <li>地址: 济南高新技术产业开发区舜华路街道舜华路1500号</li>  
                    <del>极速方法（不建议）：在Web课程群里发一句“请21组的同学尽快与我联系”</del>
                </ul>   
            </div>  
            <div class="image-wrapper lower">  
                <img src="../assets/img/qianshu.jpg" alt="下方图片" />  
            </div>     
        </div>  

        <div class="image-container">  
            <img src="../assets/img/miku.jpg" alt="右侧图片" />  
        </div>  
    </div>  
</template>  

<script setup lang="ts">  
// 可以根据需要添加相关的 Vue 逻辑  
</script>  

<style scoped>  
.contact-page {  
    display: flex;  
    /* align-items: stretch;   */
    justify-content: center;  
    min-height: 100vh; /* 使页面至少占满整个视口高度 */  
    background-color: #f9f9f9; /* 背景颜色 */  
}  

.image-container {  
    flex: 1; /* 固定宽度为页面宽度的25% */  
}  

.image-container img {  
    max-width: 100%; /* 确保图片不超出容器 */  
    height: auto; /* 自适应高度 */  
}  

.content {  
    flex: 2; /* 固定宽度为页面宽度的50% */  
    display: flex;  
    flex-direction: column; /* 垂直排列上中下内容 */   
    text-align: center; /* 内容居中 */  
    padding: 0;  
    height: 100vh; 
}  

.image-wrapper {  
    flex: 1; /* 每张图片占容器高度的1/3 */  
}  

.image-wrapper img {  
    width: 100%; /* 确保图片不超出容器 */  
    height: 365px; /* 自适应高度 */  
}  

.text-wrapper {  
    flex: 1; /* 占据剩余空间 */ 
    display: flex;  
    flex-direction: column;  
    justify-content: center; /* 垂直居中内容 */  
    padding: 20px;  
}  

.content h1 {  
    margin-bottom: 15px; /* 标题底部间距 */  
}  

.content p {  
    margin: 10px 0; /* 段落上下间距 */  
}  

.content ul {  
    list-style-type: none; /* 去除默认列表样式 */  
    padding: 0; /* 去除内边距 */  
}  

.content li {  
    margin: 5px 0; /* 列表项目上下间距 */  
    font-size: 16px; /* 字体大小 */  
}  
</style>