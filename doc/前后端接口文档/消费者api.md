[TOC]

# 消费者API文档

## 接口说明

- 基准地址：api.consumer.blb.com
- 服务端已开启CORS跨域支持
- 认证统一采用token
- 授权的API，需要在请求头TOKEN字段提供token令牌
- 所有请求都必须在请求头TYPE提供值CONSUMER
- 数据统一返回结果

```json
{
  "code": 0,
  "msg": "string",
  "success": true,
  "data": ...
}
```

参数名     | 参数说明   | 备注
------- | ------ | -----
code    | 状态码    | 正常返回1
msg     | 响应消息   |
success | 请求是否成功 |
data    | 请求结果   |

## 获取店铺分类

- 请求方式：get
- url：/category/:level
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
level | 目录层级 | 可选值 1 2 3

- 响应参数

```json
[
  {
    "categoryId": 1,
    "categoryName": "顶级目录",
    "categoryImg": "blb.com/1.jpg",
    "subCategoryList": [
      {
        "categoryId": 1,
        "categoryName": "二级目录",
        "categoryImg": "blb.com/1.jpg",
        "subCategoryList": [...]
      }
      // ...
    ]

  }
  //...
]
```

## 根据分类获取店铺

- 方式：get
- url：/category/:categoryId/shop
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
categoryId | 店铺目录ID
location | 经纬度字符串 | 如117,29这种形式
page | 页数 | 默认为1
size | 每页展示数 | 默认为10

- 响应参数

```json
{
  "total":123, //总数据条数
  "data": [
    {
      "shopId": 1, // 店铺ID
      "shopLogo": "img.com/1.jpg", // 店铺logourl
      "shopName": "黄焖鸡米饭", // 店铺名称
      "ranking": 3.7, // 店铺评分
      "distance": "1.47KM", // 店铺距离,
      "sales": 12, // 店铺商品销售数
      "startingPrice": 14, // 起送价
      "deliveryFee": 2, // 配送费
      "deliveryTime": "38分钟" // 所需配送时间
    }
    ,
    // ...后面还有shop item
  ]
}
```

## 登录接口

- 请求方式：POST
- 请求URL：/login
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
username | 用户名 |
password | 密码 |

- 响应参数

参数名     | 参数说明   | 备注
------- | ------ | -----
token | 后续请求凭证|
greeting | 登录提示语 |
username| 用户名|
userId| 用户ID|
avatar | 用户头像|

## 获取当前登录用户信息

- 方式：get
- url：/info
- 响应参数

```json
{
  "avatar":"头像",
  "username":"cxk",
  "realName":"蔡徐坤",
  "phone":"17385469123",
  "email":"xxx"
}
```

## 注册接口

- 请求方式：POST
- URL:/register
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
username | 用户名
phone | 手机
email | 邮箱
password | 密码

- 响应参数

```json
{
  "greeting": "欢迎语",
  "userNumber":3 // 当前注册用户是第几位注册用户
}
```

## 获取附近店铺

- 方式：get
- url：/shop/vicinity
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
location | 经纬度字符串 | 如117,29这种形式
page | 页数 | 默认为1
size | 每页展示数 | 默认为10

- 响应参数

```json
{
  "total":123, //总数据条数
  "data": [
    {
      "shopId": 1, // 店铺ID
      "shopLogo": "img.com/1.jpg", // 店铺logourl
      "shopName": "黄焖鸡米饭", // 店铺名称
      "ranking": 3.7, // 店铺评分
      "distance": "1.47KM", // 店铺距离,
      "sales": 12, // 店铺商品销售数
      "startingPrice": 14, // 起送价
      "deliveryFee": 2, // 配送费
      "deliveryTime": "38分钟" // 所需配送时间
    }
    ,
    // ...后面还有shop item
  ]
}
```

## 获取店铺信息

- 方式：get
- url：/shop/info/:id
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID

- 响应参数

