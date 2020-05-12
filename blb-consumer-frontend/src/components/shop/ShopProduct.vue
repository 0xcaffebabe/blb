<template>
  <el-tabs tab-position="left"
  :value="categoryActiveName"
  @tab-click="handleCategoryTabClick"
  >
    <!-- 商品分类选项栏 -->
    <el-tab-pane :label="item.categoryName" :name="item.categoryId + ''" v-for="item in categoryList" :key="item.categoryId">
        <div class="clearfix product-cate-title">
          <h2>{{item.categoryName}}</h2><span> {{item.categoryDesc}}</span>
        </div>
        <el-alert title="没有数据" v-if="!productList"></el-alert>
        <ul class="product-list">
          <li v-for="item in productList" :key="item.productId">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-image :src="item.productImg" :preview-src-list="['./food.png']" fit="cover"></el-image>
              </el-col>
              <el-col :span="12">
                <h2>{{item.productName}}</h2>
                <p class="product-detail">{{item.productDesc}}</p>
                <p class="product-sales">月售:{{item.sales}}份 好评率:{{item.positiveRate}}%</p>
                <el-radio-group
                v-model="productSpecMap[item.productId]"
                size="mini"
                >
                  <el-radio-button
                  :label="item1.specName"
                  v-for="item1 in item.productSpecList"
                  :key="item1.specId"
                  @click.native="handleSpecClick(item, item1)"
                  />
                </el-radio-group>
              </el-col>
              <el-col :span="6">
                <h1 class="product-price" v-if="!showPrice(item.productId)">￥{{getPriceRange(item)}}</h1>
                <h1 class="product-price" v-if="showPrice(item.productId)">￥{{productChoosedSpecMap[item.productId].price}}</h1>
                <el-button type="primary" icon="el-icon-plus" circle size="mini"></el-button>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </li>
        </ul>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import shopService from '../../service/ShopService'
import productService from '../../service/ProductService'
export default {
  props: ['categoryList', 'shopId'],
  data () {
    return {
      productList: [],
      productSpecMap: {},
      productChoosedSpecMap: {}
    }
  },
  computed: {
    categoryActiveName () {
      if (this.categoryList[0]) {
        return this.categoryList[0].categoryId + ''
      }
      return null
    }
  },
  methods: {
    handleCategoryTabClick (item) {
      this.getProductList(item.name)
    },
    async getProductList (categoryId) {
      try {
        this.productList = await shopService.getProductList(this.shopId, categoryId)
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handleSpecClick (item, item1) {
      this.productChoosedSpecMap[item.productId] = item1
    },
    showPrice (productId) {
      return this.productChoosedSpecMap[productId]
    },
    getPriceRange (product) {
      const price = productService.getProductPriceRange(product)
      if (price.lowPrice === price.highPrice) {
        return price.lowPrice
      }
      return price.lowPrice + '-' + price.highPrice
    }
  },
  mounted () {
    if (this.categoryActiveName) {
      this.getProductList(this.categoryActiveName)
    }
  }
}
</script>

<style lang="less" scoped>
  .el-tabs__item .is-left {
    height: 48px!important;
  }
  .product-cate-title {
    h2 {
      display: inline;
      font-size: 16px;
      margin: 0;
      padding: 0;
    }
    span {
      font-size: 14px;
      color: #666;
    }
  }
  .product-list {
    padding: 0;
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
  }
</style>
