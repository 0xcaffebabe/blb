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
}
export default new CartService()