```json
{
  "shopLogo": "img.blb.com/1.jpg",
  "shopName": "黄焖鸡米饭",
  "deliveryMethod": "蜂鸟专送", // 配送方式
  "deliveryTime": "35分钟",
  "startingPrice": 14, // 起送价
  "deliveryFee": 2, // 配送费
  "shopSlogan": "欢迎光临本店，随时可以下单", // 店铺简介
  "sellerName": "蔡徐坤", // 卖家真实姓名
  "shopAddress": "鲤城区媒人桥路2号", // 店铺地址
  "shopPhone": "17359561234" // 店铺联系电话
}
```

## 获取店铺商品分类列表

- 方式：get
- url：/shop/:id/category
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID

- 响应参数

```json
[
  {
    "categoryId": 1, // 目录ID
    "categoryName": "黄焖系列",
    "categoryDesc": "本店招牌系列菜"
  }
  // others ...
]
```

## 获取商品列表

- 方式：get
- url：/shop/:id/:categoryId/product/
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID
categoryId | 商品分类ID

- 响应参数

```json
[
  {
    "productId": 1,
    "productName": "黄焖鸡米饭",
    "productImg": "img.blb.com/1.jpg",
    "productDesc": "江西特产黄焖鸡米饭",
    "specList": [ // 商品规格列表
      {
        "specId":1,
        "specName": "大份",
        "packageFee":2, // 包装费
        "price": 18, // 规格价格
        "stock": 100
      }
      // ...
    ],
    "sales": 123, // 商品销量
    "positiveRate": 59
  }
  // ...
]
```

## 获取店铺评价信息

- 方式：get
- url：/shop/:id/evaluation
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID

- 响应参数

```json
{
  "rating": 4.7, // 店铺评分
  "wordCloud": ["味道好","干净卫生","价格便宜"]
}
```

### 获取店铺评价列表

- 方式：get
- url：/shop/:id/evaluation/list
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID
page | 页码 | 默认为1
size | 每页显示数 | 默认为10

- 响应参数

```json
{
  "total":15,
  "data": [
    {
      "nickName": "cxk", // 评价用户昵称
      "ranking": 3.8,
      "content": "不错，不错，味道好极了",
      "createTime": "2020-05-04 11:20:38"
    }
    // ...
  ]
}
```

## 获取购物车列表

- 方式：get
- url：/shop/:id/cart
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID

- 响应参数

```json
[
  {
    "productId":1,
    "specId":1,
    "productName":"黄焖鸡米饭",
    "productImg":"blb.com/1.jpg",
    "specName": "大份",
    "packageFee": 2,
    "productQuantity": 2,
    "productPrice": 18
  }
  //...
]
```

## 加入购物车

- 方式：put
- url：/shop/:id/cart/:productId/:specId
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID
productId | 商品ID
specId | 规格ID
quantity | 数量

## 删除购物车商品

- 方式：delete
- url：/shop/:id/cart/:productId/:specId
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID
productId | 商品ID
specId | 规格ID

## 清空购物车

- 方式：delete
- url：/shop/:id/cart/
- 请求参数

| 参数名 | 参数说明 | 备注 |
| ------ | -------- | ---- |
| id     | 店铺ID   |      |

## 获取默认收货地址

- 方式：get
- url：/delivery/default
- 响应参数

```json
{
  "deliveryId": 1,
  "building": "泉州师范学院软件学院",
  "detail": "男生宿舍B305",
  "realName": "蔡徐坤",
  "phone": "17359561234"
}
```


## 提交订单

- 方式：post
- url：/shop/order
- 请求参数

```json
{
  "deliveryId": 1,
  "orderNote": "米饭多一点",
  "productList": [
    {
      "productId": 1,
      "specId": 1,
      "quantity": 1
    }
    // ...
  ]
}
```

- 响应参数

参数名     | 参数说明   | 备注
------- | ------ | -----
default | orderId

## 生成支付

- 请求方式：post
- url：/pay/order/:orderId
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
orderId | 订单ID | 路径参数
type |支付类型 | 0 为支付宝

- 响应参数

参数名     | 参数说明   | 备注
------- | ------ | -----
default | payId

