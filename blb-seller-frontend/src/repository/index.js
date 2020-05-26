import axios from 'axios'

axios.defaults.baseURL = process.env.VUE_APP_API_URL
console.log(process.env.VUE_APP_API_URL)
// 添加请求头
axios.interceptors.request.use(config => {
  config.headers.TYPE = 'SELLER'
  config.headers.TOKEN = window.localStorage.getItem('token')
  return config
})
axios.interceptors.response.use(config => {
  return config
})
class Repository {
  async getShopCategory () {
    const data = await axios.get('/shop/category')
    return data.data
  }

  async login (username, password) {
    const data = await axios.post(`/login?username=${username}&password=${password}`)
    return data.data
  }

  async getShop () {
    const data = await axios.get('/shop/info')
    return data.data
  }
}
export default new Repository()
