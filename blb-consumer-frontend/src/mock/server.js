const { response } = require('express')
const express = require('express')
const result = require('./result')
const { v4: uuidv4 } = require('uuid');

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
    res.send(result({name:'blb consumer mock server'})) 
})

// 获取目录
app.get('/category/:level', (req, res) => {
  const topMenuList = [
    {categoryName: '快餐便当', categoryImg: '/imgs/hamburg.png'},
    {categoryName: '小吃夜宵', categoryImg: '/imgs/street_food.png'},
    {categoryName: '果蔬生鲜', categoryImg: '/imgs/fruit_vegetable.png'},
    {categoryName: '特色菜系', categoryImg: '/imgs/special.jpeg'},
    {categoryName: '商店超市', categoryImg: '/imgs/supermarket.png'},
    {categoryName: '鲜花蛋糕', categoryImg: '/imgs/cake.png'},
    {categoryName: '甜品饮品', categoryImg: '/imgs/juice.png'}
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

const randomInt = bound => {
  return Math.ceil(Math.random() * bound) -1
}

const clone = origin => {
  return Object.assign({}, origin)
}

const shopMetadata = [
  {shopLogo: '/imgs/shop_logos/mixilan.jpg', shopName: '米兰西饼生日蛋糕'},
  {shopLogo: '/imgs/shop_logos/yingyuan.jpg', shopName: 'e+咖啡私人影院'},
  {shopLogo: '/imgs/shop_logos/xiang.jpg', shopName: '57°C湘'},
  {shopLogo: '/imgs/shop_logos/huoguo.jpg', shopName: '火锅咖·自选火锅'},
  {shopLogo: '/imgs/shop_logos/kafei.jpg', shopName: '元品咖啡'},
  {shopLogo: '/imgs/shop_logos/hani.jpg', shopName: '哈尼小站'},
  {shopLogo: '/imgs/shop_logos/dekeshi.jpg', shopName: '德克士'},
]

// 随机生成店铺列表
const generateShopList = (req, res) => {
  const shopList = []
  for(let i = 0;i<req.query.size;i++){
    shopList.push(Object.assign({}, shopMetadata[randomInt(shopMetadata.length)]))
  }
  for(let i = 0;i<shopList.length;i++){
    shopList[i].shopId = i
    shopList[i].ranking = parseFloat((Math.random() * 5).toFixed(1))
    shopList[i].distance = (Math.random() * 3).toFixed(2) + ''
    shopList[i].sales = parseInt(Math.random() * 1000)
    shopList[i].startingPrice = parseInt(Math.random() * 10)
    shopList[i].deliveryFee = parseInt(Math.random() * 5)
    shopList[i].deliveryTime = parseInt(Math.random() * 60) + '分钟'
  }
  res.send(result(
    {
      total: 1000,
      data: shopList
    }
  ))
}

// 根据分类获取店铺
app.get('/category/:cateId/shop', (req, res) => {
  generateShopList(req, res)
})

// 登录
app.post('/login', (req, res) => {
  res.send(result({
    token: 'fake-token',
    greeting: '欢迎登录',
    username: req.query.username,
    avatar: '/imgs/avatar.jpg'
  }))
})

// 获取当前登录用户信息
app.get('/info', (req, res) => {
  const token = req.headers.token
  if (token === 'fake-token') {
    res.send(result({
      avatar: '/imgs/avatar.jpg',
      username: 'My',
      realName: '蔡徐坤',
      phone: '17359561234',
      email: 'admin@ismy.wang'
    }))
  }else {
    res.send(result({}, false, '未登录'))
  }
})

// 注册
app.post('/register', (req, res) => {
  res.send(result({
    greeting: "欢迎您",
    userNumber: 9999999
  }))
})

// 获取附近店铺信息
app.get('/shop/vicinity', (req, res) => {
  if (!req.query.size) {
    req.query.size = 12
  }
  generateShopList(req, res)
})

// 获取店铺信息
app.get('/shop/info/:id', (req, res) => {
  res.send(result({
    shopLogo: '/imgs/shop_logos/huangmenji.jpg',
    shopName: '黄焖鸡米饭',
    deliveryMethod: '蜂鸟专送',
    deliveryTime: parseInt(Math.random() * 60) + '分钟',
    deliveryFee: parseInt(Math.random() * 10),
    shopSlogan: '欢迎光临本店，随时可以下单',
    sellerName: '蔡徐坤',
    shopAddress: '鲤城区媒人桥路2号',
    startingPrice: parseInt(Math.random() * 20),
    shopPhone: '17359561234'
  }))
})

// 获取店铺商品分类列表
app.get('/shop/:id/category', (req,res) => {
  const metadata = [
    { categoryName: '🥘黄焖系列', categoryDesc: '本店招牌系列菜'},
    { categoryName: '🍗快餐系列', categoryDesc: '做得快 凉得快'},
    { categoryName: '🥣炖汤系列', categoryDesc: '美味营养炖汤'},
    { categoryName: '🥗家常炒菜', categoryDesc: '十分钟内完成的炒菜'},
    { categoryName: '🍔汉堡可乐', categoryDesc: '美味汉堡 肥宅快乐水'},
    { categoryName: '🍜面食系列', categoryDesc: '精品小麦制成'},
    { categoryName: '🍶奶茶饮品', categoryDesc: '美味汉堡 肥宅快乐水'},
  ]
  const categoryList = []
  const n = randomInt(5) + 5
  for(let i = 0;i<n;i++){
    const category = clone(metadata[randomInt(metadata.length)])
    category.categoryId = i
    categoryList.push(category)
  }
  res.send(result(categoryList))
})

const productMetadata = [
  { productName: '黄焖鸡米饭', productImg: '/imgs/foods/huangmenji.jpg', productDesc: '香香甜甜的黄焖鸡米饭'},
  { productName: '黄焖猪脚米饭', productImg: '/imgs/foods/huangmenzhujiao.jpg', productDesc: '香香甜甜的黄焖脚米饭'},
  { productName: '黄焖鸭米饭', productImg: '/imgs/foods/huangmenya.jpg', productDesc: '香香甜甜的黄焖鸭米饭'},
  { productName: '豪大大鸡排', productImg: '/imgs/foods/jipai.jpg', productDesc: '香喷喷的豪大大鸡排'},
  { productName: '黄焖腐竹升级版', productImg: '/imgs/foods/huangmenfuzhu.jpg', productDesc: '升级版黄焖腐竹'},
  { productName: '黄焖排骨', productImg: '/imgs/foods/huangmenpaigu.jpeg', productDesc: '香香甜甜的黄焖排骨'},
]

// 获取商品列表
app.get('/shop/:id/:categoryId/product', (req, res) => {
  const specMetadata = [
    { specName: '大份' }, { specName: '小份'}, {specName: '中份'}
  ]
  const n = randomInt(5) + 5
  const productList = []
  for(let i = 0;i<n;i++){
    const product = clone(productMetadata[randomInt(productMetadata.length)])
    product.productId = i
    product.sales = randomInt(200) + 50
    product.positiveRate = randomInt(99)
    product.productSpecList = []
    const m = randomInt(3) + 1
    for(let j =0;j<m;j++){
      const spec = clone(specMetadata[randomInt(specMetadata.length)])
      spec.specId = j
      spec.packageFee = randomInt(2) + 3
      spec.price = randomInt(20) + 5
      spec.stock = randomInt(100) + 1
      product.productSpecList.push(spec)
    }
    productList.push(product)
  }
  res.send(result(productList))
})

// 获取店铺评价信息
app.get('/shop/:id/evaluation',(req,res) => {
  const metadata = [
    { content: "满意", positive: true},
    { content: "不满意", positive: false},
    { content: "有图", positive: true},
    { content: "味道好", positive: true},
    { content: "送货快", positive: true},
    { content: "分量足", positive: true},
    { content: "包装精美", positive: true},
    { content: "干净卫生", positive: true},
    { content: "食材新鲜", positive: true},
    { content: "服务不错", positive: true},
    { content: "态度恶劣", positive: false},
    { content: "以次充好", positive: false},
  ]
  const n = randomInt(10) + 5
  wordcloud = []
  for(let i = 0;i<n;i++){
    s = clone(metadata[randomInt(metadata.length)])
    s.id = i
    s.count = randomInt(50) + 10
    wordcloud.push(s)
  }
  res.send(result({
    rating: (Math.random() * 5).toFixed(2),
    wingRate: randomInt(80) + 20,
    wordCloud: wordcloud
  }))
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

// 获取店铺评价列表
app.get('/shop/:id/evaluation/list', (req, res) => {
  const userMetadata = ['Cxk', 'Xxl', 'My', 'Tyy', 'Pdd', 'E170D', 'Linus', 'Jack', 'Mary', 'JK', 'Rose']
  const contentMetadata = [
    '不错！味道好极了',
    '店家服务态度不错！！',
    '没有人比我更懂评论',
    '分量足 态度优 好评！！！',
    '菜品很多，很开心',
    '第一次刷好评，好紧张啊啊啊！！！',
    '第一次刷好评，好紧张啊啊啊！！！',
    '太贵了 店家的服务态度也不好',
    '这家店不咋地 还设有最低消费 太坑爹了'
  ]
  const imgMetadata = [
    '/imgs/eval/1.jpg',
    '/imgs/eval/2.jpg',
    '/imgs/eval/3.jpg',
    '/imgs/eval/4.jpg',
    '/imgs/eval/5.jpg'
  ]
  const evalSize = parseInt(req.query.size)
  const evalList = []
  for(let i =0;i<evalSize;i++){
    const date = new Date()
    const evaluation = {
      evalId: i,
      nickName: userMetadata[randomInt(userMetadata.length)],
      phone: randomPhone(),
      ranking: (Math.random() * 5).toFixed(1),
      content: contentMetadata[randomInt(contentMetadata.length)],
      createTime: new Date()
    }
    evaluation.imgList = []
    const n = randomInt(imgMetadata.length) + 1
    for(let j =0;j<n;j++){
      evaluation.imgList.push(imgMetadata[randomInt(imgMetadata.length)])
    }
    evalList.push(evaluation)
  }
  res.send(result({
    total: 1000,
    data: evalList
  }))
})

const generateProductList = () => {
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

// 获取购物车列表
app.get('/shop/:id/cart', (req, res) => {
  
  res.send(result(generateProductList()))
})

// 加入购物车
app.put('/shop/:id/cart/:productId/:specId', (req, res) => {
  res.send(result({}))
})

// 删除购物车商品
app.delete('/shop/:id/cart/:productId/:specId', (req, res) => {
  res.send(result({}))
})

// 清空购物车
app.delete('/shop/:id/cart', (req, res) => {
  res.send(result({}))
})

//获取默认收货地址
app.get('/delivery/default', (req, res) => {
  res.send(result({
    deliveryId: 1,
    building: "泉州师范学院软件学院",
    detail: "男生宿舍B305",
    realName: "蔡徐坤",
    phone: "17350561123"
  }))
})

// 提交订单
app.post('/shop/order', (req, res) => {
  res.send(result('1'))
})

// 生成支付
app.post('/pay/order/:orderId', (req, res) => {
  res.send(result('1'))
})

// 获取支付二维码
app.get('/pay/:payId', (req, res) => {
  res.send(result({
    url: 'http://baidu.com',
    shopName: '黄焖鸡米饭',
    orderId: '368 990 455 266'
  }))
})

// 查询支付状态
app.get('/pay/status/:payId', (req, res) => {
  res.send(result({
    stats: 0,
    msg: '等待支付'
  }))
})

// 搜索店铺
app.get('/shop/search',(req, res) => {
  req.query.size = 12
  generateShopList(req, res)
})

// 获取订单列表
app.get('/shop/order', (req, res) => {
  const n = parseInt(req.query.size)
  const orderList = []
  for(let i = 0;i<n;i++){
    const order = clone(shopMetadata[randomInt(shopMetadata.length)])
    order.orderId = uuidv4()
    order.shopId = i
    order.orderAmount = randomInt(50) + 10
    order.orderStatus = randomInt(4)
    const productCount = randomInt(10) + 1
    if (productCount == 1) {
      order.orderName = productMetadata[randomInt(productMetadata.length)].productName + '等 1 件'
    }else {
      order.orderName = productMetadata[randomInt(productMetadata.length)].productName
        + ' + ' + productMetadata[randomInt(productMetadata.length)].productName + ' 等 ' + productCount + '件'
    }
    const imgSize = randomInt(4) + 1
    order.imgList = []
    for(let i = 0;i<imgSize;i++){
      order.imgList.push(productMetadata[randomInt(productMetadata.length)].productImg)
    }
    order.createTime = new Date()
    orderList.push(order)
  }
  res.send(result({
    total: 1000,
    data: orderList
  }))
})

// 获取订单详情
app.get('/shop/order/:orderId', (req, res) => {
  const productList = generateProductList()
  const order = {
    orderId: 1,
    shopId: 1,
    shopName: '米兰西饼生日蛋糕',
    shopLogo: '/imgs/shop_logos/mixilan.jpg',
    deliveryFee: randomInt(5) + 2,
    productList,
    orderAmount: randomInt(300) + 50,
    consumerAddress: '泉州软件学院 男生宿舍B305',
    consumerName: '蔡徐坤',
    consumerPhone: '173599567123',
    orderNote: '芋圆波波奶茶不要芋圆不要奶茶',
    orderStatus: randomInt(4),
    payStatus: randomInt(4),
    createTime: new Date()
  }
  res.send(result(order))
})

// 更新用户信息
app.post('/info', (req, res) => {
  res.send(result({}))
})

// 修改用户密码
app.post('/info/password', (req, res) => {
  res.send(result({}))
})

// 拉取收货信息列表
app.get('/delivery', (req, res) => {
  const addressMetadata = [
    { building: '泉州师范学院软件学院', detail: '男生宿舍B306'},
    { building: '鲤城区媒人桥路2号', detail: '男生宿舍B305'},
    { building: '南环路媒人桥河', detail: '马路边第13根柱子'},
  ]
  const personMetadata = [
    { realName: '蔡徐坤', phone: '1731231234'},
    { realName: '徐雪莉', phone: '1731238888'},
    { realName: '刘云峰', phone: '1731239876'},
  ]
  const n = randomInt(5) + 3
  const deliveryList = []
  for(let i = 0;i<n;i++){
    const delivery = clone(addressMetadata[randomInt(addressMetadata.length)])
    const person = clone(personMetadata[randomInt(personMetadata.length)])
    delivery.deliveryId = i
    delivery.realName = person.realName
    delivery.phone = person.phone
    deliveryList.push(delivery)
  }
  res.send(result(deliveryList))
})

// 更新收货信息
app.put('/delivery/:deliveryId', (req, res) => {
  res.send(result({}))
})

// 新增收货信息
app.post('/delivery', (req, res) => {
  res.send(result({}))
})

// 删除收货信息
app.delete('/delivery/:deliveryId', (req, res) => {
  res.send(result({}))
})

// 头像上传
app.post('/avatar', (req, res) => {
  res.send(result({}))
})
