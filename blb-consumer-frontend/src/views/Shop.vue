<template>
  <div>
    <shop-header :info="shopInfo"/>
    <!-- 菜单选项栏 -->
    <el-card>
        <el-tabs v-model="activeName">
          <el-tab-pane label="商品" name="product">
            <shop-product :shopId="shopId"/>
          </el-tab-pane>
          <el-tab-pane label="评价" name="eval">
            <shop-eval/>
          </el-tab-pane>
          <el-tab-pane label="商家" name="third">商家</el-tab-pane>
        </el-tabs>
    </el-card>
    <!-- 购物车 -->
    <shop-cart :cartShow="cartShow"></shop-cart>
    <!-- 侧边电梯 -->
    <div class="cart-bar">
      <el-badge :value="12">
        <el-button icon="el-icon-shopping-cart-2" circle class="cart-button" @click="$store.commit('toggleCart')"></el-button>
      </el-badge>
      <el-button type="success" class="pay-button" @click="$router.push('confirmOrder')">结算</el-button>
    </div>
  </div>
</template>

<script>
import ShopHeader from '../components/shop/ShopHeader'
import ShopProduct from '../components/shop/ShopProduct'
import ShopCart from '../components/shop/ShopCart'
import ShopEval from '../components/shop/ShopEval'

import shopService from '../service/ShopService'
export default {
  data () {
    return {
      activeName: 'product',
      cartShow: true,
      shopId: this.$route.params.shopId,
      shopInfo: {}
    }
  },
  components: {
    ShopHeader, ShopProduct, ShopCart, ShopEval
  },
  methods: {
    async getShopInfo () {
      try {
        const shopInfo = await shopService.getShopInfo(this.shopId)
        this.shopInfo = shopInfo
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getShopInfo()
  }
}
</script>

<style lang="less" scoped>
  .cart-bar {
    z-index: 1;
    float: left;
    position: fixed;
    top: 50%;
    transform: translateY(-50%);
    right: 0;
    height: 150px;
    width: 40px;
    background-color: #3d3d3f;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    .cart-button {
      font-size: 24px;
      margin-top: 20px;
      position: absolute;
      left: -20px;
      border: 4px solid #3d3d3f;
      background-color: #3190e8;
      color: white;
    }
    .pay-button {
      padding: 0;
      height: 50px;
      position: absolute;
      font-weight: 600;
      width: 100%;
      bottom: 0;
      margin-left: 0;
      border-radius: 0;
      border-bottom-left-radius: 5px;
    }
  }
</style>
