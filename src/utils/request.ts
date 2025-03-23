// axiosService.ts  
import axios from 'axios';  
import { useUserStore } from '~/store/user'; // 确保引入了你的用户 store  
import { getRequestKey, removePending } from './requestOptimize';  
import router from '~/router';  

const service = axios.create({  
  timeout: 60000,  
});  

// 请求拦截器
service.interceptors.request.use(  
  (config) => {   
    const userStore = useUserStore(); // 使用 Pinia store  
     
    // 如果请求是登录请求，则直接返回配置  
    if (config.url?.startsWith('/api/users/')) {  
      return config;  
    }  

    // Add X-Access-Token header to every request  
    if (userStore.token) {  
      config.headers['token'] = userStore.token;  
    } else {  
      // 如果没有 token，则重定向到登录页面  
      // window.location.href = '/login'; 
      router.push('/login');
      return Promise.reject(new Error('Unauthorized'));  
    }  
    
    return config;  
  },  
  (error) => {  
    return Promise.reject(error);  
  }  
);

// 响应拦截器
// 在 Axios 响应拦截器中调用 getRequestKey  
service.interceptors.response.use(  
  (response) => {  
      const { data, config } = response;  
      
      if (data.status === 401) {  
          router.push('/login');  
      }  
 
      if (config) {  
          // 处理 URL 替换  
          const url = config.url;  
          if (url) {  
              config.url = url.replace('/api', '');  
          }  

          // 确保调用 getRequestKey 时 config 是有效的  
          const key = getRequestKey(config);  
          removePending(key);  
      }  

      if (data.code === 1 || data.code === 0) {  
          return response;  
      }  
      return Promise.reject(data);  
  },  
  (error) => {  
      if (error && error.response) {  
          switch (error.response.status) {  
              case 401:  
                  router.push('/login');  
                  break;  
              case 405:  
                  error.message = '请求错误';  
                  break;  
          }  
      }  

      // 确保从 error.config 传递到 getRequestKey 的配置存在  
      if (error.config) {  
          const key = getRequestKey(error.config);  
          removePending(key);  
      }  

      return Promise.reject(error);  
  }  
);  

export default service;