<template>
  <div class="login-container">
    <div class="logo-wrapper">
      <img class="logo" src="https://napos-auth.faas.ele.me/img/napos_logo.f98de34c.svg" alt="">
    </div>
    <h2>饱了吧商家版</h2>
    <div class="input">
      <el-input v-model="username" placeholder="用户名"></el-input>
      <el-input v-model="password" placeholder="密码" show-password></el-input>
      <el-button type="primary" style="width:100%" @click="login">登录/注册</el-button>
    </div>
    <p class="tip">未注册的账号将自动为您注册</p>
    <div style="text-align:left;padding-left:40px">
    <el-checkbox v-model="agree">我已阅读并同意<a href="#">饱了吧商家隐私政策</a></el-checkbox>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      username: '',
      password: '',
      agree: true
    }
  },
  methods: {
    login () {
      if (this.username && this.password) {
        this.$message.success('登录成功')
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .login-container {
    position: relative;
    margin-left: 50%;
    margin-top: 80px;
    transform: translateX(-50%);
    height: 400px;
    width: 400px;
    border: 1px solid #ccc;
    border-radius: 20px;
    text-align: center;
  }
  .logo-wrapper {
    margin-top: 20px;
  }
  .logo {
    height: 56px;
    max-width: 100%;
    box-shadow: 0 4px 12px 0 rgba(48,49,51,.12);
    border-radius: 10px;
  }
  .input {
    padding: 0 40px;
    .el-input {
      margin-bottom: 20px;
    }
  }
  .tip {
    padding-left: 40px;
    text-align: left;
    color: #666;
    font-size: 14px;
  }
  a {
    text-decoration: none;
    color: #409EFF;
  }
</style>
