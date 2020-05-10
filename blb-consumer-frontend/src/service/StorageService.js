class StorageService {
  getItem (key) {
    return window.localStorage.getItem(key)
  }

  putItem (key, val) {
    window.localStorage.setItem(key, val)
  }
}

export default new StorageService()
