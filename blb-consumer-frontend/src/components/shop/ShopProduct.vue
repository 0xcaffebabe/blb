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
          <transition-group name="list">
            <li v-for="item in productList" :key="item.productId">
              <product-item :product="item" :shopId="shopId"/>
            </li>
          </transition-group>
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
        console.error(e)
        this.$message.error(e.message)
      }
    },
    refreshProductList () {
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
  .list-item {
    display: inline-block;
    margin-right: 10px;
  }
  .list-enter-active, .list-leave-active {
    transition: all 1s;
  }
  .list-enter, .list-leave-to
  /* .list-leave-active for below version 2.1.8 */ {
    opacity: 0;
    transform: translateY(30px);
  }
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
