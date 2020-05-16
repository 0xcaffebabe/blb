<template>
  <div>
    <el-card>
      <div slot="header">
        {{title}}
      </div>
      <el-alert v-if="shopList.length == 0" title="没有数据"></el-alert>
      <ul class="shop-list">
        <li class="shop-item-wrapper" v-for="item in shopList" :key="item.shopId">
          <div class="shop-item" @click="handleShopItemClick(item)">
            <el-image fit="cover" :src="item.shopLogo" alt="" style="height:128px">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
            <h1>{{item.shopName}}</h1>
            <el-rate
              :value="item.ranking"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}">
            </el-rate>
            <span class="shop-sales">月售:{{item.sales}}单</span>
            <div>
              <span class="shop-price">起送:￥{{item.startingPrice}}/配送费:￥{{item.deliveryFee}}</span>
            </div>
            <p class="distance el-icon-time">{{item.distance}} / <span>{{item.deliveryTime}}</span></p>
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
    handleShopItemClick (shop) {
      this.$router.push('/shop/' + shop.shopId)
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
      text-align: center;
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
