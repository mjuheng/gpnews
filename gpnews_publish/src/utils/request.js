// 导出一个配置好的axios提供给main挂载
import axios from 'axios'
import local from '@/utils/local'
import router from '@/router'
import JSONBIG from 'json-bigint'
import qs from 'qs'
import { Message } from 'element-ui'

// 对axios进行配置
// baseURL  作用：设置基准地址（前面一段相同的地址）
axios.defaults.headers.post['Content-Type'] = 'application/json'
axios.defaults.transformResponse = [(data) => {
  // 后台的原始数据   理想情况 json字符串
  // 后台可能没有任何响应内容  data 值是 null
  try {
    return JSONBIG.parse(data)
  } catch (e) {
    return data
  }
}]
// 配置请求头
// if (local.getUser()) {
//   axios.defaults.headers.Authorization = `Bearer ${local.getUser().token}`
// }

// 请求拦截器
axios.interceptors.request.use(config => {
  if(config.method === "post" && config.url.search('/data-file') == -1){
    config.data = qs.parse(config.data)
  }
  // 1. 获取token
  const sessionId = localStorage.sessionId || ''
  // 2. 头部设置token
  config.headers.Authorization = sessionId
  return config
}, err => {
  return Promise.reject(err)
})

// 响应拦截器
axios.interceptors.response.use(res => res , err => {
  // 请求失败
  if (err.response.status === 401) {
    // 跳转登录页面  http://localhost:8080/#/login
    // window.location.href = 'http://localhost:8080/#/login'
    // window.location.hash = '#/login'
    // 建议使用 vue-router 来跳转 , push是router实例的函数
    Message({type: 'error', message: '请登录账户'})
    return router.push('/login')
  }else if (err.response.status == 504){
    Message({type: 'error', message: '接口异常'})
  }else if (err.response.status == 500){
    // if (err.response.data.message.search('Subject does not have') != -1){
    //   Message({type: 'error', message: '权限不足'})
    // }else {
      Message({type: 'error', message: err.response.data.message})
    // }
  }
  return Promise.reject(err)
})

export default axios
