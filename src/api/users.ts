import request from '~/utils/request'
/**
 *
 * 用户登录相关接口
 *
 **/
// 登录
export const login = (data: any) =>
  request({
    'url': '/api/users/login',
    'method': 'post',
    data: data
  })

  // 退出
 export const userLogout = () =>
 request({
   'url': '/api/users/logout',
   'method': 'post',
 })

 //注册
 export const register = (data: any) =>
  request({
    'url': '/api/users/register',
    'method': 'post',
    data: data
  })

  //修改密码
  export const updatePassword = (data: any) =>
    request({
      'url': '/api/users/update',
      'method': 'post',
      data: data
    })

  //获取验证码  
 export const getVerifyCode = (data: any) =>
  request({
    'url': '/api/users/code',
    'method': 'post',
    data: data
  })
 
  //校验验证码
  export const checkVerifyCode = (data: any) =>
    request({
      'url': '/api/users/checkcode',
      'method': 'post',
      data: data
  })

  //获取图像验证码
  export const getImageCode = () =>
    request({
      'url': '/api/users/getValidateCode',
      'method': 'get',
  })

  //校验图形验证码
  export const testImageCode = (data: any) =>
    request({
      'url': '/api/users/testValidateInfo',
      'method': 'post',
      data: data
  })

  //获取当前用户信息
  export const getUserData = (id:any) =>{
    return request({
        url: `/api/userData/${id}`,
        method: 'get'
    })
  }
  //修改用户信息
export const editUserData = (id:any,params: any) => {
  return request({
    url: `/api/userData/${id}`, // 将 id 作为路径参数 
      method: 'put',
      data: { ...params }
  })
}

