<template>
  <!-- 首页轮播广告组件 -->
  <div>
      <el-carousel :interval="4000" height="500px">
          <el-carousel-item v-for="item in imgList" :key="item">
            <el-image :src="item" alt="" fit="cover"/>
          </el-carousel-item>
      </el-carousel>
  </div>
</template>

<script>
export default {
  data () {
    return {
      imgList: [
        '/imgs/banner/1.webp',
        '/imgs/banner/2.jpg',
        '/imgs/banner/3.jpg',
        '/imgs/banner/4.jpg'
      ]
    }
  }
}
</script>

<style lang="less" scoped>
</style>
