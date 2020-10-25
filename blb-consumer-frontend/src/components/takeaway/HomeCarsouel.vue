<template>
  <!-- 首页轮播广告组件 -->
  <div>
      <el-carousel :interval="4000" type="card" height="430px">
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
        'https://img.meituan.net/600.600/msmerchant/5b3b70487ed684fea84ed4a906272c2e831841.jpg',
        'https://img.meituan.net/msmerchant/d53e16698a2121f46e8fc4530acdabdc6026232.jpg',
        'https://p1.meituan.net/600.600/deal/58570ab465c2177bbf7e40cd9e21d9f0206194.jpg',
        'https://img.meituan.net/msmerchant/463762bddd19dbf45fc8ce189303e4031695926.png@600w_600h_1l'
      ]
    }
  }
}
</script>

<style lang="less" scoped>
</style>
