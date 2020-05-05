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
username| 用户名
userId| 用户ID
avatar | 用户头像

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

## 获取附近商家

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
  "shopName": "黄焖鸡米饭",
  "deliveryMethod": "蜂鸟专送", // 配送方式
  "deliveryTime": "35分钟",
  "startingPrice": 14, // 起送价
  "deliveryFee": 2, // 配送费
  "slogan": "欢迎光临本店，随时可以下单", // 店铺简介
  "sellerName": "蔡徐坤", // 卖家真实姓名
  "shopAddress": "鲤城区媒人桥路2号", // 店铺地址
  "shopPhone": "17359561234" // 店铺联系电话
}
```

## 获取店铺分类列表

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
- url：/shop/:id/product
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
id | 店铺ID

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

## 获取店铺评价

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
  "wor"
}
```