import axios from './config'

class Repository {
  async getShopCategory () {
    const data = await axios.get('/category/1')
    return data.data
  }

  async getConsumerInfo () {
    const data = await axios.get('/info')
    return data.data
  }

  async login (username, password) {
    const data = await axios.post(`/login?username=${username}&password=${password}`)
    return data.data
  }

  async register (params) {
    const data = await axios.post('/register', params)
    return data.data
  }
}

export default new Repository()
