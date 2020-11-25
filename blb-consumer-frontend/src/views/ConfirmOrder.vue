<template>
    <div>
      <div style="margin-bottom:20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/shop/1' }">{{shopInfo.shopName}}</el-breadcrumb-item>
          <el-breadcrumb-item>确认下单</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <el-row :gutter="10">
          <el-col :span="16">
            <order-product :shopInfo="shopInfo" :productList="productList"/>
          </el-col>
          <el-col :span="8">
            <el-card>
              <el-row>
                <el-col :span="4">
                  <span class="el-icon-location location-icon"></span>
                </el-col>
                <el-col :span="18">
                  <strong>{{delivery.realName}}</strong> <span style="font-size:14px">先生/女士</span> <span class="phone">{{delivery.phone}}</span>
                  <p class="address">{{delivery.building}} {{delivery.detail}}</p>
                </el-col>
                <el-col :span="2">
                  <span class="el-icon-arrow-right"></span>
                </el-col>
              </el-row>
            </el-card>
            <el-card class="delivery-time">
              <el-col :span="8"><h1>送达时间</h1></el-col>
              <el-col :span="16">
                <p>预计 {{getDeliveryTime()}}</p>
                <el-tag effect="dark" type="primary" size="mini">蜂鸟专送</el-tag>
              </el-col>
            </el-card>
            <el-card class="coupon-list">
              <div slot="header">红包卡券</div>
              <ul>
                <li v-for="item in couponList" :key="item">
                  <el-row>
                    <el-col :span="4">
                      <span class="el-icon-s-cooperation"></span>
                    </el-col>
                    <el-col :span="18">
                      <p>{{item}}</p>
                    </el-col>
                    <el-col :span="2">
                      <span class="el-icon-arrow-right"></span>
                    </el-col>
                  </el-row>
                </li>
              </ul>
            </el-card>
            <el-card style="margin-top:10px">
              <ul>
                <li>
                    <span>支付方式</span>
                    <el-select v-model="payValue" placeholder="请选择" style="width:100px;margin-left:100px">
                      <el-option
                        v-for="item in payOptions"
                        :key="item"
                        :label="item"
                        :value="item">
                      </el-option>
                    </el-select>
                </li>
                <el-divider></el-divider>
                <li>
                  <p>订单备注</p>
                  <el-input type="textarea" v-model="orderForm.orderNote"></el-input>
                </li>
              </ul>
            </el-card>
            <el-card style="margin-top:10px">
              <span>待支付:￥ {{totalAmount}}</span>
              <el-button type="success" style="margin-left:50px" @click="handleMakeOrder">确认下单</el-button>
            </el-card>
          </el-col>
        </el-row>
    </div>
</template>

<script>
import OrderProduct from '../components/order/OrderProduct'
import consumerService from '../service/ConsumerService'
import orderService from '../service/OrderService'
import cartService from '../service/CartService'
import productService from '../service/ProductService'
export default {
  data () {
    return {
      payValue: '支付宝',
      payOptions: [
        '支付宝', '银联'
      ],
      shopInfo: {},
      productList: [],
      hasDefaultDelivery: false,
      delivery: {},
      orderForm: {
        deliveryId: '',
        orderNote: '',
        productList: []
      },
      couponList: [
        '店铺红包 | 满 20-20',
        '超值联盟红包 | 满 20-3',
        '新人首单红包 | 满 20-15'
      ]
    }
  },
  components: {
    OrderProduct
  },
  created () {
    this.shopInfo = this.$store.state.confirmOrderData.shopInfo
    this.productList = this.$store.state.confirmOrderData.productList
    this.orderForm.productList = this.productList
    this.getDefaultDelivery()
  },
  computed: {
    totalAmount () {
      return productService.calcTotalAmount(this.productList)
    }
  },
  methods: {
    async getDefaultDelivery () {
      try {
        const delivery = await consumerService.getDefaultDelivery()
        if (delivery) {
          this.hasDefaultDelivery = true
          this.delivery = delivery
          this.orderForm.deliveryId = this.delivery.deliveryId
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    getDeliveryTime () {
      return orderService.getOrderDeliveryTime()
    },
    handleMakeOrder () {
      this.makeOrder()
    },
    async makeOrder () {
      try {
        const orderId = await orderService.makeOrder(this.orderForm)
        if (orderId) {
          this.$message.success('下单成功,请进行支付')
          cartService.clearCart(this.shopInfo.shopId)
          this.$router.push({
            name: 'pay',
            params: {
              orderId,
              shopInfo: this.shopInfo
            }
          })
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .el-icon-location {
    font-size:32px;
    line-height:48px;
    height:48px;
    color:rgb(49, 144, 232);
  }
  .el-icon-arrow-right {
    font-size: 24px;
    line-height:56px;
    height:56px;
  }
  .phone {
    font-size: 14px;
  }
  .address {
    margin-top: 5px;
    font-size: 14px;
  }
  .delivery-time {
    margin-top: 10px;
    padding-bottom: 20px;
    h1 {
      margin: 0;
      font-size: 20px;
      height: 44px;
      line-height: 44px;
    }
    p {
      margin: 0;
      color:rgb(49, 144, 232);
    }
    .el-tag {
      float: right;
      margin-top: 10px;
    }
  }
  .coupon-list {
    margin-top: 10px;
    .el-icon-s-cooperation {
      font-size: 32px;
      line-height:48px;
      height:48px;
      color: red;
    }
  }
</style>
