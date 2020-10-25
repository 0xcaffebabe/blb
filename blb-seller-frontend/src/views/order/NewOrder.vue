<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="16">
        <order-item
        :order="item"
        v-for="item in orderList"
        :key="item.orderId"
        @refreshOrder="getNewOrder"
        />
      </el-col>
      <el-col :span="8">
        <el-card class="overview">
          <div slot="header">今日营业总览</div>
          <p>订单量：20单</p>
          <p>营业额：1823 元</p>
        </el-card>
        <el-card class="overview">
          <div slot="header">今日出餐汇总</div>
          <ul class="dilivery-overview">
            <li>黄焖鸡<span class="fr">X1</span></li>
            <li>黄焖猪蹄<span class="fr">X2</span></li>
            <li>黄焖腐竹<span class="fr">X1</span></li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import OrderItem from '../../components/order/OrderItem'
export default {
  props: ['orderList'],
  data () {
    return {
      expand: '1'
    }
  },
  methods: {
    getNewOrder () {
      this.$emit('refreshOrder')
    }
  },
  components: {
    OrderItem
  }
}
</script>

<style lang="less" scoped>
  .order-item {
    margin-top:20px;
  }
  .header {
    h1 {
      display: inline;
    }
    span {
      float: right;
      font-size: 14px;
      height: 30px;
      line-height: 30px;
    }
  }
   .consumer-info {
    h3 {
      margin: 0;
      display: inline;
    }
    span {
      font-size: 14px;
      color: #666;
    }
    p {
      margin-top: 10px;
      color: #666;
      font-size: 14px;
    }
  }
  .order-note {
    span {
      color: #E6A23C;
    }
  }
  .delivery-info {
    float:right;
    color:#409EFF
  }
  .product-name {
    font-size: 16px;
    margin: 0;
    padding: 0;
  }
  .product-spec {
    font-size: 12px;
    font-weight: 600;
  }
  .product-num {
    font-weight: 700;
  }
  .overview {
    margin-top: 20px;
    p {
      margin: 0;
      padding: 5px;
      font-weight: 600;
    }
  }
  .dilivery-overview {
    font-size: 14px;
    li {
      padding: 5px;
    }
  }
</style>
