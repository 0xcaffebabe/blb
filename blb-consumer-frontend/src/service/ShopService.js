import repository from '../repository'
import locationService from './LocationService'
class ShopService {
  async getShopListByCategory (params) {
    const data = await repository.getShopByCategory({
      location: locationService.getLocation().location,
      ...params
    })
    if (!data.success) {
      throw new Error(`获取分类${params.categoryId}下的店铺失败:${data.msg}`)
    }
    return data.data
  }
}
export default new ShopService()
