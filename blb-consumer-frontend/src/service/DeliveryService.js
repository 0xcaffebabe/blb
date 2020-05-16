import repository from '../repository'
class DeliveryService {
  async getDeliveryList () {
    const data = await repository.getDeliveryList()
    if (!data.success) {
      throw new Error('获取收货信息列表失败:' + data.msg)
    }
    return data.data
  }

  async addDelivery (params) {
    const data = await repository.addDelivery(params)
    if (!data.success) {
      throw new Error('增加收货信息失败:' + data.msg)
    }
    return true
  }

  async updateDelivery (params) {
    const data = await repository.updateDelivery(params)
    if (!data.success) {
      throw new Error('增加收货信息失败:' + data.msg)
    }
    return true
  }

  async deleteDelivery (deliveryId) {
    const data = await repository.deleteDelivery(deliveryId)
    if (!data.success) {
      throw new Error('删除收货信息失败:' + data.msg)
    }
    return true
  }
}
export default new DeliveryService()
