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
        'https://imgcache.dealmoon.com/fsvrugc.dealmoon.com/ugc/fbc/9c2/b75/1ba7fee879eb81c006bb991.jpg_1280_1280_3_af99.jpg',
        'https://tu1.whhost.net/uploads/20181130/23/1543592588-BeXSZyILvU.jpg',
        'https://imgcp.aacdn.jp/img-a/1720/auto/global-aaj-front/article/2018/02/5a96955e67d36_5a96954e14a47_33320052.jpg',
        'https://cf.dtcj.com/879b495e-feb2-4191-bedb-d3b7fba1702c.jpg?imageslim'
      ]
    }
  }
}
</script>

<style lang="less" scoped>
</style>
