<template>
    <div>
      <!-- 首页轮播图 -->
      <home-carsouel></home-carsouel>
      <!-- 附近商家 -->
      <nearby-shop></nearby-shop>
    </div>
</template>

<script>
import HomeCarsouel from '../components/takeaway/HomeCarsouel'
import NearbyShop from '../components/takeaway/NearbyShop'
export default {
  data () {
    return {}
  },
  components: {
    'home-carsouel': HomeCarsouel,
    'nearby-shop': NearbyShop
  }
}
</script>
