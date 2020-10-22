const { response } = require('express')
const express = require('express')
const result = require('./result')
const app = express()
app.listen(8001)
app.use(function(req, res, next) {
  res.setHeader('Access-Control-Allow-Origin','*');
  res.setHeader('Access-Control-Allow-Methods', '*'); 
  res.setHeader('Access-Control-Allow-Headers', '*'); 
  res.setHeader("Content-Type", "application/json;charset=utf-8");
  next();
})
app.get('/',(req,res)=>{
    res.send(result({name:'blb seller mock server'})) 
})

const randomInt = bound => {
  return Math.ceil(Math.random() * bound) -1
}

const clone = origin => {
  return Object.assign({}, origin)
}

// 注册或登录
app.post('/login', (req, res) => {
  const username = req.query.username
  let token = ''
  let greeting = '欢迎登录'
  if (username === 'unregister') {
    token = 'unregister-token'
    greeting = '欢迎注册 您是本系统第9999位商家'
  }else {
    token = 'fake-token'
  }
  res.send(result({
    greeting,
    token,
    status: 0
  }))
})

// 店铺注册
app.post('/shop/register', (req, res) => {
  res.send(result('1'))
})

// 图片上传
app.post('/image', (req, res) => {
  res.send(result('https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3174175902,1222382505&fm=26&gp=0.jpg'))
})

// 获取店铺分类
app.get('/shop/category', (req, res) => {
  const topMenuList = [
    {categoryName: '快餐便当', categoryImg: 'https://fuss10.elemecdn.com/b/ff/533cf9617bd57fe1dfb05603bebcfpng.png'},
    {categoryName: '小吃夜宵', categoryImg: 'https://fuss10.elemecdn.com/4/35/a7eda7659bac613e524ca7c1ae12epng.png'},
    {categoryName: '果蔬生鲜', categoryImg: 'https://fuss10.elemecdn.com/6/23/5a6fce94bed63a21508f68a72c158png.png'},
    {categoryName: '特色菜系', categoryImg: 'https://fuss10.elemecdn.com/6/55/ac1bfd1e818013a9f099e964f1e9djpeg.jpeg'},
    {categoryName: '商店超市', categoryImg: 'https://fuss10.elemecdn.com/a/c1/5c5dd59b641bdfdeb822362547fb4png.png'},
    {categoryName: '鲜花蛋糕', categoryImg: 'https://fuss10.elemecdn.com/0/e0/7558e305abfb2618ae760142222f9png.png'},
    {categoryName: '甜品音频', categoryImg: 'https://fuss10.elemecdn.com/4/82/43703799592368585b23589cf3ba8png.png'}
  ]
  const subMenuList = [
    ['简餐', '盖浇饭', '米粉面馆', '包子粥店', '香锅砂锅', '麻辣烫', '饺子混沌', '汉堡'],
    ['小龙虾', '地方小吃', '烧烤', '炸鸡炸串', '鸭脖卤味', '零食'],
    ['水果', '生鲜', '蔬菜', '海鲜水产'],
    ['川湘菜', '江浙菜', '粤菜', '东北菜', '闽菜', '西北菜'],
    ['超市', '便利店', '母婴店'],
    ['鲜花', '蛋糕', '面包'],
    ['奶茶果汁', '甜品', '咖啡']
  ]
  const categoryList = [];
  for (let i = 0;i<topMenuList.length;i++){
    const category = {
      categoryId: i,
      categoryName: topMenuList[i].categoryName,
      categoryImg: topMenuList[i].categoryImg,
    };
    const subCategoryList = []
    let shopCount = 0
    for(let j = 0;j < subMenuList[i].length; j++){
      const subCategory = {
        categoryId: i + '' + j,
        categoryName: subMenuList[i][j],
        shopCount: randomInt(500) + 50
      }
      shopCount += subCategory.shopCount
      subCategoryList.push(subCategory)
    }
    category.subCategoryList = subCategoryList
    category.shopCount = shopCount
    categoryList.push(category)
  }
  res.send(result(
    categoryList
  ))
})

// 获取当前店铺信息
app.get('/shop/info', (req, res) => {
  const token = req.headers.token
  console.log(token)
  if (token === 'unregister-token'){
    res.send(result())
  }else {
    res.send(result({
      shopName: '黄焖鸡米饭'
    }))
  }
})