## 获取支付二维码

- 请求方式：get
- url：/pay/:payId
- 响应参数

参数名     | 参数说明   | 备注
------- | ------ | -----
default | 二维码链接 | 需要前端自己生成二维码

## 查询支付状态

- 请求方式：get
- url：/pay/status/:payId
- 响应参数

```json
{
  "status": 0, // 0 1 2 3分别为等待支付 已扫码 已支付 已取消
  "msg": "等待支付" // 支付状态文本表示
}
```

## 搜索店铺

- 方式：get
- url：/shop/search
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
kw | 搜索关键字
page | 页数 | 默认为1
size | 每页展示数 | 默认为10

- 响应参数

```json
{
  "total":123, //总数据条数
  "data": [
    {
      "shopId": 1, // 店铺ID
      "shopLogo": "img.com/1.jpg", // 店铺logourl
      "shopName": "黄焖鸡米饭", // 店铺名称
      "ranking": 3.7, // 店铺评分
      "distance": "1.47KM", // 店铺距离,
      "sales": 12, // 店铺商品销售数
      "startingPrice": 14, // 起送价
      "deliveryFee": 2, // 配送费
      "deliveryTime": "38分钟" // 所需配送时间
    }
    ,
    // ...后面还有shop item
  ]
}
```

## 获取订单列表

- 方式：get
- url：/order
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
page | 页数 | 默认为1
size | 每页展示数 | 默认为10

- 响应参数

```json
[
  {
    "orderId":1,
    "shopId": 1,
    "shopName": "黄焖鸡米饭",
    "shopLogo": "blb.com/1.jpg",
    "orderAmount": 52.5,
    "payStatus": 0,
    "orderDesc": "黄焖鸡米饭 + 菊花茶 等 2件",
    "createTime": "2020-05-05 20:07:06"
  }
  //...
]
```

## 获取订单详情

- 方式：get
- url：/shop/order/:orderId
- 响应参数

```json
{
  "orderId": 1,
  "shopId": 1,
  "shopName": "黄焖鸡米饭",
  "shopLogo": "blb.com/logo.jpg",
  "productList": [
    {
      "productId":1,
      "productName": "黄焖鸡",
      "productImg": "blb.com/1.JPG",
      "specId": 1,
      "specName": "大份",
      "productQuantity": 1,
      "productPrice": 18
    }
    //...
  ],
  "orderAmount": 305.90,
  "consumerAddress": "泉州软件学院 男生宿舍B305",
  "consumerName":"蔡徐坤",
  "consumerPhone":"173599567123",
  "orderNote": "芋圆波波奶茶不要芋圆不要奶茶",
  "orderStaut":0,
  "payStatus":0,
  "createTime": "2020-05-05 20:07:06"
}
```

## 更新用户信息

- 方式：POST
- url：/info
- 请求参数

```json
{
  "avatar": "blb.com/1.jpg",
  "username": "new username",
  "phone": "1739569185693"
}
```

## 修改用户密码

- 方式：POST
- url：/info/password
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
oldPassword|旧密码
newPassword | 新密码

## 拉取收货信息列表

- 方式：get
- url：/delivery
- 响应参数

```json
{
  "deliveryId": 1,
  "building": "泉州软件学院",
  "detail": "男生宿舍B305",
  "realName": "蔡徐坤",
  "phone": "173956173456"
}
```

## 更新收货信息

- 方式：PUT
- url：/delivery/:deliveryId
- 请求参数

```json
{
  "building":"泉州师范学院软件学院",
  "detail":"路边摊2号",
  "defaultDelivery":true
}
```

## 新增收货信息

- 方式：POST
- url：/delivery
- 请求参数

```json
{
  "building":"泉州师范学院软件学院",
  "detail":"路边摊2号",
  "defaultDelivery":true
}
```

## 删除收货信息

- 方式：DELETE
- url：/delivery/:deliveryId
- 请求参数

## 头像上传

- 方式：POST
- url：/avatar
- 请求参数

file: 文件内容