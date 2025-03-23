import request from '~/utils/request'
/**
 * 评论信息相关接口
 */

export const getComments=(id:any)=>{
    return request({
        url:`api/comments/${id}`,
        method:'get'
    })
}

export const addComments=(id:any,params:any)=>{
    return request({
        url:`api/comments/${id}`,
        method:'post',
        data:{...params}
    })
}