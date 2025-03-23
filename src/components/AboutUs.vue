<template>
  <div class="static-background">
    <div class="text-container">
      <h2 class="title">项目团队招募平台</h2>
      <p class="subtitle">汇聚人才，共创未来</p>
      <p class="description">
        加入我们，与志同道合的伙伴一起探索创新，实现项目梦想！
      </p>
      <ul class="features">
        <li>轻松发布和查找项目</li>
        <li>智能匹配最合适的团队成员</li>
        <li>实时沟通和协作工具</li>
        <li>成功案例和用户反馈参考</li>
        <li>完全免费，无隐藏费用</li>
      </ul>
      <router-link to="/index" class="cta-button">开始探索</router-link>
    </div>
  </div>
  <div class="titles">
    <p>专为竞赛与项目团队招募而设计</p>
  </div>
  <!-- 介绍平台的初衷 -->
  <div class="section">
    <div class="section-inner">
      <img src="../assets/img/J1.png" alt="Team Icon" class="section-icon">
      <p>
        我们的初衷很简单：<strong>连接每一个有梦想的开发者</strong>。
        在这个充满挑战的时代，每一个项目都值得被看见，每一段才华都应该被发掘。
        我们的平台正是为了这样的你而诞生，让每一个项目都能找到最合适的团队。
      </p>
    </div>
  </div>

  <!-- 阐述平台的目的 -->
  <div class="section">
    <div class="section-inner">
      <img src="../assets/img/J2.png" alt="Collaboration Icon" class="section-icon">
      <p>
        我们的目的是构建一个<strong>高效、透明的团队招募生态系统</strong>，
        让项目发布者能够轻松找到合适的团队成员，同时也让寻求机会的人才能够快速加入心仪的项目。
        我们希望通过这个平台，促进知识与技能的交流，激发无限的创新可能。
      </p>
    </div>
  </div>

  <!-- 展望平台的未来 -->
  <div class="section">
    <div class="section-inner">
      <img src="../assets/img/J3.png" alt="Growth Icon" class="section-icon">
      <p>
        展望未来，我们<strong>致力于不断优化平台功能，提升用户体验</strong>。
        我们期待与更多的项目发起者和寻求者携手，共同打造一个充满活力与创新的团队招募平台，
        让每一个项目都能在这里找到最适合的伙伴，共同书写成功的篇章。
      </p>
    </div>
  </div>

  <el-menu :default-active="activeIndex" class="menu-bar" mode="horizontal" @select="handleSelect" :style="{ width: '100%', justifyContent: 'space-around' }">
    <el-menu-item index="1" class="menu-item">热门项目</el-menu-item>
    <el-menu-item index="2" class="menu-item">成功案例</el-menu-item>
    <el-menu-item index="3" class="menu-item">用户反馈</el-menu-item>
  </el-menu>

  <main>
    <component :is="currentComponent"></component>
  </main>
</template>
  
<script setup lang="ts">
import { ref, defineAsyncComponent } from 'vue';

const activeIndex = ref('1'); 
const currentComponent = ref(defineAsyncComponent(() => import('../components/as/hotProjects.vue'))); // 默认加载热门项目
 
const handleSelect = (key: string, keyPath: string[]) => {
  if (key === '1') {
    currentComponent.value = defineAsyncComponent(() => import('../components/as/hotProjects.vue'));
  } else if (key === '2') {
    currentComponent.value = defineAsyncComponent(() => import('../components/as/successStories.vue'));
  } else if (key === '3') {
    currentComponent.value = defineAsyncComponent(() => import('../components/as/userFeedback.vue'));
  }
  activeIndex.value = key;
};


</script>

<style scoped>
.static-background {
  position: relative;
  width: 100%;
  height: 800px;
  background-image: url('../assets/img/J4.png');
  background-size: cover;
  background-position: center;
  opacity: 0.8;
  overflow: hidden;
}

.text-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
  width: 100%;
  max-width: 600px;
  padding: 20px;
  text-align: center;
  color: rgba(7, 6, 6, 0.769); /* 设置整体文字颜色为白色 */
  font-family: 'Arial', sans-serif; /* 设置字体 */
  line-height: 1.6; /* 增加行高使文本更易读 */
}
 
.title {
  font-size: 3em;
  font-weight: bold;
  margin-bottom: 0.5em;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6); /* 添加文本阴影以增强可读性 */
}
 
.subtitle {
  font-size: 1.5em;
  margin-bottom: 1em;
  font-style: italic; /* 添加斜体样式 */
}
 
.description {
  font-size: 1em;
  margin-bottom: 1.5em;
}
 
.features {
  list-style: none; /* 移除默认的列表样式 */
  padding: 0;
  margin: 20px 0;
}
 
.features li {
  background: rgba(255, 255, 255, 0.2); /* 设置半透明背景 */
  margin: 5px 0;
  padding: 10px;
  border-radius: 5px; /* 添加圆角 */
  font-size: 1em;
}
 

.cta-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #ff6f61;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  font-size: 1em;
  font-weight: bold;
}

.titles {
  text-align: center;
  padding-top: 40px;
  padding-bottom: 20px;
  margin: 0 auto;
  font-size: 2.5em;
}

.section {
  margin-top: 40px;
  padding: 20px;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
  border-radius: 15px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  position: relative;
  padding-left: 80px;
}

.section-inner {
  text-align: left;
}

.section-icon {
  position: absolute;
  top: 50%;
  left: 20px;
  transform: translateY(-50%);
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #e0e0e0;
  padding: 5px;
}

.menu-bar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 120px;
  background-color: white;
}

.menu-item {
  font-size: 20px;
  color: rgb(216, 114, 145);
  flex: 1;
  text-align: center;
  max-width: 20%;
  box-sizing: border-box;
}

.el-menu-item.is-active {
  color: #d3d3d3;
}
</style>