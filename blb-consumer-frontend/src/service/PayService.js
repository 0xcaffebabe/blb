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

  async getPayQRCode (orderId) {
    const data = await repository.getPayUrl(orderId)
    if (!data.success) {
      throw new Error('获取支付链接失败:' + data.msg)
    }
    const dataUrl = await qrcode.toDataURL(data.data)
    return dataUrl
  }

  async getPayStatus (payId) {
    const data = await repository.getPayStatus(payId)
    if (!data.success) {
      throw new Error('获取支付状态失败:' + data.msg)
    }
    return data.data
  }
}
export default new PayService()
