import moment from 'moment'
import repository from '../repository'
moment.locale('zh-cn')
// 订单服务
class OrderService {
  getOrderDeliveryTime () {
    return moment().add(1, 'hours').calendar()
  }

  async makeOrder (params) {
    if (!params.deliveryId) {
      throw new Error('下单失败:收货信息ID为空')
    }
    if (!params.productList) {
      throw new Error('下单失败:下单商品列表为空')
    }
    params.productList = params.productList.map(v => {
      return {
        productId: v.productId,
        specId: v.specId,
        quantity: v.productQuantity
      }
    })
    const data = await repository.makeOrder(params)
    if (!data.success) {
      throw new Error('下单失败:' + data.msg)
    }
    return data.data
  }

  async getOrderList (page, size) {
    const data = await repository.getOrderList(page, size)
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

  getOrderStatus (order) {
    if (order.orderStatus === 0) {
      return '未处理'
    }
    if (order.orderStatus === 1) {
      return '已处理'
    }
    if (order.orderStatus === 2) {
      return '配送中'
    }
    if (order.orderStatus === 3) {
      return '已完结'
    }
    if (order.orderStatus === 4) {
      return '已作废'
    }
  }
}
export default new OrderService()
