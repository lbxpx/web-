import request from '~/utils/request'
/**
 * 我的项目相关接口
 */

export const getUserProject=(userId:any)=>{
    return request({
        url: `/api/myprojects/${userId}`,
        method: 'get',
    })
}

export const existPro=(userId: any,projectId:any)=>{
    return request({
        url:`/api/myprojects/${userId}/${projectId}`,
        method: 'delete'
    })
}

/**
 * 结束项目
 * @param projectId 
 * @returns 
 */
export const endPro=(projectId: any)=>{
    return request({
        url: `/api/projects/${projectId}`,
        method: 'delete',
    })
}

/**
 * 获取项目团队信息
 * @param projectId 
 * @returns 
 */
export const getProjectTeamMembers= (projectId:any)=>{
    return request({
        url: `/api/myprojects/team/${projectId}`,
        method:'get'
    })
}

