<template>
  <el-card class="order-item">
    <el-row>
      <el-col :span="2">
        <div class="shop-img" @click="$router.push('/shop/1')">
          <el-avatar :src="order.shopLogo" fit="cover" shape="square"  :size="64"></el-avatar>
        </div>
      </el-col>
      <el-col :span="22">
        <span class="order-status">{{getOrderStatus(order)}}</span>
        <h4 class="shop-name" @click="$router.push('/shop/' + order.shopId)">{{order.shopName}} <span class="el-icon-arrow-right"></span></h4>
        <p class="order-date">{{order.createTime}}</p>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="18" class="order-detail">
            <ul class="order-img-list">
              <li v-for="img in order.imgList" :key="img + Math.random()" class="order-img-item">
                <el-image fit="cover" :src="img" :preview-src-list="[img]"/>
              </li>
            </ul>
          </el-col>
          <el-col :span="6">
            <p class="order-price">￥{{order.orderAmount}}</p>
            <p class="order-name">{{order.orderName}}</p>
          </el-col>
        </el-row>
        <div v-if="getOrderStatus(order) === '配送中' && editable" class="amap-box-wrapper">
          <el-amap class="amap-box" :zoom="10" :center="[117.5,24]" >
            <el-amap-marker vid="component-shop-marker" :position="[117.5,24]"></el-amap-marker>
            <el-amap-text vid="component-shop-text" :position="[117.5,24]" :text="'店铺位置'"></el-amap-text>
            <el-amap-marker vid="component-rider-marker" :position="[117.5,24.1]"></el-amap-marker>
            <el-amap-text vid="component-rider-text" :position="[117.5,24.1]" :text="'骑手位置'"></el-amap-text>
          </el-amap>
        </div>
        <el-button v-if="getOrderStatus(order) === '已完结' && editable" @click="showOrderEval" size="mini" type="warning" style="float:right;margin-top:5px;margin-left:15px">评价订单</el-button>
        <el-button v-if="editable" size="mini" type="primary" style="float:right;margin-top:5px;margin-left:15px" @click="$router.push('/shop/' + order.shopId)">再来一单</el-button>
        <el-button v-if="editable" size="mini" type="success" style="float:right;margin-top:5px" @click="showOrderDetail(order.orderId)">订单详情</el-button>
      </el-col>
    </el-row>
    <order-eval v-if="editable"/>
  </el-card>
</template>

<script>
import orderService from '../../service/OrderService'
import OrderEval from './OrderEval'
export default {
  props: {
    order: {
      type: Object,
      required: true
    },
    editable: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  data () {
    return {}
  },
  methods: {
    getOrderStatus (order) {
      return orderService.getOrderStatus(order)
    },
    showOrderDetail (orderId) {
      this.$emit('show-order-detail', orderId)
    },
    showOrderEval (order) {
      this.$store.commit('toogleEvalPaneShow')
    }
  },
  components: {
    OrderEval
  }
}
</script>

<style lang="less" scoped>
  .order-item {
    margin-bottom: 20px;
    .shop-name {
      display: inline-block;
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
    .order-price {
      color:#f60;
      font-size: 20px;
      padding: 0;
      margin: 0;
      text-align: right;
    }
    .order-name {
      text-align: right;
      margin-top: 32px;
      margin-bottom: 0;
      color: #666;
    }
    .order-status {
      font-size: 14px;
      float: right;
    }
  }
  .order-img-list {
    margin: 0;
  }
  .order-img-item {
    display: inline-block;
    padding: 0 5px;
    .el-image {
      height: 100px;
      width: 144px;
      border-radius: 5px;
    }
  }
  .amap-box {
    width: 100%;
    height: 400px;
    border: 2px solid #409EFF;
    box-sizing: border-box;
  }
</style>
