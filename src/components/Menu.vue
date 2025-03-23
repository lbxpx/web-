
<template>
    <!-- 行 -->
    <el-row class="btbg">
        <!-- 列 -->
        <!-- offset偏移量 span占位-->
        <el-col :offset="9" :span="6">
            <!-- 图标 -->
            <el-icon @click="openOrClose" v-if="!isCollapse">
                <Fold />
            </el-icon>
            <!-- 图标 -->
            <el-icon @click="openOrClose" v-if="isCollapse">
                <Expand />
            </el-icon>
        </el-col>
    </el-row>

    <el-menu default-active="home" :router="true" class="el" active-text-color="#d23918" text-color="black" :collapse="isCollapse" :collapse-transition="false" >  
        <template v-for="item1 in menuList" :key="item1.name">  
            <el-sub-menu v-if="item1.children && item1.children.length" :index="String(item1.name)">  
                <template #title>  
                    <component :is="item1.meta.icon" class="is"></component>  
                    <span>{{ item1.meta.title }}</span>  
                </template>  
        
                <el-menu-item v-for="child in item1.children" :key="child.name"  :index="String(child.name)" @click="handleClick(child)">  
                    <component :is="child.meta.icon" class="is"></component>  
                    <span>{{ child.meta.title }}</span>  
                </el-menu-item>  
            </el-sub-menu>  
    
            <el-menu-item v-if="item1.children == undefined&& item1.meta.rag ==1" :index="String(item1.name)" @click="handleClick(item1)">  
                <component :is="item1.meta.icon" class="is"></component>  
                <span>{{ item1.meta.title }}</span>  
            </el-menu-item>  
        </template>  
    </el-menu>

</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import{ ref, computed } from 'vue'
import PubSub from 'pubsub-js'
import { CustomRouteMeta, MenuItem } from '~/Types/types'

const router = useRouter();  

// 计算属性，过滤出包含 meta 信息的路由  
const menuList = computed<MenuItem[]>(() => {  
    return router.getRoutes()  
    .filter(route => route.meta && (route.meta as unknown as CustomRouteMeta).title) // 确保 title 存在  
    .map(route => {  
        // 通过检查是否有有效子路由来决定是否赋值 children  
        const children =   
            route.children && route.children.length   
                ? route.children.filter(child => child.meta && (child.meta as unknown as CustomRouteMeta).title).map(child => ({  
                    name: String(child.name),  // 显式将子路由的 name 转换为字符串  
                    meta: {  
                        title: (child.meta as unknown as CustomRouteMeta).title,  // 强制转换为 RouteMeta  
                        icon: (child.meta as unknown as CustomRouteMeta).icon || '', // 确保 icon 存在，且有默认值  
                        rag: (child.meta as unknown as CustomRouteMeta).rag
                    }  
                }))   
                : undefined; // 如果没有有效的子路由，就保持 undefined  

        return {  
            name: String(route.name),  // 显式将 name 转换为字符串  
            meta: {  
                title: (route.meta as unknown as CustomRouteMeta).title,  // 强制转换为 RouteMeta  
                icon: (route.meta as unknown as CustomRouteMeta).icon || '', // 确保 icon 存在，且有默认值 
                rag: (route.meta as unknown as CustomRouteMeta).rag
            },  
            children, // 仅在有子路由时赋值  
        };  
    });  
});


// 点击处理函数  
const handleClick = (item: MenuItem) => {  
    router.push({ name: item.name });  
};  

const isCollapse = ref(false)

const openOrClose = () => {
    isCollapse.value = !isCollapse.value
    //生产者 发布消息
    PubSub.publish('menuopenorclose', isCollapse.value)
}
</script>

<style scoped>
    .el{
        background-color: #96e9f8;
        --el-menu-horizontal-height: 100%;
    }

    .el:not(.el-menu--collapse) {
        background-color: #96e9f8;
        height: 90%;
        min-height: calc(100vh - 100px);
    }
    
    .el-menu--collapse {
        min-height: calc(100vh - 100px);
    }
    .is{
        width: 16px;
        height: 16px;
        padding-right: 5px;
    }
    
    .btbg {
        background-color: #4fd7ef;
        color: white;
        cursor: pointer;
        /* height: 20px; */
        /* padding-top: 10px; */
    }
</style>

