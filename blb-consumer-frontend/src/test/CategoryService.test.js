import reposutiry from '../repository'
import categoryService from '../service/CategoryService'

jest.mock('../repository')
test('获取店铺目录列表', () => {
  const data = {
    success: true,
    msg: 'success',
    data: [
      { categoryName: '目录1'}
    ]
  }
  reposutiry.getShopCategory.mockResolvedValue(data)
  return categoryService.getShopCategory().then(data => expect(data).toEqual(data))
})
