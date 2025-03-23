
import request from '~/utils/request'
/**
 * 博客相关接口
 */
export const getBlogsPage = (params:any)=>{
    return request({
        url: '/api/blogs/page',
        method: 'get',
        params
    })
}

export const getBlogsDetail = (id: any)=>{
    return request({
        url:`/api/blogs/${id}`,
        method:'get',
    })
}

export const addBlogPost = (params:any)=>{
    return request({
        url:'/api/blogs/write',
        method:'put',
        data: { ...params }
    })
}

