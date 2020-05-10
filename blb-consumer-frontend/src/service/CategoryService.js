import repository from '../repository'

class CategoryService {
  async getShopCategory () {
    return await repository.getShopCategory()
  }
}

export default new CategoryService()
