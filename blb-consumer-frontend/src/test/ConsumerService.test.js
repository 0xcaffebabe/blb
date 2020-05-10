import reposutiry from '../repository'
import storageService from '../service/StorageService'
import consumerService from '../service/ConsumerService'

jest.mock('../repository')
jest.mock('../service/StorageService')
test('测试 isLogin',() => {
  storageService.getItem.mockResolvedValue('token')
  reposutiry.getConsumerInfo.mockResolvedValue({ success: true })
  return consumerService.isLogin().then(ret => expect(ret).toEqual(true))
})

test('测试 getConsumerInfo', () => {
  reposutiry.getConsumerInfo.mockResolvedValue({
    success: true,
    data: {
      name: 'cxk'
    }
  })
  return consumerService.getConsumerInfo().then(data => expect(data.name).toEqual('cxk'))
})
test('测试 login', () => {
  reposutiry.login.mockResolvedValue({
    success: true,
    data: {
      token: "token",
      avatar: "avatar"
    }
  })
  const ret = consumerService.login('cxk','123')
  
  return ret
    .then(data => 
      expect(data.token).toEqual('token') && 
      expect(data.avatar).toEqual('avatar') &&
      // 确定putItem被调用
      expect(storageService.putItem.mock.calls[0][0]).toBe('token') &&
      expect(storageService.putItem.mock.calls[0][1]).toBe('token')
      )
})