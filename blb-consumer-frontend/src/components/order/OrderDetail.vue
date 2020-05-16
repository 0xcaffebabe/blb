<template>
  <el-dialog
    title="订单详情"
    :visible="$store.state.orderDetailShow"
    @close="$store.commit('toggleOrderDetail')"
    width="80%">
      <el-row :gutter="20">
        <el-col :span="20">
          <order-product :productList="order.productList" :shopInfo="{shopName: order.shopName}"/>
        </el-col>
        <el-col :span="4">
          <el-card class="order-detail-header">
            <el-avatar :size="80" :src="order.shopLogo"></el-avatar>
            <h2>{{getPayStatus(order.payStatus)}}</h2>
            <el-button type="primary" size="mini" @click="$router.push('shop')">再来一单</el-button>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top:10px">
        <el-col :span="12">
          <el-card>
            <div slot="header">配送信息</div>
            <ul class="delivery-info">
              <li>送达时间：尽快送达</li>
              <li>送货地址：{{order.consumerAddress}}</li>
              <li>收货人：{{order.consumerName}}</li>
              <li>手机：{{order.consumerPhone}}</li>
              <li>备注：{{order.orderNote}}</li>
            </ul>
            <el-divider></el-divider>
            <el-timeline>
              <el-timeline-item type="success" timestamp='2020-04-03 20:46' :key="1">顾客下单</el-timeline-item>
              <el-timeline-item type="success" timestamp='2020-04-03 21:46' :key="2">商家制作</el-timeline-item>
              <el-timeline-item type="primary" timestamp='2020-04-03 22:46' :key="3">骑手配送</el-timeline-item>
              <el-timeline-item type="info" timestamp='2020-04-03 23:46' :key="4">订单完结</el-timeline-item>
            </el-timeline>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="order-info">
            <div slot="header">订单信息</div>
            <ul>
              <li>订单号: {{order.orderId}}</li>
              <li>创建时间：{{order.createTime}}</li>
            </ul>
          </el-card>
          <el-card style="margin-top:10px">
            <div slot="header">地图</div>
            <el-amap class="amap-box" :vid="'amap-vue'" :zoom="10" :center="[117.5,24]">
              <el-amap-marker vid="component-marker" :position="[117.5,24]"></el-amap-marker>
            </el-amap>
          </el-card>
        </el-col>
      </el-row>
  </el-dialog>
</template>

<script>
import OrderProduct from './OrderProduct'
import payService from '../../service/PayService'
export default {
  props: ['show', 'order'],
  data () {
    return { }
  },
  components: {
    OrderProduct
  },
  methods: {
    getPayStatus (status) {
      return payService.getPayStatusStr(status)
    }
  },
  created () {
    console.log(this.order)
  }
}
</script>

<style lang="less" scoped>
  .amap-box {
    width: 100%;
    height: 200px;
  }
  ul {
    margin: 0;
    padding: 0;
  }
  .order-detail-header {
    text-align: center;
  }
  .delivery-info, .order-info {
      font-size: 14px;
      color: #666;
      li {
        margin-bottom: 10px;
      }
  }
</style>
