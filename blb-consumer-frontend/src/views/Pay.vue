<template>
  <div>
    <el-card>
      <div slot="header">
        订单:{{shopName}} - {{orderId}}
      </div>
      <div style="text-align:center">
        <el-image :src="qrcode" style="width:256px;height:256px"></el-image>
        <p>支付状态:{{payStatus}}</p>
        <el-button type="primary" @click="$router.push('order')">已完成支付?</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import payService from '../service/PayService'
export default {
  data () {
    return {
      orderId: this.$route.params.orderId,
      payId: '',
      qrcode: '',
      shopName: '',
      timer: null,
      payStatus: '',
      shopInfo: this.$route.params.shopInfo
    }
  },
  methods: {
    async getPayId () {
      try {
        this.payId = await payService.generatePay(this.orderId)
        if (this.payId) {
          const data = await payService.getPayInfo(this.payId)
          this.qrcode = data.url
          this.shopName = data.shopName
          this.orderId = data.orderId
          if (this.qrcode) {
            this.timer = setInterval(async () => {
              const status = await payService.getPayStatus(this.payId)
              this.payStatus = status.msg
              if (status.status === 2) {
                // 支付成功
                clearInterval(this.timer)
                this.$message.success(status.msg)
                this.$router.push('/order')
              }
            }, 3000)
          }
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getPayId()
  },
  // 即将离开该页面
  beforeRouteLeave (to, from, next) {
    // 离开该页面之前，清空定时查询支付状态
    clearInterval(this.timer)
    next()
  }
}
</script>

<style lang="less" scoed>

</style>
