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
  "sellerNumber": 3 // 当前商家数量
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
  }
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