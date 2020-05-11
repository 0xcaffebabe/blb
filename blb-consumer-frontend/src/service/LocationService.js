// 位置服务
class LocationService {
  getLocation () {
    if (this.location) {
      return this.location
    }
    this.location = {
      city: '漳州',
      location: '117,29'
    }
    return this.location
  }
}
export default new LocationService()
