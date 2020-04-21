<template>
    <div>
      <!-- 首页轮播图 -->
      <home-carsouel></home-carsouel>
      <!-- 附近商家 -->
      <shop-list :title="'附近商家'"></shop-list>
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
