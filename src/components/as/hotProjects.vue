
<template>
    <div>
        <version></version>
    </div>
    <div class="container">
        <div class="project-card">
            <h2 class="project-title">购物平台开发项目</h2>
            <p class="project-description">
                本项目致力于打造一个功能完善的购物平台，集商品浏览、购物车、在线支付、订单管理、用户评价等功能于一体。采用最新的前端技术和后端架构，确保平台的高效运行和用户体验的优化。
            </p>
        </div>
        <div class="project-card">
            <h2 class="project-title">电商网络开发项目</h2>
            <p class="project-description">
                专注于电商网络的深度开发，包括商品推荐系统、搜索引擎优化、数据分析与可视化等。通过大数据分析用户行为，实现个性化推荐，提升用户购买转化率，助力电商企业快速发展。
            </p>
        </div>
        <div class="project-card">
            <h2 class="project-title">社交平台软件开发</h2>
            <p class="project-description">
                开发一款集社交、娱乐、信息共享于一体的综合性社交平台。提供用户注册、好友添加、动态发布、私信聊天、群组讨论等功能，注重用户隐私保护和社交体验的优化。
            </p>
        </div>
        <div class="project-card">
            <h2 class="project-title">编程竞赛组队平台</h2>
            <p class="project-description">
                为编程爱好者打造一个组队参赛的平台，提供赛事信息、队伍招募、在线编程练习、成绩排名等功能。通过平台，用户可以轻松找到志同道合的队友，共同备战各类编程竞赛。
            </p>
        </div>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
.container {
    display: flex;
    flex-wrap: wrap; /* 允许盒子换行 */
    justify-content: space-between; /* 盒子之间保持间距 */
    align-items: flex-start;
    padding: 20px;
}
 
.project-card {
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 20px;
    width: calc(48% - 20px); /* 调整宽度，使盒子在容器中均匀分布 */
    margin-bottom: 20px; /* 添加下边距，使盒子之间有空隙 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease; /* 添加过渡效果 */
}
 
.project-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}
 
.project-title {
    font-size: 1.5em;
    color: #333;
    margin-bottom: 10px;
}
 
.project-description {
    font-size: 1em;
    color: #666;
    line-height: 1.5;
}
</style>