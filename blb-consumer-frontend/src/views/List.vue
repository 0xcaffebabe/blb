<template>
  <div>
    shop list {{categoryId}}
    <shop-list style="margin-top:10px" :title="'分类结果'" :shopList="shopData.data"></shop-list>
  </div>
</template>

<script>
import ShopList from '../components/takeaway/ShopList'
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      shopData: {},
      categoryId: this.$route.params.categoryId
    }
  },
  created () {
    this.getShopData()
  },
  beforeRouteUpdate (to, from, next) {
    next()
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
          categoryId: this.$route.params.categoryId
        })
        this.shopData = shopData
        console.log(this.shopData)
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  }
}
</script>

<style lang="less" scoped>

</style>
