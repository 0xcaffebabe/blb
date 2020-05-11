import reposutiry from '../repository'
import categoryService from '../service/CategoryService'

jest.mock('../repository')
test('获取店铺目录列表', () => {
  const ret = {
    success: true,
    msg: 'success',
    data: [
      { categoryName: '目录1'}
    ]
  }
  reposutiry.getShopCategory.mockResolvedValue(ret)
  return categoryService.getShopCategory().then(data => expect(data).toEqual(ret.data))
})
