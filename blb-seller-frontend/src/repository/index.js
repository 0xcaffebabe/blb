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

  async getSellerInfo () {
    const data = await axios.get('/seller/info')
    return data.data
  }

  async upload (fromData) {
    const data = await axios({
      method: 'post',
      url: '/image',
      headers: { 'Content-Type': 'multipart/form-data' },
      data: fromData
    })
    return data.data
  }

  async registerShop (params) {
    const data = await axios.post('/shop/register', params)
    return data.data
  }

  async getNewOrderList () {
    const data = await axios.get('/shop/order/new')
    return data.data
  }

  async dinnerOut (orderId) {
    const data = await axios.put(`/shop/order/${orderId}/out`)
    return data.data
  }

  async getOrderList (params) {
    const data = await axios.get(`/shop/order/list?page=${params.page}&size=${params.size}`)
    return data.data
  }

  async getOrderDetail (orderId) {
    const data = await axios.get('/shop/order/' + orderId)
    return data.data
  }

  async getProductCategory () {
    const data = await axios.get('/shop/product/category')
    return data.data
  }

  async getProduct (categoryId) {
    const data = await axios.get(`/shop/category/${categoryId}/product`)
    return data.data
  }

  async updateStock (params, type) {
    const data = await axios.put(`/shop/category/${params.categoryId}/product/${params.productId}/${params.specId}/stock?type=${type}`)
    return data.data
  }

  async addProduct (product) {
    const data = await axios.post(`/shop/category/${product.categoryId}/product`, product)
    return data.data
  }
}
export default new Repository()
