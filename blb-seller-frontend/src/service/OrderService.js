import repository from '../repository'
class OrderService {
  async getNewOrderList () {
    const data = await repository.getNewOrderList()
    if (!data.success) {
      throw new Error('获取新订单失败:' + data.msg)
    }
    return data.data
  }

  async dinnerOut (orderId) {
    const data = await repository.dinnerOut(orderId)
    if (!data.success) {
      throw new Error('出餐失败:' + data.msg)
    }
    return true
  }

  async getOrderList (params) {
    const data = await repository.getOrderList(params)
    if (!data.success) {
      throw new Error('获取订单列表失败:' + data.msg)
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
}
export default new OrderService()
