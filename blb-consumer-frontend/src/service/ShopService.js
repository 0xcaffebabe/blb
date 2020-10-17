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

  async getNearyByShop () {
    const data = await repository.getNearByShop(locationService.getLocation().location)
    if (!data.success) {
      throw new Error('获取附近店铺失败:' + data.msg)
    }
    return data.data
  }

  async searchShop (params) {
    const data = await repository.searchShop(params)
    if (!data.success) {
      throw new Error('搜索店铺失败:' + data.msg)
    }
    return data.data
  }

  async getShopInfo (shopId) {
    const data = await repository.getShopInfo(shopId)
    if (!data.success) {
      throw new Error('获取店铺信息失败:' + data.msg)
    }
    return data.data
  }

  async getShopProductCategory (shopId) {
    const data = await repository.getShopProductCategory(shopId)
    if (!data.success) {
      throw new Error('获取店铺商品分类失败:' + data.msg)
    }
    return data.data
  }

  async getProductList (shopId, categoryId) {
    const data = await repository.getProductList(shopId, categoryId)
    if (!data.success) {
      throw new Error('获取商品列表失败:' + data.msg)
    }
    const productList = data.data
    // 设置默认选中规格
    productList.forEach(v => {
      v.defaultSpec = v.productSpecList[0]
    })
    return productList
  }

  async getShopEval (shopId) {
    const data = await repository.getShopEval(shopId)
    if (!data.success) {
      throw new Error('获取店铺评价失败:' + data.msg)
    }
    return data.data
  }

  async getShopEvalList (params) {
    const data = await repository.getShopEvalList(params)
    if (!data.success) {
      throw new Error('获取店铺评价列表失败:' + data.msg)
    }
    return data.data
  }
}
export default new ShopService()
