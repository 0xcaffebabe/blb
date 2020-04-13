<template>
  <el-tabs tab-position="left" type="border-card" :value="categoryActiveName">
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
</template>

<script>
export default {
  data () {
    return {
      categoryActiveName: '1'
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
</style>
