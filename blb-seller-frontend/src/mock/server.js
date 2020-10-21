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
