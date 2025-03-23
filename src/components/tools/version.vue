<template>
    <div>
     <el-main class="carousel-container">
    <el-carousel :interval="4000" type="card" height="300px" class="carousel">
      <el-carousel-item v-for="item in imageList" :key="item.url">
        <img :src="item.url" alt="Carousel Image" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>
  </el-main>   
    </div>
</template>

<script setup lang="ts">
import utils from '~/utils/utils';

// 定义跑马灯的图片路径
const imageList = [
  { url: utils.getAssetsFile('J5.png') },
  { url: utils.getAssetsFile('J6.png') },
  { url: utils.getAssetsFile('J7.png') },
  { url: utils.getAssetsFile('J8.png') },
];
</script>

<style scoped>
.carousel-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%;
  margin: 0 auto;
}

.carousel {
  width: 100%;
  height: 300px;
}
.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>