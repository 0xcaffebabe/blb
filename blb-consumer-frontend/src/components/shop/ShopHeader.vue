<template>
  <div>
    <el-card class="shop-header" :body-style="{padding:0}">
      <div class="shop-header-img">
        <el-image :src="info.shopLogo" fit="cover"/>
      </div>
      <el-row class="shop-detail" :gutter="20">
        <el-col :span="4">
          <el-image
            class="shop-logo"
            style="width: 150px; height: 150px"
            :src="info.shopLogo"
            :preview-src-list="[info.shopLogo]"
            fit="cover"></el-image>
        </el-col>
        <el-col :span="16">
          <h1 class="shop-name">{{info.shopName}}</h1>
          <p class="shop-delivery">
            <el-tag effect="dark" type="primary" size="mini" style="vertical-align:top">蜂鸟专送</el-tag>
            / 35分钟送达
             / 配送费￥{{info.deliveryFee}} 起送价 ￥{{info.startingPrice}}
            </p>
          <p class="shop-slogan">{{info.shopSlogan}}</p>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  props: ['info'],
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
  .shop-header-img {
    padding: 0;
    -webkit-tap-highlight-color: transparent;
    -webkit-font-smoothing: antialiased;
    .el-image {
      width: 100%;
      height: 200px;
      filter: blur(10px);
    }
  }
  .shop-detail {
    margin-top: -200px;
    top: 0;
    .shop-delivery {
      color: white;
    }
    .shop-slogan {
      color: white;
    }
  }
  .shop-logo {
    margin-left: 10px;
    border-radius: 10px;
    box-shadow: 2px 2px 13px rgba(0,0,0,.5);
  }
  .shop-name {
    margin: 0;
    font-size: 36px;
    color: white;
    font-weight: 600;
  }
</style>
