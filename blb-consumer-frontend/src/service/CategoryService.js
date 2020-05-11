import repository from '../repository'

class CategoryService {
  async getShopCategory () {
    const data = await repository.getShopCategory()
    if (!data.success) {
      throw new Error('获取店铺分类失败:' + data.msg)
    }
    return data.data
  }
}

export default new CategoryService()
