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
	async getDeliveryOrder () {
		const data = await repository.getDeliveryOrder()
		if (!data.success){
			throw new Error('获取待配送订单列表失败:' +data.msg)
		}
		return data.data
	}
	async grabOrder (orderId) {
		const data= await repository.grabOrder(orderId)
		if (!data.success) {
			throw new Error('接单失败:' + data.msg)
		}
		return data.data
	}
	async getUndeliveryOrder(){
		const data = await repository.getUndeliveryOrder()
		if (!data.success) {
			throw new Error('获取未完成订单失败:' + data.msg)
		}
		return data.data
	}
	async getOrderDetail (orderId) {
		const data = await repository.getOrderDetail(orderId)
		if (!data.success) {
			throw new Error('获取订单详情失败:' + data.msg)
		}
		return data.data
	}
	async shipOrder (orderId) {
		const data = await repository.shipOrder(orderId)
		if (!data.success) {
			throw new Error('配送订单详情失败:' + data.msg)
		}
		return true
	}
	async completeOrder (orderId,code) {
		const data = await repository.completeOrder(orderId,code)
		if (!data.success) {
			throw new Error('完结订单详情失败:' + data.msg)
		}
		return true
	}
	
}
export default new RiderService()
