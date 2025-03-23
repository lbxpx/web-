import { AxiosRequestConfig } from 'axios';
import md5 from 'md5';

//根据请求的地址，方式，参数，统一计算出当前请求的md5值作为key
const getRequestKey = (config: AxiosRequestConfig | null): string => {  
    if (!config) {  
        // 如果没有获取到请求的相关配置信息，根据时间戳生成  
        return md5(Date.now().toString()); // 将数字转换为字符串  
    }  

    // 使用安全的方式访问 url 和 method  
    const url = config.url || ''; // 默认空字符串以避免 undefined  
    const method = config.method || 'GET'; // 默认 GET 方法  
    const data = typeof config.data === 'string' ? config.data : JSON.stringify(config.data || {});  

    return md5(`${url}&${method}&${data}`);  
};  

// 定义 pending 为一个字典，使用 string 或 number 作为索引  
const pending: Record<string, any> = {};  

// 检查 key 值  
const checkPending = (key: string | number): boolean => !!pending[key];  

// 删除 key 值  
const removePending = (key: string | number): void => {  
    delete pending[key];  
};

export {
    getRequestKey,
    pending,
    checkPending,
    removePending
}
