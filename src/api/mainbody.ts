import request from '~/utils/request'
/**
 * 首页相关接口
 */
export const getProjectsPage = (params: any) => {
    return request({
      url: '/api/projects/page',
      method: 'get',
      params
    })
}
  