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

  async getShopEval (shopId) {
    const data = await axios.get(`/shop/${shopId}/evaluation`)
    return data.data
  }

  async getShopEvalList (params) {
    const data = await axios.get(`/shop/${params.shopId}/evaluation/list?tagId=${params.tagId}&page=${params.page}&size=${params.size}`)
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

  async getConsumerDefaultDelivery () {
    const data = await axios.get('/delivery/default')
    return data.data
  }

  async makeOrder (params) {
    const data = await axios.post('/shop/order', params)
    return data.data
  }

  async generatePay (orderId) {
    const data = await axios.post('/pay/order/' + orderId)
    return data.data
  }

  async getPayUrl (payId) {
    const data = await axios.get('/pay/' + payId)
    return data.data
  }

  async getPayStatus (payId) {
    const data = await axios.get('/pay/status/' + payId)
    return data.data
  }

  async clearCart (shopId) {
    const data = await axios.delete('/shop/' + shopId + '/cart')
    return data.data
  }

  async getOrderList (page, size) {
    const data = await axios.get('/shop/order', {
      params: {
        page, size
      }
    })
    return data.data
  }

  async getOrderDetail (orderId) {
    const data = await axios.get('/shop/order/' + orderId)
    return data.data
  }

  async getNearByShop (location) {
    const data = await axios.get('/shop/vicinity', {
      params: { location }
    })
    return data.data
  }

  async searchShop (params) {
    const data = await axios.get('/shop/search', {
      params
    })
    return data.data
  }

  async getDeliveryList () {
    const data = await axios.get('/delivery')
    return data.data
  }

  async addDelivery (params) {
    const data = await axios.post('/delivery', params)
    return data.data
  }

  async updateDelivery (params) {
    const data = await axios.put('/delivery/' + params.deliveryId, params)
    return data.data
  }

  async deleteDelivery (deliveryId) {
    const data = await axios.delete('/delivery/' + deliveryId)
    return data.data
  }

  async uploadAvatar (fromData) {
    const data = await axios({
      method: 'post',
      url: '/avatar',
      headers: { 'Content-Type': 'multipart/form-data' },
      data: fromData
    })
    return data.data
  }

  async updateUserInfo (userInfo) {
    const data = await axios.post('/info', userInfo)
    return data.data
  }

  async updatePassword (oldPassword, newPassword) {
    const data = await axios.post(`/info/password?oldPassword=${oldPassword}&newPassword=${newPassword}`)
    return data.data
  }
}

export default new Repository()
