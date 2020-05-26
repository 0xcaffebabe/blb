import repository from '../repository'
class ShopService {
  async getShopCategory () {
    const data = await repository.getShopCategory()
    return data.data
  }

  async hasShop () {
    const data = await repository.getShop()
    if (!data.success) {
      throw new Error('获取店铺信息失败:' + data.msg)
    }
    if (data.data) {
      return true
    }
    return false
  }
}
export default new ShopService()
