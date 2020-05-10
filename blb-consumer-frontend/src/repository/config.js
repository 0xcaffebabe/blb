import axios from 'axios'

axios.defaults.baseURL = process.env.VUE_APP_API_URL
// 添加请求头
axios.interceptors.request.use(config => {
  config.headers.TYPE = 'CONSUMER'
  return config
})
axios.interceptors.response.use(config => {
  return config
})
export default axios
