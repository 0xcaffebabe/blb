<template>
  <el-dialog
    :title="activeName == 'login' ? '登录' : '注册'"
    :visible="$store.state.loginPanelShow"
    @close="$store.commit('closeLoginPanel')"
    width="40%">
      <el-tabs v-model="activeName">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="loginFormRules" ref="loginForm" :label-position="'right'" label-width="80px">
            <el-form-item label="登录账号" prop="name">
              <el-input v-model="loginForm.name" placeholder="用户名/手机/邮箱"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="loginForm.password" placeholder="密码" show-password clearable></el-input>
            </el-form-item>
          </el-form>
           <div>
            <el-button type="success" style="width:100px;float:right" @click="login">登录</el-button>
           </div>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" :rules="registerFormRules" ref="registerForm" :label-position="'right'" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item label="手机" prop="phone">
              <el-input v-model="registerForm.phone" placeholder="手机"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" placeholder="密码" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="repeatPassword">
              <el-input v-model="registerForm.repeatPassword" placeholder="确认密码" show-password></el-input>
            </el-form-item>
          </el-form>
           <div>
            <el-button type="primary" style="width:100px;float:right" @click="register">注册</el-button>
           </div>
        </el-tab-pane>
      </el-tabs>
  </el-dialog>
</template>

<script>
import consumerService from '../service/ConsumerService'
export default {
  data () {
    const emailValidator = (rule, value, callback) => {
      if (/^\w+([.-]?w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(value)) {
        callback()
      } else {
        callback(new Error('邮箱格式不正确!'))
      }
    }
    const phoneValidator = (role, value, callback) => {
      if (/^1[3456789]\d{9}$/.test(value)) {
        callback()
      } else {
        callback(new Error('手机号码格式不正确!'))
      }
    }
    const passwordValidator = (role, value, callback) => {
      if (value === this.registerForm.password) {
        callback()
      } else {
        callback(new Error('两次输入的密码不一致!'))
      }
    }
    return {
      activeName: 'login',
      loginForm: {
        name: '',
        password: ''
      },
      loginFormRules: {
        name: [
          { required: true, message: '请输入登录账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      registerForm: {
        username: '',
        password: '',
        email: '',
        phone: '',
        repeatPassword: ''
      },
      registerFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在3-20', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: emailValidator, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在3-20', trigger: 'blur' }
        ],
        repeatPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: passwordValidator, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: phoneValidator, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login () {
      this.$refs.loginForm.validate(async valid => {
        if (valid) {
          try {
            const data = await consumerService.login(this.loginForm.name, this.loginForm.password)
            this.$message.success('登录成功')
            // 设置登录状态 用户信息
            this.$store.commit('setUserInfo', data)
            this.$store.commit('setLoginState', true)
            // 关闭弹窗
            this.$store.commit('closeLoginPanel')
          } catch (e) {
            this.$message.error(e.message)
          }
        } else {
          this.$message.error('请将信息填写完整')
        }
      })
    },
    register () {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.$message.success('注册成功')
        } else {
          this.$message.error('请将信息填写完整')
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
