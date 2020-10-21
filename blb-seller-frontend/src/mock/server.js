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