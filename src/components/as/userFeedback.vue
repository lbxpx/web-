<template>
    <div class="review-container">
        <div class="review" v-for="(review, index) in reviews" :key="index">
            <h3 class="review-title">{{ review.title }}</h3>
            <p class="review-content">{{ review.content }}</p>
            <div class="review-footer">
                <span>用户{{ index + 1 }}：{{ review.user }}</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
const reviews = [
    {
        user: '项目达人',
        title: '🚀找到了最佳的项目伙伴！🚀',
        content: '自从加入了这个项目组队平台，我轻松地找到了与我技能互补、志同道合的团队成员。我们一起完成了多个有挑战性的项目，不仅提升了个人能力，还收获了宝贵的团队合作经验。平台的匹配算法非常精准，让我能够迅速找到合适的人选。而且，平台上的用户都非常专业和靠谱，每次合作都非常愉快。强烈推荐给所有需要组建团队的项目经理和开发者！'
    },
    {
        user: '创意先锋',
        title: '💡激发无限创意，共创辉煌！💡',
        content: '这个项目组队平台真是我的创意宝库！在这里，我遇到了许多思维活跃、富有创造力的伙伴。我们一起头脑风暴，碰撞出无数新颖的项目点子。平台的交流工具非常便捷，让我们能够随时随地分享想法和进展。我已经和多个团队成功合作，完成了多个备受好评的项目。感谢这个平台，让我找到了这么多志同道合的创意伙伴！'
    },
    {
        user: '技术大牛',
        title: '💻技术难题，迎刃而解！💻',
        content: '作为技术大牛，我经常需要寻找能够挑战我的项目。这个项目组队平台让我如鱼得水！在这里，我找到了许多高难度的技术项目，与一群同样热爱技术的伙伴一起攻克难关。我们不仅解决了许多技术难题，还共同学习、成长。平台的用户素质都非常高，每次合作都能让我受益匪浅。强烈推荐给所有追求技术成长的朋友们！'
    },
    {
        user: '协作高手',
        title: '🤝团队协作，共创佳绩！🤝',
        content: '我一直非常重视团队协作，而这个项目组队平台让我找到了最佳的协作伙伴。我们团队在平台上组建后，迅速进入了工作状态，每个人都充分发挥了自己的专长。平台的项目管理工具非常实用，让我们能够高效地分配任务、跟踪进度。通过多次成功的合作，我们团队已经建立了深厚的友谊和默契。感谢这个平台，让我们能够一起创造更多的佳绩！'
    }
];
</script>

<style scoped>
/* 样式部分保持不变 */
.review-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
}

.review {
    margin-bottom: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.review-title {
    font-size: 1.5em;
    margin-bottom: 10px;
    color: #333;
    font-weight: bold;
}

.review-content {
    font-size: 1em;
    color: #555;
    line-height: 1.6;
}

.review-footer {
    margin-top: 10px;
    font-size: 0.9em;
    color: #777;
    text-align: right;
}
</style>