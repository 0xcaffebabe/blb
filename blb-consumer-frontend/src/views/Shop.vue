<template>
  <div>
    <el-card class="shop-header" :body-style="{padding:0}">
      <div class="shop-header-img">
        <el-image src="https://p1.meituan.net/waimaipoi/23d94486b3882b8eb6101aa251bbd45030720.jpg" fit="cover"/>
      </div>
      <el-row class="shop-detail" :gutter="20">
        <el-col :span="4">
          <el-image
            class="shop-logo"
            style="width: 150px; height: 150px"
            src="https://p1.meituan.net/waimaipoi/23d94486b3882b8eb6101aa251bbd45030720.jpg"
            :preview-src-list="['https://p1.meituan.net/waimaipoi/23d94486b3882b8eb6101aa251bbd45030720.jpg']"
            fit="cover"></el-image>
        </el-col>
        <el-col :span="16">
          <h1 class="shop-name">小康烘焙</h1>
          <p class="shop-delivery">
            <el-tag effect="dark" type="primary" size="mini" style="vertical-align:top">蜂鸟专送</el-tag>
            / 35分钟送达
             / 配送费￥5 起送价 ￥20
            </p>
          <p class="shop-slogan">公告：欢迎光临，用餐高峰请提前下单，谢谢</p>
        </el-col>
      </el-row>
    </el-card>
    <!-- 菜单选项栏 -->
    <el-card>
        <el-tabs v-model="activeName">
          <el-tab-pane label="商品" name="product">
            <el-tabs tab-position="left" type="border-card" :value="productActiveName">
              <!-- 商品分类选项栏 -->
              <el-tab-pane label="好吃的🍔" :name="item+''" v-for="item in 6" :key="item">
                <el-card>
                  <div slot="header" class="clearfix product-cate-title">
                    <h2>好吃的🍔</h2><span> 香香甜甜的烤面筋</span>
                  </div>
                  <ul class="product-list">
                    <li v-for="item in 6" :key="item">
                      <el-row :gutter="20">
                        <el-col :span="6">
                          <el-image src="./food.png" :preview-src-list="['./food.png']" fit="cover"></el-image>
                        </el-col>
                        <el-col :span="12">
                          <h2>华莱士全家桶</h2>
                          <p class="product-detail">原料:鸡腿*3 可乐*2 童子鸡*1</p>
                          <p class="product-sales">月售:606份 好评率:38%</p>
                        </el-col>
                        <el-col :span="6">
                          <h1 class="product-price">￥20</h1>
                          <el-button type="primary" icon="el-icon-plus" circle size="small"></el-button>
                        </el-col>
                      </el-row>
                      <el-divider></el-divider>
                    </li>
                  </ul>
                </el-card>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
          <el-tab-pane label="评价" name="second">评价</el-tab-pane>
          <el-tab-pane label="商家" name="third">商家</el-tab-pane>
        </el-tabs>
    </el-card>
    <!-- 购物车抽屉 -->
    <el-drawer
      title="购物车"
      :with-header="false"
      :visible.sync="drawer"
      :direction="'rtl'">
      <el-card class="cart-card">
        <div slot="header" class="cart-header">
          <h2>购物车</h2>
          <el-button icon="el-icon-delete" type="danger" size="mini">清空</el-button>
        </div>
        <ul class="cart-list">
          <li class="cart-list-item" v-for="item in 7" :key="item">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-image src="./food.png" fit="cover"></el-image>
              </el-col>
              <el-col :span="18">
                <el-row>
                  <el-col :span="14">
                    <h3>华莱士全家桶</h3>
                    <span>套餐1</span>
                    <h1 class="product-price">￥20</h1>
                  </el-col>
                  <el-col :span="10">
                    <el-input-number label="描述文字" size="mini" style="width:100px;margin-top:20px"></el-input-number>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </li>
        </ul>
      </el-card>
    </el-drawer>
    <div class="cart-bar">
      <el-badge :value="12">
        <el-button icon="el-icon-shopping-cart-2" circle class="cart-button" @click="drawer = true"></el-button>
      </el-badge>
      <el-button type="success" class="pay-button">结算</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'product',
      productActiveName: '1',
      drawer: true
    }
  }
}
</script>

<style lang="less" scoped>
  .product-cate-title {
    h2 {
      display: inline;
      margin: 0;
      padding: 0;
    }
    span {
      color: #666;
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
  .product-list {
    .el-image {
      max-height: 150px;
      max-width: 150px;
    }
    li :hover {
      cursor: pointer;
    }
    li:hover .el-divider {
      cursor: pointer;
      box-shadow: 2px 2px 13px rgba(0,0,0,.5);
    }
    h2 {
      margin: 0;
    }
    .product-detail {
      font-size: 14px;
      color: #666;
    }
    .product-sales {
      font-size: 16px;
    }
    .product-price {
      margin: 0;
      margin-bottom: 40px;
      font-size: 36px;
      color: #f60;
    }
    .el-button {
      margin-left: 40px;
    }
  }
  .cart-bar {
    z-index: 1;
    float: left;
    position: fixed;
    top: 50%;
    transform: translateY(-50%);
    right: 0;
    height: 200px;
    width: 40px;
    background-color: #3d3d3f;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    .cart-button {
      font-size: 24px;
      margin-top: 20px;
      position: absolute;
      left: -20px;
      border: 4px solid #3d3d3f;
      background-color: #3190e8;
      color: white;
    }
    .pay-button {
      padding: 0;
      height: 50px;
      position: absolute;
      font-weight: 600;
      width: 100%;
      bottom: 0;
      margin-left: 0;
      border-radius: 0;
      border-bottom-left-radius: 5px;
    }
  }
  .cart-card {
    h2 {
      display: inline;
      margin: 0;
    }
    .el-button {
      float: right;
    }
  }
  .cart-list {
    padding: 0;
    .cart-list-item {
      .el-image {
        width: 64px;
        height: 64px;
      }
      h3 {
        margin: 0;
      }
      span {
        font-size: 14px;
        color: #666;
      }
      .product-price {
        margin: 0;
        color: #f60;
        font-size: 20px;
      }
    }
  }
</style>
