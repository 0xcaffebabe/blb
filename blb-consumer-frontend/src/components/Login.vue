<template>
  <el-dialog
    title="登录"
    :visible="$store.state.loginPanelShow"
    @close="$store.commit('toggleLoginPanel')"
    width="30%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="loginFormRules" ref="ruleForm" :label-position="'right'" label-width="50px">
            <el-form-item label="登录" prop="name">
              <el-input v-model="loginForm.name" placeholder="用户名/手机/邮箱"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="name">
              <el-input v-model="loginForm.name" placeholder="密码" show-password clearable></el-input>
            </el-form-item>
          </el-form>
           <el-tab-pane label="注册" name="register">注册</el-tab-pane>
        </el-tab-pane>
        <div style="">
          <el-button type="success" style="width:100px;float:right">登录</el-button>
        </div>
      </el-tabs>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'login',
      loginForm: {},
      loginFormRules: []
    }
  }
}
</script>

<style lang="less" scoped>

</style>
