import repository from '../repository'
// 购物车服务
class CartService {
  async addProduct (params) {
    const data = await repository.addProductIntoCart(params)
    if (!data.success) {
      throw new Error('加入购物车失败:' + data.msg)
    }
  }

  async getProductList (shopId) {
    const data = await repository.getCartProductList(shopId)
    if (!data.success) {
      throw new Error('获取购物车商品列表失败:' + data.msg)
    }
    return data.data
  }

  getCartTotal (productList) {
    if (!productList) {
      return 0
    }
    let total = 0
    productList.forEach(v => {
      total += v.productQuantity
    })
    return total
  }

  async clearCart (shopId) {
    const data = await repository.clearCart(shopId)
    return data.success
  }
}
export default new CartService()
