<template>
  <div>
    <shop-list style="margin-top:10px" :title="'分类结果'" :shopList="shopData.data"></shop-list>
    <el-card v-if="shopData.data" style="margin-top:10px;text-align:center">
      <el-pagination
      @size-change="handleSizeChange"
      background
      @current-change="handlePageChange"
      :current-page="page"
      :page-sizes="[4, 12, 20, 30]"
      :page-size="size"
      layout="prev, pager, next"
      :total="shopData.total">
    </el-pagination>
    </el-card>
  </div>
</template>

<script>
import ShopList from '../components/takeaway/ShopList'
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      shopData: {},
      categoryId: this.$route.params.categoryId,
      page: 1,
      size: 12
    }
  },
  created () {
    this.getShopData()
  },
  beforeRouteUpdate (to, from, next) {
    if (!to.fullPath.startsWith('/list')) {
      return next()
    }
    next()
    // 跳转到其他分类下额店铺页面
    this.page = 1
    this.size = 2
    this.categoryId = this.$route.params.categoryId
    this.getShopData()
  },
  components: {
    ShopList
  },
  methods: {
    async getShopData () {
      try {
        const shopData = await shopService.getShopListByCategory({
          categoryId: this.$route.params.categoryId,
          page: this.page,
          size: this.size
        })
        this.shopData = shopData
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async handlePageChange (val) {
      this.page = val
      await this.getShopData()
      this.scrollToTop()
    },
    async handleSizeChange (val) {
      this.size = val
      await this.getShopData()
      this.scrollToTop()
    },
    scrollToTop () {
      window.scrollTo({ top: 0 })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
