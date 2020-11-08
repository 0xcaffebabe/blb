<template>
  <div>
    <el-card>
      <div slot="header">我的订单</div>
      <div class="order-list">
        <el-card v-for="item in orderList" :key="item.orderId" class="order-list-item">
            <el-row>
              <el-col :span="2">
                <div class="shop-img" @click="$store.commit('toggleOrderDetail')">
                  <el-avatar :src="item.shopLogo" fit="cover" shape="square"  :size="64"></el-avatar>
                </div>
              </el-col>
              <el-col :span="22">
                <span class="order-status">{{getOrderStatus(item)}}</span>
                <h4 class="shop-name" @click="$router.push('/shop/' + item.shopId)">{{item.shopName}} <span class="el-icon-arrow-right"></span></h4>
                <p class="order-date">{{item.createTime}}</p>
                <el-divider></el-divider>
                <span style="float:right;color:#f60">￥{{item.orderAmount}}</span>
                <p class="order-detail">{{item.orderName}}</p>
                <div v-if="getOrderStatus(item) === '配送中'" class="amap-box-wrapper">
                  <el-amap class="amap-box" :zoom="10" :center="[117.5,24]" >
                    <el-amap-marker vid="component-shop-marker" :position="[117.5,24]"></el-amap-marker>
                    <el-amap-text vid="component-shop-text" :position="[117.5,24]" :text="'店铺位置'"></el-amap-text>
                    <el-amap-marker vid="component-rider-marker" :position="[117.5,24.1]"></el-amap-marker>
                    <el-amap-text vid="component-rider-text" :position="[117.5,24.1]" :text="'骑手位置'"></el-amap-text>
                  </el-amap>
                </div>
                <el-button size="mini" type="primary" style="float:right;margin-top:5px;margin-left:15px" @click="$router.push('/shop/' + item.shopId)">再来一单</el-button>
                <el-button size="mini" type="success" style="float:right;margin-top:5px" @click="showOrderDetail(item.orderId)">订单详情</el-button>
              </el-col>
            </el-row>
          </el-card>
          </div>
    </el-card>
  </div>
</template>

<script>
import orderService from '../../service/OrderService'
export default {
  props: ['orderList'],
  data () {
    return {}
  },
  methods: {
    getOrderStatus (order) {
      return orderService.getOrderStatus(order)
    },
    showOrderDetail (orderId) {
      this.$emit('showOrderDetail', orderId)
    }
  }
}
</script>

<style lang="less" scoped>
  .order-list-item {
    margin-bottom: 20px;
    .shop-name {
      margin: 0;
      padding: 0;
      vertical-align: middle;
      font-size: 14px;
      cursor: pointer;
    }
    .el-avatar {
      cursor: pointer;
    }
    .order-date {
      font-size: 12px;
      color: #666;
    }
    .order-status {
      font-size: 14px;
      float: right;
    }
    .order-detail {
      font-size: 14px;
    }
  }
  .amap-box {
    width: 100%;
    height: 400px;
    border: 2px solid #409EFF;
    box-sizing: border-box;
  }
</style>
