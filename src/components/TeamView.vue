<template>
  <div class="team-intro-container">
    <div class="background-image"></div>
    <div class="team-content">
      <h1>我们的开发团队</h1>
      <div class="team-members">
        <div class="team-member" v-for="(member, index) in teamMembers" :key="index">
          <h2>{{ member.name }}</h2>
          <p><strong>分工:</strong> {{ member.role }}</p>
          <p><strong>爱好:</strong> {{ member.hobbies.join(', ') }}</p>
          <p><strong>擅长:</strong> {{ member.skills.join(', ') }}</p>
          <p><strong>个人简介:</strong> {{ member.bio }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const teamMembers = [
  {
    name: '彭欣',
    role: '皇帝',
    hobbies: ['阅读', '徒步旅行'],
    skills: ['手搓整个Web项目', '编程语法样样精通'],
    bio: '小组皇帝，本次项目的主要负责人，完成了99%的内容，但谦逊地表示，团队的每一份贡献都至关重要，剩余的1%需大家共同努力完善，确保项目圆满成功，彰显团队精神的力量。',
  },
  {
    name: '薛亦卓',
    role: '创新内容负责',
    hobbies: ['编程', '打游戏'],
    skills: ['创新内容，添加新功能', '测试'],
    bio: '前端技术栈深厚，对用户体验设计有独到见解。他巧妙融合最新前端技术与用户行为研究，创新性地提升页面交互，让用户体验跃升至全新高度。',
  },
  {
    name: '贾梵宇',
    role: '团队介绍页面编写',
    hobbies: ['看电影', '游戏'],
    skills: ['善用大模型技术辅助项目开发', '构图设计'],
    bio: '擅长利用大模型技术进行页面设计，通过精准的数据分析与预测，不断优化页面布局与元素，实现个性化推荐，大幅提升用户留存与转化率。',
  },
  {
    name: '周翔宇',
    role: 'UI设计师',
    hobbies: ['画画', '健身'],
    skills: ['PS', 'Adobe XD'],
    bio: '具备出色的设计感，能够精准把握用户需求。无论是色彩搭配、布局规划，还是功能实现，都能精准触达用户痛点，打造既美观又实用的产品界面。',
  },
  {
    name: '林子超',
    role: '辅助工程师',
    hobbies: ['音乐', '旅行'],
    skills: ['自动化测试', '性能测试'],
    bio: '对测试有极大热情，确保产品质量是他的首要任务。他深入参与每个测试环节，细致入微地排查漏洞，以零容忍的态度对待每一个潜在问题，赢得团队高度信赖。',
  }
];
  </script>
  
  <style scoped>
  .team-intro-container {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  overflow: hidden;
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('../assets/img/J9.jpeg'); /* 背景图片路径 */
  background-size: cover;
  background-attachment: fixed; /* 背景图片固定不动 */
  z-index: -1;
}

.team-content {
  
  z-index: 999;
  color: white;
  text-align: center;
  padding: 50px 20px;
  overflow-y: auto; /* 允许垂直滚动 */
  height: 100%;
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url('../assets/img/J10.jpeg'); 
}

.team-members {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  max-width: 1000px;
}

.team-member {
  background-color: rgba(0, 0, 0, 0.6);
  padding: 20px;
  margin: 10px 0;
  border-radius: 8px;
  text-align: left;
}

.team-member h2 {
  margin-top: 0;
}

.team-member p {
  margin: 5px 0;
}
  </style>