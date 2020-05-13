import repository from '../repository'
import storageService from './StorageService'
class ConsumerService {
  // 判断是否登录
  async isLogin () {
    const token = storageService.getItem('token')
    if (!token) {
      return false
    }
    const data = await repository.getConsumerInfo()
    return data.success
  }

  // 获取当前登录用户信息（会对信息进行缓存）
  async getConsumerInfo () {
    if (this.consumerInfo) {
      return this.consumerInfo
    }
    const data = await repository.getConsumerInfo()
    if (!data.success) {
      throw new Error('获取用户信息失败:' + data.msg)
    }
    return data.data
  }

  // 进行登录
  async login (username, password) {
    const data = await repository.login(username, password)
    if (!data.success) {
      throw new Error('登录失败:' + data.msg)
    }
    storageService.putItem('token', data.data.token)
    return data.data
  }

  // 进行注册
  async register (params) {
    const data = await repository.register(params)
    if (!data.success) {
      throw new Error('注册失败:' + data.msg)
    }
    return data.data
  }

  // 获取当前登录用户默认收货信息
  async getDefaultDelivery () {
    const data = await repository.getConsumerDefaultDelivery()
    if (!data.success) {
      throw new Error('获取默认收货信息失败:' + data.msg)
    }
    return data.data
  }
}
export default new ConsumerService()
