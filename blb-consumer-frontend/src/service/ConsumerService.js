import repository from '../repository'
class ConsumerService {
  // 判断是否登录
  async isLogin () {
    const token = window.localStorage.getItem('token')
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
    window.localStorage.setItem('token', data.data.token)
    return data.data
  }
}
export default new ConsumerService()
