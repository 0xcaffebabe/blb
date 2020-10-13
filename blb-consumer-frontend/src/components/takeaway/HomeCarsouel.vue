<template>
  <!-- 首页轮播广告组件 -->
  <div>
      <el-carousel :interval="4000" type="card" height="300px">
          <el-carousel-item v-for="item in imgList" :key="item">
          <h3 class="medium">
            <img :src="item" alt="" >
          </h3>
          </el-carousel-item>
      </el-carousel>
  </div>
</template>

<script>
export default {
  data () {
    return {
      imgList: [
        'http://img1.qunarzz.com/sight/p0/1705/c2/c2249cf7d5ae5b71a3.img.jpg_600x330_2dd75e54.jpg',
        'http://img1.qunarzz.com/sight/p0/1610/23/235cd9aa750eb531a3.water.jpg_600x330_c3104644.jpg',
        'http://img1.qunarzz.com/sight/p0/1501/18/181cff1e8af11041.water.jpg_600x330_40b06d15.jpg'
      ]
    }
  }
}
</script>

<style lang="less" scoped>

</style>
