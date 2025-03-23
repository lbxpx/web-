<template>  
    <div class="profile-page">  
        <div class="sidebar"> 
            <img :src="getImgSrc()" alt="头像" class="avatar" />  
            <div class="user-info"> 
                <p>{{ username }}</p>   
                <div class="user-stats">   
                    <p>用户等级: {{ userLevel }}</p>  
                    <p>火力值: {{ firepower }}</p>
                    <p>关注：1</p>  
                    <p>粉丝：50</p> 
                </div> 
            </div>  
        </div>
        <div class="userbutton">
            <button>+关注</button>
            <button>发消息</button>
        </div>
        
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
        <div class="main-content"> 
            <router-view></router-view>   
        </div>  
    </div>  
</template>  

<script setup lang="ts">   
import { ref } from 'vue';  
import { useUserStore } from "~/store/user";
const userStore = useUserStore();
const img = ref(userStore.image);
const getImgSrc = () => {  
    // console.log("当前图片URL:", img.value); // 添加调试信息  
    if (img.value === null || img.value === '')  
        return new URL("~/assets/0002.jpg", import.meta.url).href;  
    else return img.value;  
};
const hoveredItem = ref<string | null>(null);  

const menuItems = [  
    { name: '个人资料', link: '/aboutMe' },  
    { name: '消息', link: '/messages' },
    { name: '撰写博客', link: '/writeblog' },    
    { name: '首页', link: '/index' },  
];

const username = ref('John Doe');  
const userLevel = ref('普通用户');  
const firepower = ref(100);  
  
</script>  

<style scoped>  
.profile-page {  
    background-image: url('../assets/img/likelisi.jpg');  
    background-size: cover;  
    background-attachment: fixed;
    display: flex;
    flex-direction: column;  
    padding: 20px;  
}  

.sidebar {
    display: flex;
    width: 100%; /* 左侧边栏宽度 */   
}  

.user-info {
    display: flex;
    flex-direction: column;   
    margin-bottom: 25px;  
}

.user-info p {
    color: #18191C;
    font-weight: bold;
    margin-bottom: 0;
    font-size: 25px;
}

.user-stats {  
    display: flex; /* 使用 Flexbox 布局 */   
} 
.user-stats p{
    color: gray;
    font-size: 14px;
    margin-right: 20px;
}

.avatar { 
    align-self: center; 
    border-radius: 50%; 
    width: 80px;
    height: 80px; 
    margin-right: 20px;
}   

.userbutton {
    padding-left: 10px;
}

.userbutton>button {
    width: 100px;
    height: 30px;
    border: none;
    border-radius: 5px;
    margin-right: 5px;
    cursor: pointer;
}

.userbutton>button:nth-child(1) {
    background-color: #00AEEC;
    color: white;
}

.userbutton>button:nth-child(1):hover {
    background-color: #00B5E5;
    transition: 1000ms;
}

.userbutton>button:nth-child(2) {
    background-color: white;
    border: #61666D solid 1px;
    color: #61666D;
}

.userbutton>button:nth-child(2):hover {
    border-color: #00AEEC;
    color: #00AEEC;
    transition: 1000ms;
}

.main-content {  
    
    flex: 1; /* 主体内容占据剩余空间 */  
    margin: 0 30px;
} 

.navbar {  
    display: flex;  
    background-color: transparent;  
    padding: 10px 0;  
    width: 100%;  
    justify-content: left;  
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