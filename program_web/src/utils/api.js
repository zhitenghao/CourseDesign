import axios from 'axios'// 导入axios
import { Message } from 'element-ui'//
import router from '@/router'

// 请求拦截器
axios.interceptors.request.use(config => {
  // 如果存在token，请求携带这个token
  if (window.localStorage.getItem('tokenStr')) {
    config.headers.Authorization = window.localStorage.getItem('tokenStr')
  }
  return config
}, error => {
  console.log(error)
})

// 响应拦截器，统一处理错误提示
axios.interceptors.response.use(success => { // success表示成功调到后端接口，但若后端接口不允许做这样的操作，即业务逻辑错误
  // 业务逻辑错误
  if (success.status && success.status === 200) { // 是否调到接口 并且 调到接口返回200
    // 判断业务逻辑是否错误
    // data就是后端返回的json对象，后端返回的json对象有code（对应的响应码）、message（响应信息）、object(返回的json中携带了一些传递给我们要用的对象)
    // 500 业务逻辑错误
    // 401 未登录
    // 403 权限禁止,权限不足
    if (success.data.code === 500 || success.data.code === 401 || success.data.code === 403) {
      Message.error({ message: success.data.message }) // 弹出错误信息
      return // 弹出错误信息即可，不需要返回对应的数据
    }
    // 弹出成功提示信息
    if (success.data.message) { // 判断有没有成功信息
      Message.success({ message: success.data.message })
    }
  }
  return success
}, error => { // 后端接口访问失败，拿不到json对象
  // 504 服务器有问题
  // 404 页面找不到
  if (error.response.code === 504 || error.response.code === 404) {
    Message.error({ message: '服务器被吃了' })
  } else if (error.response.code === 403) {
    Message.error({ message: '权限不足，请联系管理员！' })
  } else if (error.response.code === 401) {
    Message.error({ message: '尚未登录，请登录' })
    router.replace('/') // 跳转到登录页面
  } else {
    if (error.response.data.message) { // 判断返回的响应里面是否有信息，如果有打印响应信息
      Message.error({ message: error.response.data.message })
    } else { // 如果没有响应信息，未知错误
      Message.error({ message: '未知错误' })
    }
  }
})

let base = '' // eslint-disable-line no-unused-vars

// 传送json格式的post请求
// url 请求路径(接口)
// params 参数
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}

// 传送json的put请求
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params
  })
}

// 传送json的get请求
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    data: params
  })
}

// 传送json的delete请求
export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    data: params
  })
}
