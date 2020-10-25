<template>
    <div class="container">
        <el-row>
        <el-col :span="4">
          <span class="logo" @click="test">饱了吧
          </span>
        </el-col>
        <el-col :span="16">
          <el-menu :default-active="'takeaway'"
                    mode="horizontal"
                    background-color="#3190e8"
                    text-color="#fff"
                    active-text-color="#ffd04b"
                    :router="true"
                    >
            <el-menu-item index="/takeaway" class="el-icon-eleme"> 外卖</el-menu-item>
            <el-menu-item index="/search" class="el-icon-search"> 搜索</el-menu-item>
            <el-menu-item index="/order" class="el-icon-s-order" :disabled="!$store.state.user.login"> 订单</el-menu-item>
            <el-menu-item index="/my" class="el-icon-user" :disabled="!$store.state.user.login"> 我的</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="4">
            <span class="login-tip" @click="$store.commit('toggleLoginPanel')" v-if="!$store.state.user.login">登录/注册</span>
            <el-popover
              v-else
              placement="bottom"
              title="设置"
              width="200"
              trigger="click">
              <div style="text-align:center">
                <el-avatar :src="$store.state.user.info.avatar"></el-avatar>
                <p>蔡徐坤</p>
                <p>您有 <a href="#">1</a> 条新消息</p>
                <div>
                  <el-button type="primary" size="mini">切换账号</el-button>
                  <el-button type="warning" size="mini">注销</el-button>
                </div>
              </div>
              <el-avatar :src="$store.state.user.info.avatar" class="avatar" slot="reference"></el-avatar>
            </el-popover>
            <span class="el-icon-location location" @click="$store.commit('toggleLocationChooser')"> {{$store.state.location}}
              <span class="el-icon-caret-bottom" v-if="!$store.state.locationChooserShow"></span>
              <span class="el-icon-caret-top" v-else></span>
            </span>
        </el-col>
      </el-row>
      <location-chooser></location-chooser>
    </div>
</template>

<script>
import LocationChooser from './LocationChooser'
import categoryService from '../../service/CategoryService'
import consumerService from '../../service/ConsumerService'
import locationService from '../../service/LocationService'
export default {
  data () {
    return { }
  },
  async created () {
    const isLogin = await consumerService.isLogin()
    this.$store.commit('setLoginState', isLogin)
    if (isLogin) {
      const userInfo = await consumerService.getConsumerInfo()
      this.$store.commit('setUserInfo', userInfo)
    }
    const location = await locationService.getLocation()
    console.log(location)
  },
  components: {
    LocationChooser
  },
  methods: {
    async test () {
      const data = await categoryService.getShopCategory()
      console.log(data)
    }
  }
}
</script>

<style lang="less" scoped>
  .login-tip {
    font-size:14px;
    color:white;
    margin-right:20px;
    cursor: pointer;
  }
  .avatar {
    vertical-align: middle;
    margin-right: 10px;
    cursor: pointer;
  }
  .logo {
    height: 56px;
    line-height: 56px;
    color: #fff;
    font-size: 20px;
    text-shadow: 2px 2px 3px rgba(0,0,0,.5);
  }
  .container {
    padding: 0 10px;
    background-color: #3190e8;
  }
  .location {
    cursor: pointer;
    height: 56px;
    line-height: 56px;
    font-size: 16px;
    color:white;
  }
</style>
