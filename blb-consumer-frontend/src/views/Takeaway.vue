<template>
    <div>
      <!-- 首页轮播图 -->
      <home-carsouel></home-carsouel>
      <!-- 附近商家 -->
      <shop-list :title="'附近商家'" :shopList="shopList"></shop-list>
    </div>
</template>

<script>
import HomeCarsouel from '../components/takeaway/HomeCarsouel'
import ShopList from '../components/takeaway/ShopList'
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      shopList: []
    }
  },
  methods: {
    async getShop () {
      try {
        const data = await shopService.getNearyByShop()
        this.shopList = data.data
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  components: {
    HomeCarsouel, ShopList
  },
  created () {
    this.getShop()
  }
}
</script>
