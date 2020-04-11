<template>
    <div>
        <el-carousel :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="item in 4" :key="item">
            <h3 class="medium">
              <img src="http://img1.qunarzz.com/sight/p0/1705/c2/c2249cf7d5ae5b71a3.img.jpg_600x330_2dd75e54.jpg" alt="">
              {{ item }}
            </h3>
            </el-carousel-item>
        </el-carousel>
        <el-card>
          <div slot="header">
            附近商家
          </div>
          <ul class="shop-list">
            <li class="shop-item-wrapper" v-for="item in 8" :key="item">
              <div class="shop-item">
                <img src="https://p1.meituan.net/waimaipoi/23d94486b3882b8eb6101aa251bbd45030720.jpg" alt="">
                <h1>小康烘焙</h1>
                <el-rate
                  v-model="value"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}">
                </el-rate>
                <span class="shop-sales">月售:106单</span>
                <div>
                  <span class="shop-price">起送:￥20/免配送费</span>
                </div>
                <p class="distance el-icon-time">1388.8公里 / <span>14小时48分钟</span></p>
              </div>
            </li>
          </ul>
        </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      value: 3.5
    }
  }
}
</script>

<style lang="less" scoped>
  .shop-list {
    display: flex;
    flex-flow: row wrap;
    justify-content: space-around;
    .shop-item-wrapper {
      flex: 0 0 25%;
    }
    .shop-item {
      padding: 20px 20px 0 20px;
      width: 208px;
    }
    .shop-item:hover {
      box-shadow: 2px 2px 13px rgb(217, 236, 255);
      cursor: pointer;
    }
    li img {
      max-width: 208px;
      max-height: 156px;
    }
    h1 {
      margin: 10px 0;
      font-weight: 400;
    }
    .shop-price {
      font-size: 12px;
      color: #666;
    }
    .shop-sales {
      font-size: 12px;
    }
    .distance {
      font-size: 14px;
      span {
        color: #3190e8;
        font-weight: 550;
      }
    }
  }
</style>
