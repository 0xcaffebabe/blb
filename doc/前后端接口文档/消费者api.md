[TOC]

# 消费者API文档

## 接口说明

- 基准地址：api.consumer.blb.com
- 服务端已开启CORS跨域支持
- 认证统一采用token
- 授权的API，需要在请求头TOKEN字段提供token令牌
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