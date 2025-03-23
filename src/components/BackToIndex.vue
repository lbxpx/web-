<template>  
   <div class="navbar">  
     <router-link   
        to="/index"  
        class="navbar-item"   
    >  
            回到首页  
    </router-link>  
   </div>  
</template>  

<script lang="ts" setup>  
 
</script>  

<style scoped>   
.navbar {  
    display: flex;  
    background-color: transparent;  
    width: 100%;  
    justify-content: center;  
}  

.navbar-item {  
    color: black;  
    text-decoration: none;  
    padding: 15px 20px;  
    transition: transform 0.3s, color 0.3s; /* 添加 transform 动画 */  
    text-align: center;  
}  

.navbar-item:hover {  
    transform: scale(1.2); /* 悬停时放大文字 */  
    color: black; /* 悬停时保持黑色 */  
}  
</style>