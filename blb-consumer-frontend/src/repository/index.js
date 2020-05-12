import axios from './config'

class Repository {
  async getShopCategory () {
    const data = await axios.get('/category/1')
    return data.data
  }

  async getConsumerInfo () {
    const data = await axios.get('/info')
    return data.data
  }

  async login (username, password) {
    const data = await axios.post(`/login?username=${username}&password=${password}`)
    return data.data
  }

  async register (params) {
    const data = await axios.post('/register', params)
    return data.data
  }

  async getShopByCategory (params) {
    const data = await axios.get(`/category/${params.categoryId}/shop`, {
      params
    })
    return data.data
  }

  async getShopInfo (shopId) {
    const data = await axios.get('/shop/info/' + shopId)
    return data.data
  }

  async getShopProductCategory (shopId) {
    const data = await axios.get(`/shop/${shopId}/category/`)
    return data.data
  }

  async getProductList (shopId, categoryId) {
    const data = await axios.get(`/shop/${shopId}/${categoryId}/product/`)
    return data.data
  }

  async addProductIntoCart (params) {
    const data = await axios.put(`/shop/${params.shopId}/cart/${params.productId}/${params.specId}?quantity=${params.quantity}`)
    return data.data
  }

  async getCartProductList (shopId) {
    const data = await axios.get('/shop/' + shopId + '/cart')
    return data.data
  }
}

export default new Repository()
