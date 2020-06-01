# 骑手API

## 接口说明

- 基准地址：api.rider.blb.com
- 服务端已开启CORS跨域支持
- 认证统一采用token
- 授权的API，需要在请求头TOKEN字段提供token令牌
- 所有请求都必须在请求头TYPE提供值RIDER
- 数据统一返回结果

```json
{
  "code": 0,
  "msg": "string",
  "success": true,
  "data": ...
}
```

## 注册

- POST
- url：/register
- 请求参数

```json
{
  "username": "cxk",
  "phone": "17835961234",
  "email": "admin@xx.com",
  "realName": "蔡徐坤",
  "idNumber": "360534123467834231",
  "password": "123"
}
```

- 返回参数

注册提示语

## 登录

- POST
- url：/login
- 请求参数

参数名     | 参数说明   | 备注
------- | ------ | -----
username | 用户名 | 
password | 密码 | 

- 响应参数

```json
{
  "token":"token",
  "greeting": "欢迎登录"
}
```

## 获取未完结订单

- GET
- url：/order/undelivery
- 响应参数

```json
{
  "orderId": 1,
  "dinnerOutCode": "3521",
  "takeMealCode": "3521",
  "shopName": "黄焖鸡米饭",
  "shopAddress": "店铺地址"
}
```

## 获取待配送订单列表

- GET
- url：/order/delivery
- 响应参数

```json
[
  {
    "orderId": 1,
    "orderName": "黄焖鸡米饭 - cxk 的订单",
    "orderAmount": 35
  }
]
```

## 接单

- POST
- url：/order/grab/:orderId
- 响应参数

接单提示语

## 获取订单详情

- GET
- url：/order/detail/:orderId
- 响应参数

```json
{
  "orderId": 1,
  "dinnerOutCode": "3521",
  "takeMealCode": "3521",
  "shopName": "黄焖鸡米饭",
  "shopAddress": "店铺地址"
}
```

## 订单配送中

- POST
- url：/order/:orderId/delivery

## 订单完结

- POST
- url：/order/:orderId/complete