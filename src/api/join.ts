import request from '~/utils/request'
/**
 * 加入项目相关接口
 */
export const getProjectDetail = (id: any,userId:any)=>{
    return request({
        url:`/api/joins/${id}/${userId}`,
        method:'get',
    })
}

export const applyToJoinProject = (params:any)=>{
    return request({
        url:'/api/joins',
        method:'post',
        data: { ...params }
    })
}