import { da } from 'element-plus/es/locale'
import request from '~/utils/request'
/**
 * 消息接口
 */

/**
 * 获取消息
 * @param id
 * @returns 
 */
export const getUserMessages  = (id:any) => {
    return request({
      url: `/api/messages/${id}`,
      method: 'get',
    })
}

/**
 * 批准申请
 * @param id 
 * @param params 
 * @returns 
 */
export const approved  = (data:any) => {
  return request({
    url: '/api/messages/approved',
    method: 'post',
    data: data
  })
}

export const rejected = (data:any) => {
    return request({
      url: '/api/messages/rejected',
      method: 'post',
      data: data
    })
}