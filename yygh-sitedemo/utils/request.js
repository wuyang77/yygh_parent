import axios from 'axios'
import cookie from 'js-cookie'
import { MessageBox, Message } from 'element-ui'
// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8222/',//网关
    timeout: 15000 // 请求超时时间
})
// http request 拦截器
service.interceptors.request.use(
    config => {
    // token 先不处理，后续使用时在完善
    //cookie中的token取出来，放入到请求头中，这样就能解决跨域问题
    // token 先不处理，后续使用时在完善
    if (cookie.get('token')){
        config.headers['token'] = cookie.get('token')
    }
    return config
},
  err => {
    return Promise.reject(err)
})
// http response 拦截器
service.interceptors.response.use(
    response => {
        if (response.data.code !== 20000) {
          console.log(response.data)
            Message({
                message: response.data.message,
                type: 'error',
                duration: 5 * 1000
            })
            return Promise.reject(response.data)
        } else {
            return response.data
        }
    },
    error => {
        return Promise.reject(error.response)
})
export default service
