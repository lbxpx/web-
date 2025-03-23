//引入路由对象
import { createRouter, createWebHistory } from 'vue-router'

// 每个路由都需要映射到一个组件。
const routes = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('~/views/Login.vue')
    },
    {
        path: '/index',
        name:'index',
        component:() => import('~/views/Index.vue'),
    },
    {
        path: '/profile',
        name:'profile',
        component:() => import('~/views/Profile.vue'),
        redirect: '/aboutme',
        children:[
            {
                path: '/messages',
                name: 'messages',
                component: () => import('~/views/Messages.vue')
            },
            {
                path: '/aboutme',
                name: 'aboutme',
                component: () => import('~/views/aboutMe.vue')
            },
        ]
    },
    
    {
        path: '/myProject',
        name: 'myProject',
        component: () => import('~/views/MyProject.vue')
    },
    {
        path: '/recruit',
        name: 'recruit',
        component: () => import('~/views/Recruit.vue')
    },
    {
        path: '/join/:id', // :id是路由参数  ',
        name: 'join',
        component: () => import('~/views/Join.vue')
    },
    {  
        path: '/search',  
        name: 'search',  
        component: () => import('~/views/Search.vue'),  
    }, 
    {
        path: '/contact',
        name: 'contact',
        component: () => import('~/views/Contact.vue')
    },
    {
        path: '/blog',
        name: 'blog',
        component: () => import('~/views/Blog.vue')
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('~/components/AboutUs.vue')
    },
    {
        path: '/team',
        name: 'team',
        component: () => import('~/components/TeamView.vue')
    },
    {
        path: '/blogdetail',
        name: 'blogdetail',
        component: () => import('~/views/BlogDetail.vue')
    },
    {
        path: '/writeblog',
        name: 'writeblog',
        component: () => import('~/views/WriteBlog.vue')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('~/views/404.vue')
    },
    {
        path: '/:pathMatch(.*)*',//解决进入路由守卫后未找到对应的路由记录问题
        redirect: '/404'
    }
]
 
const router = createRouter({
    history: createWebHistory(), // 修正函数名
    routes
})
 
//导出router
export default router;


