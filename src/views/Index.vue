<template>
    <div>
        <div class="background"></div> 
         <CommonHeader></CommonHeader>
         <Navbar />
         <MainBody/>
    </div>
</template>

<script setup lang="ts">
import CommonHeader from '~/components/CommonHeader.vue';

</script>

<style lang="less" scoped>

.background {  
    position: fixed; /* 固定背景 */  
    top: 0;  
    left: 0;  
    right: 0;  
    bottom: 0;  
    background-image: url('../assets/img/likelisi.jpg'); /* 替换为你的背景图像 */  
    background-size: cover; /* 背景覆盖 */  
    background-position: center; /* 背景居中 */  
    opacity: 0.5; /* 设置透明度 */  
    z-index: -1; /* 确保背景在底层 */  
  } 
</style>

  
  
  
  
  
  