const randomPhone = () => {
  let str = "1"
  str += randomInt(9) + ''
          + randomInt(9)
          + '****' +
          + randomInt(9) + ''
          + randomInt(9) + ''
          + randomInt(9) + ''
          + randomInt(9) + ''
  return str          
}

const generateProductList = () => {
  const productMetadata = [
    { productName: '黄焖鸡米饭', productImg: 'http://www.shang360.com/upload/item/20170829/77643495931503977103_m.jpg'},
    { productName: '黄焖猪脚米饭', productImg: 'http://n1.itc.cn/img8/wb/smccloud/recom/2015/07/04/143597888154966028.JPEG'},
    { productName: '黄焖鸭米饭', productImg: 'https://cbu01.alicdn.com/img/ibank/2017/879/174/4278471978_452542804.jpg?__r__=1496373886684'},
    { productName: '豪大大鸡排', productImg: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=414659565,1925804957&fm=26&gp=0.jpg'},
    { productName: '黄焖腐竹升级版', productImg: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589258470736&di=41b941d408e2c3fc07444825f27f8651&imgtype=0&src=http%3A%2F%2Fcmsimgshow.zhuchao.cc%2F20431%2F20151229025835.jpg%3Fpath%3Dwww.haokeqilu.cn%2Fuploads%2Fcp%2F20151229025835.jpg'},
    { productName: '黄焖排骨', productImg: 'https://cp1.douguo.com/upload/caiku/5/9/5/690x390_59664ebdda727cb6d0e331851c053355.jpeg'},
  ]
  const specMetadata = ['大份', '中份', '小份']
  const n = randomInt(8) + 5
  const productList = []
  for(let i = 0;i<n;i++){
    const product = clone(productMetadata[randomInt(productMetadata.length)])
    product.productId = i
    product.specId = i
    product.specName = specMetadata[randomInt(specMetadata.length)]
    product.packageFee = randomInt(5) + 2
    product.productQuantity = randomInt(4) + 1
    product.productPrice = randomInt(20) + 5
    productList.push(product)
  }
  return productList
}

// 获取新订单列表
app.get('/shop/order/new', (req, res) => {
  const orderMetadata = [
    { consumerName: '蔡徐坤', consumerAddress: '泉州软件学院媒人河里面', consumerPhone: '17359581234', orderNote: '饭多一点谢谢'},
    { consumerName: '徐雪丽', consumerAddress: '泉州软件学院媒人河外面', consumerPhone: '17359588888', orderNote: '豪大大鸡排红茶套餐, 红茶换可乐'},
    { consumerName: '连云斌', consumerAddress: '泉州经贸学院男生宿舍B305', consumerPhone: '17359589999', orderNote: '芋圆波波奶茶不要芋圆不要奶茶'},
  ]
  const n = randomInt(8) + 5
  const orderList = []
  for(let i = 0;i<n;i++){
    const order = clone(orderMetadata[randomInt(orderMetadata.length)])
    order.orderId = i,
    order.orderStatus = randomInt(3)
    order.productList = generateProductList()
    order.dinnerOutCode = randomInt() + '' + randomInt() + '' + randomInt() + '' + randomInt()
    orderList.push(order)
  }
  res.send(result(orderList))
})

// 出餐
app.put('/shop/order/:orderId/out', (req, res) => {
  res.send(result({}))
})

// 获取订单列表
app.get('/shop/order/list', (req, res) => {
  const productMetadata = [
    '黄焖鸡米饭','黄焖猪脚米饭','黄焖鸭米饭','豪大大鸡排','黄焖腐竹升级版','黄焖排骨','冰阔乐','菊花茶','红牛','香辣鸡腿堡'
  ]
  const n = req.query.size
  const orderList = []
  for(let i = 0;i<n;i++){
    const order = {
      orderId: i
    }
    const productCount = randomInt(10) + 1
    if (productCount == 1) {
      order.orderName = productMetadata[randomInt(productMetadata.length)] + '等 1 件'
    }else {
      order.orderName = productMetadata[randomInt(productMetadata.length)]
        + ' + ' + productMetadata[randomInt(productMetadata.length)] + ' 等 ' + productCount + '件'
    }
    order.orderAmount = randomInt(20) + 10
    order.orderStatus = randomInt(3)
    order.payStatus = randomInt(3)
    orderList.push(order)
  }
  res.send(result({
    total: 1000,
    data: orderList
  }))
})
