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
    res.send(result({name:'blb consumer mock server'})) 
})

// 获取目录
app.get('/category/:level', (req, res) => {
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
    for(let j = 0;j < subMenuList[i].length; j++){
      const subCategory = {
        categoryId: i + '' + j,
        categoryName: subMenuList[i][j]
      }
      subCategoryList.push(subCategory)
    }
    category.subCategoryList = subCategoryList
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

// 随机生成店铺列表
const generateShopList = (req, res) => {
  const metadata = [
    {shopLogo: 'https://p0.meituan.net/bbia/c63505335fd950e3a56d352fe4be41eb138325.jpg@220w_125h_1e_1c', shopName: '米兰西饼生日蛋糕'},
    {shopLogo: 'https://p1.meituan.net/600.600/shopmainpic/6cb402acf097539bb9f1a9be49a023e3124761.jpg@220w_125h_1e_1c', shopName: 'e+咖啡私人影院'},
    {shopLogo: 'https://p0.meituan.net/600.600/deal/__12303698__9660676.jpg@220w_125h_1e_1c', shopName: '57°C湘'},
    {shopLogo: 'https://img.meituan.net/msmerchant/54fb990f3c02532a3255f020c82edc9f1432759.png@220w_125h_1e_1c', shopName: '火锅咖·自选火锅'},
    {shopLogo: 'https://p0.meituan.net/600.600/bbia/c58ff676ad214e99f39de19e682e96c5606999.jpg@220w_125h_1e_1c', shopName: '元品咖啡'},
    {shopLogo: 'https://img.meituan.net/600.600/msmerchant/176c18daf749328483e2754a4e898e1443278.jpg@220w_125h_1e_1c', shopName: '哈尼小站'},
    {shopLogo: 'https://img.meituan.net/600.600/msmerchant/82843020c1277ed2fa7620b2b1c385b9175314.jpg@220w_125h_1e_1c', shopName: '德克士'},
  ]
  const shopList = []
  for(let i = 0;i<req.query.size;i++){
    shopList.push(Object.assign({}, metadata[randomInt(metadata.length)]))
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
    avatar: 'https://pic1.zhimg.com/v2-89b980f785a7d9dd1068fb9d171ed6cd_is.jpg'
  }))
})

// 获取当前登录用户信息
app.get('/info', (req, res) => {
  const token = req.headers.token
  if (token === 'fake-token') {
    res.send(result({
      avatar: 'https://pic1.zhimg.com/v2-89b980f785a7d9dd1068fb9d171ed6cd_is.jpg',
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
    shopLogo: 'https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3174175902,1222382505&fm=26&gp=0.jpg',
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

// 获取商品列表
app.get('/shop/:id/:categoryId/product', (req, res) => {
  const productMetadata = [
    { productName: '黄焖鸡米饭', productImg: 'http://www.shang360.com/upload/item/20170829/77643495931503977103_m.jpg', productDesc: '香香甜甜的黄焖鸡米饭'},
    { productName: '黄焖猪脚米饭', productImg: 'http://n1.itc.cn/img8/wb/smccloud/recom/2015/07/04/143597888154966028.JPEG', productDesc: '香香甜甜的黄焖脚米饭'},
    { productName: '黄焖鸭米饭', productImg: 'https://cbu01.alicdn.com/img/ibank/2017/879/174/4278471978_452542804.jpg?__r__=1496373886684', productDesc: '香香甜甜的黄焖鸭米饭'},
    { productName: '豪大大鸡排', productImg: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=414659565,1925804957&fm=26&gp=0.jpg', productDesc: '香喷喷的豪大大鸡排'},
    { productName: '黄焖腐竹升级版', productImg: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589258470736&di=41b941d408e2c3fc07444825f27f8651&imgtype=0&src=http%3A%2F%2Fcmsimgshow.zhuchao.cc%2F20431%2F20151229025835.jpg%3Fpath%3Dwww.haokeqilu.cn%2Fuploads%2Fcp%2F20151229025835.jpg', productDesc: '升级版黄焖腐竹'},
    { productName: '黄焖排骨', productImg: 'https://cp1.douguo.com/upload/caiku/5/9/5/690x390_59664ebdda727cb6d0e331851c053355.jpeg', productDesc: '香香甜甜的黄焖排骨'},
  ]
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