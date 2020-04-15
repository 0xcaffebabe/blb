<template>
  <el-drawer
    title="购物车"
    :with-header="false"
    :visible.sync="$store.state.cartShow"
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
</template>

<script>
export default {
  props: ['cartShow'],
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
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
        font-size: 14px;
        margin: 0;
        margin-bottom: 10px;
      }
      span {
        font-size: 12px;
        color: #666;
      }
      .product-price {
        margin: 0;
        margin-top: 10px;
        color: #f60;
        font-size: 18px;
      }
    }
  }
</style>
