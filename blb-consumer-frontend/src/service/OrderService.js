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
    const data = await repository.makeOrder(params)
    if (!data.success) {
      throw new Error('下单失败:' + data.msg)
    }
    return data.data
  }
}
export default new OrderService()
