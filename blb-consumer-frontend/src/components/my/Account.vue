<template>
  <el-card>
    <div slot="header">账户</div>
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="account-item">
          <span style="color:#ff9900" @click="$store.commit('toggleBalancePane')">{{info.balance}}</span><span> 元</span>
          <p>账户余额</p>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="account-item">
          <span style="color:#ff5f3e" @click="$store.commit('toggleCouponPaneShow')">{{info.coupon}}</span><span> 张</span>
          <p>优惠券</p>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="account-item" style="border-right:none">
          <span style="color:#6ac20b" @click="$router.push('/pointsMall')">{{info.points}}</span><span> 分</span>
          <p>积分</p>
        </div>
      </el-col>
    </el-row>
    <balance-pane/>
    <coupon-pane/>
  </el-card>
</template>

<script>
import BalancePane from './BalancePane'
import CouponPane from './CouponPane'
export default {
  data () {
    return {
      info: {
        balance: 123.18,
        coupon: 26,
        points: 58
      }
    }
  },
  components: {
    BalancePane, CouponPane
  }
}
</script>

<style lang="less" scoped>
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
