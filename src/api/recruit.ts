import request from '~/utils/request'
/**
 * 发布招募相关接口
 */
export const fetchTagList= ()=>{
    return request({
        url: '/api/categories',
        method: 'get'
    })
}
export const addProject= (params:any)=>{
    return request({
        url: '/api/projects',
        method: 'post',
        data: { ...params }
    })
}