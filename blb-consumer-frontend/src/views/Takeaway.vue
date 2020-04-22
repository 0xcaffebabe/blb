<template>
    <div>
      <!-- 首页轮播图 -->
      <home-carsouel></home-carsouel>
      <!-- 附近商家 -->
      <shop-list :title="'附近商家'" :shopList="[1,2,3,4,5,6,7,9]"></shop-list>
    </div>
</template>

<script>
import HomeCarsouel from '../components/takeaway/HomeCarsouel'
import ShopList from '../components/takeaway/ShopList'
export default {
  data () {
    return {}
  },
  components: {
    HomeCarsouel, ShopList
  }
}
</script>
