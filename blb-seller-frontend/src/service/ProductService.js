import repository from '../repository'
class ProductService {
  getProductPriceRange (product) {
    let highPrice = product.productSpecList[0].price
    let lowPrice = product.productSpecList[0].price
    product.productSpecList.forEach(v => {
      if (v.price < lowPrice) {
        lowPrice = v.price
      }
      if (v.price > highPrice) {
        highPrice = v.price
      }
    })
    return { highPrice, lowPrice }
  }

  async getProductCategory () {
    const data = await repository.getProductCategory()
    if (!data.success) {
      throw new Error('获取商品目录失败:' + data.msg)
    }
    return data.data
  }

  async getProduct (categoryId) {
    const data = await repository.getProduct(categoryId)
    if (!data.success) {
      throw new Error('获取商品列表失败:' + data.msg)
    }
    return data.data
  }

  async setProductStockFull (params) {
    const data = await repository.updateStock(params, 'full')
    if (!data.success) {
      throw new Error('置满库存失败:' + data.msg)
    }
    return true
  }

  async setProductStockEmpty (params) {
    const data = await repository.updateStock(params, 'empty')
    if (!data.success) {
      throw new Error('沽清库存失败:' + data.msg)
    }
    return true
  }

  async addProduct (product) {
    if (!product.productSpecList || product.productSpecList.length === 0) {
      throw new Error('新增商品失败:至少要有一个商品规格')
    }
    const data = await repository.addProduct(product)
    if (!data.success) {
      throw new Error('新增商品失败:' + data.msg)
    }
    return true
  }
}
export default new ProductService()
