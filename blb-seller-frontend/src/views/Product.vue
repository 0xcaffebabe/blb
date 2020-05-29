<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <div>
          <el-input placeholder="搜索商品"></el-input>
          <el-menu
            :default-active="defaultActive"
            @select="getProduct"
            >
            <el-menu-item :index="item.categoryId + ''" v-for="item in categoryList" :key="item.categoryId">
              {{item.categoryName}}
            </el-menu-item>
          </el-menu>
        </div>
      </el-col>
      <el-col :span="20">
        <div>
          <el-button size="mini" type="primary" @click="showProductEdit=true">新增商品</el-button>
        </div>
        <ul class="product-list">
          <li class="product-list-item" v-for="item in productList" :key="item.productId">
            <el-card>
              <el-row>
                <el-col :span="16">
                  <h3 class="product-name">{{item.productName}}</h3>
                  <p class="danger product-price">
                    ￥{{getPriceRange(item)}}
                  </p>
                  <el-dropdown class="product-spec" :hide-on-click="false">
                    <el-button type="primary" size="mini">
                      规格<i class="el-icon-arrow-down el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item v-for="item1 in item.productSpecList" :key="item1.specId">
                        {{item1.specName}}: {{item1.stock}}
                        <el-button-group>
                          <el-button type="success" size="mini" @click="setStcokFull(item.productCategory.categoryId, item.productId, item1.specId)">置满</el-button>
                          <el-button type="info" size="mini" @click="setStcokEmpty(item.productCategory.categoryId, item.productId, item1.specId)">沽清</el-button>
                        </el-button-group>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  <el-button type="success" size="mini" style="margin-left:10px;margin-top:40px" @click="showProductEdit = true">编辑</el-button>
                </el-col>
                <el-col :span="8">
                  <el-image  fit="cover" style="height:100px;width:100px" :src="item.productImg"></el-image>
                </el-col>
              </el-row>
            </el-card>
          </li>
        </ul>
      </el-col>
    </el-row>
    <product-edit-panel v-model="showProductEdit"/>
  </div>
</template>

<script>
import ProductEditPanel from '../components/product/ProductEditPanel'
import productService from '../service/ProductService'
export default {
  data () {
    return {
      categoryList: [],
      productList: [],
      showProductEdit: false
    }
  },
  components: {
    ProductEditPanel
  },
  computed: {
    defaultActive () {
      if (this.categoryList && this.categoryList.length !== 0) {
        this.getProduct(this.categoryList[0].categoryId)
        return this.categoryList[0].categoryId + ''
      }
      return ''
    }
  },
  methods: {
    handleCategoryEdit () {
      this.showCategoryEdit = !this.showCategoryEdit
    },
    async getCategoryList () {
      try {
        this.categoryList = await productService.getProductCategory()
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async getProduct (categoryId) {
      try {
        this.productList = await productService.getProduct(categoryId)
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async setStcokFull (categoryId, productId, specId) {
      try {
        if (await productService.setProductStockFull({ categoryId, productId, specId })) {
          this.$message.success('置满库存成功')
          this.getProduct(categoryId)
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async setStcokEmpty (categoryId, productId, specId) {
      try {
        if (await productService.setProductStockEmpty({ categoryId, productId, specId })) {
          this.$message.success('沽清库存成功')
          this.getProduct(categoryId)
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    getPriceRange (product) {
      const price = productService.getProductPriceRange(product)
      if (price.lowPrice === price.highPrice) {
        return price.lowPrice
      }
      return price.lowPrice + '-' + price.highPrice
    }
  },
  created () {
    this.getCategoryList()
  },
  directives: {
    focus: {
      inserted (el, { value }) {
        if (value) {
          el.querySelector('input').focus()
        }
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .product-name {
    margin: 0;
    font-size: 16px;
  }
  .product-price {
    margin-top: 10px;
    font-size: 14px;
  }
  .product-list {
    margin-top: 10px;
    display: flex;
    flex-flow: row wrap;
    justify-content: center;
    .product-list-item {
      flex: 0 0 32%;
      margin-bottom: 10px;
      margin-right: 10px;
    }
  }
  .product-spec {
    margin-top: 40px;
  }
</style>
