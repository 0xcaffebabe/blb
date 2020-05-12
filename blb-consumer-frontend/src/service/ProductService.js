class ProductService {
  getProductPriceRange (product) {
    let highPrice = product.productSpecList[0].price
    let lowPrice = product.productSpecList[0].price
    product.productSpecList.forEach(v => {
      if (v.price < lowPrice) {
        lowPrice = v.price
      }
      if (v.price > highPrice) {
        highPrice = v.price
      }
    })
    return { highPrice, lowPrice }
  }
}
export default new ProductService()
