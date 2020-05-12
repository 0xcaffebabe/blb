<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-image :src="product.productImg" :preview-src-list="['./food.png']" fit="cover"></el-image>
      </el-col>
      <el-col :span="12">
        <h2>{{product.productName}}</h2>
        <p class="product-detail">{{product.productDesc}}</p>
        <p class="product-sales">月售:{{product.sales}}份 好评率:{{product.positiveRate}}%</p>
        <el-radio-group
        size="mini"
        v-model="choosedSpec"
        >
          <el-radio-button
          :label="item1.specName"
          v-for="item1 in product.productSpecList"
          :key="item1.specId"
          @click.native="handleSpecClick(item1)"
          />
        </el-radio-group>
      </el-col>
      <el-col :span="6">
        <h1 class="product-price" v-if="!showPrice()">￥{{getPriceRange(product)}}</h1>
        <h1 class="product-price" v-if="showPrice()">￥{{currentSpec.price}}</h1>
        <el-button type="primary" icon="el-icon-plus" :disabled="!showPrice()" circle size="mini" @click="intoCart"></el-button>
      </el-col>
    </el-row>
    <el-divider></el-divider>
  </div>
</template>

<script>
import productService from '../../service/ProductService'
import cartService from '../../service/CartService'
export default {
  props: ['product', 'shopId'],
  data () {
    return {
      choosedSpec: '',
      currentSpec: {}
    }
  },
  methods: {
    showPrice () {
      return this.choosedSpec
    },
    getPriceRange (product) {
      const price = productService.getProductPriceRange(product)
      if (price.lowPrice === price.highPrice) {
        return price.lowPrice
      }
      return price.lowPrice + '-' + price.highPrice
    },
    handleSpecClick (item1) {
      this.currentSpec = item1
    },
    async intoCart () {
      try {
        await cartService.addProduct({
          shopId: this.shopId,
          productId: this.product.productId,
          specId: this.currentSpec.specId,
          quantity: 1
        })
        this.$message.success(this.choosedSpec + '加入购物车成功')
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  }
}
</script>

<style lang="less" scoped>
    .el-image {
      max-height: 150px;
      max-width: 150px;
    }
    li :hover {
      cursor: pointer;
    }
    h2 {
      font-size: 18px;
      margin: 0;
    }
    .product-detail {
      font-size: 14px;
      color: #666;
    }
    .product-sales {
      font-size: 14px;
    }
    .product-price {
      margin: 0;
      margin-bottom: 40px;
      font-size: 24px;
      color: #f60;
    }
    .el-button {
      margin-left: 30px;
    }
</style>
