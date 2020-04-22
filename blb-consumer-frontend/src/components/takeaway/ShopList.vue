<template>
  <div>
    <el-card>
      <div slot="header">
        {{title}}
      </div>
      <ul class="shop-list">
        <li class="shop-item-wrapper" v-for="item in shopList" :key="item">
          <div class="shop-item" @click="handleShopItemClick">
            <el-image src="https://p1.meituan.net/waimaipoi/23d94486b3882b8eb6101aa251bbd45030720.jpg" alt="">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
            <h1>小康烘焙</h1>
            <el-rate
              :value="3.7"
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
  props: ['title', 'shopList'],
  data () {
    return {}
  },
  methods: {
    handleShopItemClick () {
      this.$router.push('shop')
    }
  }
}
</script>

<style lang="less" scoped]>
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
      box-sizing: border-box;
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
      font-size: 12px;
      span {
        color: #3190e8;
        font-weight: 550;
      }
    }
  }
</style>
