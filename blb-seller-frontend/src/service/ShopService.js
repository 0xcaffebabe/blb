import repository from '../repository'
class ShopService {
  async getShopCategory () {
    const data = await repository.getShopCategory()
    return data.data
  }

  async hasShop () {
    const data = await repository.getShop()
    if (!data.success) {
      throw new Error('获取店铺信息失败:' + data.msg)
    }
    if (data.data) {
      return true
    }
    return false
  }

  async getShopInfo () {
    const data = await repository.getShop()
    if (!data.success) {
      throw new Error('获取店铺信息失败:' + data.msg)
    }
    return data.data
  }

  async upload (fileList) {
    if (!fileList || fileList.length === 0) {
      throw new Error('上传头像失败:没有选择图片')
    }
    const formData = new FormData()
    formData.append('file', fileList[0])
    const data = await repository.upload(formData)
    if (!data.success) {
      throw new Error('上传失败:' + data.msg)
    }
    return data.data
  }

  async registerShop (params) {
    params = JSON.parse(JSON.stringify(params))
    const startHour = new Date(params.bussinessHour[0])
    const endHour = new Date(params.bussinessHour[1])
    params.bussinessHour = startHour.getHours() + ':' + startHour.getMinutes() + '-' + endHour.getHours() + ':' + endHour.getMinutes()
    params.categoryId = params.categoryId[params.categoryId.length - 1]
    const data = await repository.registerShop(params)
    if (!data.success) {
      throw new Error('注册店铺失败:' + data.msg)
    }
    return data.data
  }

  async updateShopInfo (shopInfo) {
    const data = await repository.updateShopInfo(shopInfo)
    if (!data.success) {
      throw new Error('更新店铺信息失败:' + data.msg)
    }
    return true
  }

  getShopBussinessHour (shopInfo) {
    const s = shopInfo.businessHour.split('-')
    const startTime = new Date(s[0])
    const endTime = new Date(s[1])
    console.log({ startTime, endTime })
    return { startTime, endTime }
  }
}
export default new ShopService()
