<template>  
    <div class="navbar-container">  
        <div class="navbar">  
            <router-link  
                v-for="item in menuItems"  
                :key="item.name"  
                :to="item.link"  
                class="navbar-item"  
                @mouseover="hoveredItem = item.name"  
                @mouseleave="hoveredItem = null"  
                :class="{ 'hovered': hoveredItem === item.name }"  
            >  
                {{ item.name }}  
            </router-link>  
        </div>  
    </div>  
</template>  

<script lang="ts" setup>  
import { ref } from 'vue';  

const hoveredItem = ref<string | null>(null);  

const menuItems = [  
    { name: '我的项目', link: '/myProject' },  
    { name: '发布招募', link: '/recruit' },  
    { name: '联系我们', link: '/contact' },  
    { name: '博客', link: '/blog' }, 
    {name:'关于我们 ',link:'/about'},
    {name:'队伍介绍',link:'team'}
];  
</script>  

<style scoped>  
.navbar-container {  
    display: flex;  
    flex-direction: column;  
    align-items: center;  
    justify-content: center;  
    width: 90%;  
    max-width: 1380px;  
    margin: 0 auto;  
    padding: 10px 0;  
    background-color: transparent;  
}  

.navbar {  
    display: flex;  
    background-color: transparent;  
    padding: 10px 0;  
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

.navbar-item.hovered {  
    transform: scale(1.2); /* 悬停时放大文字 */  
    color: black; /* 悬停时保持黑色 */  
}  
</style>