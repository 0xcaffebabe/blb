<template>
  <div>
    <el-card class="my">
      <div slot="header">我的</div>
      <el-row :gutter="20">
        <el-col :span="8" class="my-left">
          <el-row>
            <el-col :span="6" style="text-align:center">
              <el-avatar circle :size="64" :src="userInfo.avatar"></el-avatar>
              <input type="file" ref="fileUploader">
              <el-button size="small" type="primary" @click="handleUpload">点击上传头像</el-button>
            </el-col>
            <el-col :span="18">
              <h3 v-show="nickNameShow" @click="nickNameShow = !nickNameShow">{{userInfo.username}}</h3>
              <el-input clearable v-show="!nickNameShow" style="width:200px" @blur="nickNameShow = !nickNameShow" :value="userInfo.username"></el-input>
              <p style="margin-top:-10px"> <span class="el-icon el-icon-phone"></span> {{userInfo.phone}}</p>
            </el-col>
          </el-row>
          <el-card class="info-card">
            <div @click="showUserEditPanel('delivery')"><span class="el-icon el-icon-place"></span>  <span class="el-icon-arrow-right" style="float:right"></span> 收货地址</div>
            <el-divider></el-divider>
            <div @click="showUserEditPanel('password')"><span class="el-icon el-icon-lock"></span> <span class="el-icon-arrow-right" style="float:right"></span> 登录密码</div>
            <el-divider></el-divider>
            <div @click="showUserEditPanel('info')"><span class="el-icon el-icon-phone-outline"></span> <span class="el-icon-arrow-right" style="float:right"></span> 手机号码</div>
            <el-divider></el-divider>
            <div @click="showVipPanel"><span class="el-icon el-icon-sugar"></span> <span class="el-icon-arrow-right" style="float:right"></span> 饱了吧会员</div>
            <el-divider></el-divider>
            <div @click="$router.push('/service')"><span class="el-icon el-icon-service"></span>  <span class="el-icon-arrow-right" style="float:right"></span> 客服中心</div>
          </el-card>
        </el-col>
        <el-col :span="16" class="my-right">
          <el-card>
            <div slot="header">账户</div>
            <el-row :gutter="20">
              <el-col :span="8">
                <div class="account-item">
                  <span style="color:#ff9900" @click="$store.commit('toggleBalancePane')">123.18</span><span> 元</span>
                  <p>账户余额</p>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="account-item">
                  <span style="color:#ff5f3e" @click="$store.commit('toggleCouponPaneShow')">18</span><span> 张</span>
                  <p>优惠券</p>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="account-item" style="border-right:none">
                  <span style="color:#6ac20b" @click="$router.push('/pointsMall')">58</span><span> 分</span>
                  <p>积分</p>
                </div>
              </el-col>
            </el-row>
          </el-card>
          <task-list style="margin-top:20px"/>
        </el-col>
      </el-row>
    </el-card>
    <user-edit-pane :currentTab="currentTab"></user-edit-pane>
    <vip-pane></vip-pane>
    <balance-pane/>
    <coupon-pane/>
  </div>
</template>

<script>
import UserEditPane from '../components/my/UserEditPane'
import VipPane from '../components/my/VipPane'
import BalancePane from '../components/my/BalancePane'
import CouponPane from '../components/my/CouponPane'
import TaskList from '../components/my/TaskList'
import consumerService from '../service/ConsumerService'
export default {
  data () {
    return {
      nickNameShow: true,
      userInfo: this.$store.state.user.info,
      currentTab: 'devliery'
    }
  },
  methods: {
    async handleUpload () {
      const fileList = this.$refs.fileUploader.files
      try {
        const avatar = await consumerService.uploadAvatar(fileList)
        if (avatar) {
          this.$message.success('上传头像成功')
          this.userInfo.avatar = avatar
          if (await consumerService.updateUserInfo(this.userInfo)) {
            this.$message.success('更新资料成功')
          }
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    showUserEditPanel (tab) {
      this.currentTab = tab
      this.$store.commit('toggleUserEditPanel')
    },
    showVipPanel () {
      this.$store.commit('toggleVipPanel')
    }
  },
  components: {
    UserEditPane, TaskList, VipPane, BalancePane, CouponPane
  }
}
</script>

<style lang="less" scoped>
  .my-left {
    h3 {
      margin-top: 10px;
      margin-left: 10px;
    }
  }
  .info-card {
    margin-top: 10px;
    font-size: 14px;
    div {
      cursor: pointer;
    }
  }
  .account-item {
    text-align:center;
    border-right:1px solid #ccc;
    span:first-child {
      font-size:32px;
      font-weight:700;
      cursor: pointer;
    }
  }
</style>
