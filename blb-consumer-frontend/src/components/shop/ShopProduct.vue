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
        <el-alert title="没有数据" v-if="productList.length == 0" :closable="false"></el-alert>
        <ul class="product-list">
          <li v-for="item in productList" :key="item.productId">
            <product-item :product="item"/>
          </li>
        </ul>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import shopService from '../../service/ShopService'

import ProductItem from './ProductItem'
export default {
  props: ['shopId'],
  data () {
    return {
      productList: [],
      productSpecMap: {},
      productChoosedSpecMap: {},
      categoryList: []
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
  components: {
    ProductItem
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
    refreshProductList () {
      console.log(this.categoryList)
      if (this.categoryActiveName) {
        this.getProductList(this.categoryActiveName)
      }
    },
    async getProductCategory () {
      try {
        this.categoryList = await shopService.getShopProductCategory(this.shopId)
        // 获取商品目录完成，触发product更新
        this.refreshProductList()
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  mounted () {},
  created () {
    this.getProductCategory()
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
  }
</style>
