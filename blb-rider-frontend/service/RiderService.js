import repository from '../repository/index.js'
class RiderService {
	async login (username, password) {
		const data = await repository.login(username, password)
		if (!data.success) {
			throw new Error('登录失败:' + data.msg)
		}
		uni.setStorageSync('token',data.data.token)
		return data.data
	}
	async register (params) {
		const data = await repository.register(params)
		if (!data.success) {
			throw new Error('注册失败:' + data.msg)
		}
		return data.data
	}
}
export default new RiderService()
