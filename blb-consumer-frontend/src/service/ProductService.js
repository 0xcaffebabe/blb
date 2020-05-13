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

  // 计算商品列表总包装费
  calcTotalPackageFee (productList) {
    if (!productList) {
      return 0
    }
    let total = 0
    productList.forEach(v => {
      total += v.packageFee
    })
    return total
  }

  // 计算商品列表总付
  calcTotalAmount (productList) {
    if (!productList) {
      return 0
    }
    let total = 0
    productList.forEach(v => {
      total += v.productPrice * v.productQuantity
    })
    total += this.calcTotalPackageFee(productList)
    return total
  }
}
export default new ProductService()
