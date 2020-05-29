# 商家api

## 接口说明

- 基准地址：api.seller.blb.com
- 服务端已开启CORS跨域支持
- 认证统一采用token
- 授权的API，需要在请求头TOKEN字段提供token令牌
- 所有请求都必须在请求头TYPE提供值SELLER
- 数据统一返回结果

```json
{
  "code": 0,
  "msg": "string",
  "success": true,
  "data": ...
}
```

## 登录/注册

- 方式：POST
- url：/login
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
username | 用户名 | 
password | 密码 | 

- 响应参数

```json
{
  "greeting": "注册欢迎语",
   "token":"xxx",
    "status": 0
}
```

## 店铺注册

- 方式：POST
- url：/shop/register
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
shopName | 店铺名称
shopAddress | 店铺地址
categoryId | 店铺分类ID
shopLogo | 店铺LOGO
shopDesc | 店铺简介
shopSlogan | 店铺slogan
businessHour | 营业时间
phone | 店铺联系电话
deliveryFee | 配送费
startingPrice | 起送价
businessLicense | 营业执照
foodServiceLicense | 食品服务许可证

## 图片上传

- 方式：POST
- url：/image
- 请求参数

file: 文件内容

## 获取店铺分类

- 请求方式：get
- url：/shop/category
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

## 获取当前店铺信息

- 方式：get
- url：/shop/info
- 响应参数

```json
{
  "shopName":"黄焖鸡米饭",
  ...
}
```

## 获取新订单列表

- 方式：get
- url：/shop/order/new
- 响应参数

```json
[
  {
    "orderId": 1,
    "orderStatus": 0,
    "consumerName": "王小明",
    "consumerAddress": "泉州师范学院软件学院 男B305",
    "consumerPhone": "17359561234",
    "diningOutCode": "2981", //   骑手取餐码
    "orderNote": "黄焖鸡不要辣",
    "productList": [
      {
        "productId": 1,
        "productName": "黄焖鸡",
        "specId": 1,
        "specName": "大份",
        "productQuantity": 1,
        "productPrice": 13
      }
      //...
    ]
  },
  ...
]
```

## 出餐

- 方式：PUT
- url：/shop/order/:orderId/out
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
orderId | 订单ID

## 获取订单列表

- 方式：get
- url：/shop/order/list
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
startDate | 开始日期 | 与结束日期可以一起为空
endDate | 结束日期 | 与开始日期可以一起为空
kw | 搜索关键字 | 可以为空
page | 页数 | 默认为1
size | 每页展示数 | 默认为10

- 响应参数

```json
{
  "total": 15,
  "data": [
    {
      "orderId":1,
      "orderName": "xxx",
      "orderAmount": 15,
      "orderStatus": 0,
      "payStatus": 1
    },
    ...
  ]
}
```

## 获取订单详情

- 方式:get
- url:/shop/order/:orderId
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
orderId | 订单ID

- 响应参数

```json
{
    "orderId": 1,
    "orderStatus": 0,
    "consumerName": "王小明",
    "consumerAddress": "泉州师范学院软件学院 男B305",
    "consumerPhone": "17359561234",
    "diningOutCode": "2981", //   骑手取餐码
    "orderNote": "黄焖鸡不要辣",
    "productList": [
      {
        "productId": 1,
        "productName": "黄焖鸡",
        "specId": 1,
        "specName": "大份",
        "productQuantity": 1,
        "productPrice": 13
      }
      //...
    ]
}
```

## 获取店铺商品分类

- 方式：get
- url：/shop/product/category
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

## 新增商品分类

- 方式：POST
- url：/shop/category
- 请求参数

```json
{
  "categoryName": "目录名称",
  "orderDesc": "目录描述"
}
```

## 更新商品分类

- 方式：PUT
- url：/shop/category/:id
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID

```json
{
  "categoryName": "新目录名称",
  "categoryDesc": "新描述"
}
```

## 删除目录

- 方式：DELETE
- url：/shop/category/:id

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID

## 根据目录获取商品列表

- 方式：get
- url：/shop/category/:id/product
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID

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

## 新增商品

- 方式：POST
- url：/shop/category/:id/product
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID

```json
{
  "categoryId": 1,
  "productName": "黄焖腐竹",
  "productSpec": "香喷喷黄焖腐竹",
  "productImg": "blb.com/1.jpg",
  "productSpecList": [
    {
      "specName": "大份",
      "packageFee": 2,
      "price": 15
    }
    ...
  ]
}
```

## 更新商品

- 方式：PUT
- url：/shop/category/:id/product
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID

```json
{
  "productId": 1,
  "categoryId": 1,
  "productName": "黄焖腐竹",
  "productSpec": "香喷喷黄焖腐竹",
  "productImg": "blb.com/1.jpg",
  "productSpecList": [
    {
      "specId": 1,
      "specName": "大份a",
      "packageFee": 2,
      "price": 15
    },
    {
      "specName": "大份b",
      "packageFee": 2,
      "price": 17
    }
    ...
  ]
}
```

## 下架商品

- 方式：DELETE
- url：/shop/category/:id/product/:productId
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID
productId | 商品ID

## 更新库存

- 方式：PUT
- url：/shop/category/:id/product/:productId/:specId/stock
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 目录ID|
productId | 商品ID|
specId | 分类ID |
type | 更新库存的类型 | full或empty

## 更新店铺信息

- PUT
- url：/shop/info
- 请求参数

```json
{
  "shopLogo": "logo.jpg",
  "shopName": "黄焖鸡米饭(鲤城店)",
  "shopDesc": "店铺简介",
  "shopSlogan": "让生活更美好",
  "businessHour": "8:00-24:00",
  "deliveryFee": 5,
  "startingPrice": 15
}
```

## 获取商家信息

- GET
- url：/seller/info