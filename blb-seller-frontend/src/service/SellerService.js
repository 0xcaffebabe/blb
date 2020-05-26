import repository from '../repository'
class SellerService {
  async login (username, password) {
    const data = await repository.login(username, password)
    if (!data.success) {
      throw new Error('登录失败:' + data.msg)
    }
    window.localStorage.setItem('token', data.data.token)
    return data.data
  }
}
export default new SellerService()
