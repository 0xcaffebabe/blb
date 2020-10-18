import repository from '../repository'
import qrcode from 'qrcode'
// 支付服务
class PayService {
  async generatePay (orderId) {
    const data = await repository.generatePay(orderId)
    if (!data.success) {
      throw new Error('生成支付失败:' + data.msg)
    }
    return data.data
  }

  async getPayInfo (orderId) {
    const data = await repository.getPayUrl(orderId)
    if (!data.success) {
      throw new Error('获取支付信息失败:' + data.msg)
    }
    const dataUrl = await qrcode.toDataURL(data.data.url)
    return {
      url: dataUrl,
      shopName: data.data.shopName,
      orderId: data.data.orderId
    }
  }

  async getPayStatus (payId) {
    const data = await repository.getPayStatus(payId)
    if (!data.success) {
      throw new Error('获取支付状态失败:' + data.msg)
    }
    return data.data
  }

  getPayStatusStr (status) {
    if (status === 0) {
      return '未支付'
    }
    if (status === 1) {
      return '已支付'
    }
    if (status === 2) {
      return '已取消'
    }
  }
}
export default new PayService()
