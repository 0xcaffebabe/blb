<template>
  <div>
    <el-card>
      <div slot="header">
        订单:华莱士(旧镇店)-368 990 455 266
      </div>
      <div style="text-align:center">
        <el-image :src="qrcode"></el-image>
        <p>支付状态:等待支付</p>
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
      qrcode: ''
    }
  },
  methods: {
    async getPayId () {
      try {
        this.payId = await payService.generatePay(this.orderId)
        if (this.payId) {
          this.qrcode = await payService.getPayQRCode(this.payId)
          if (this.qrcode) {
            setInterval(async () => {
              console.log('支付状态', await payService.getPayStatus(this.payId))
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
  }
}
</script>

<style lang="less" scoed>

</style>
