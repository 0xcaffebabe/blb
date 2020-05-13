<template>
  <div>
    <el-card>
            <div slot="header">
              {{shopInfo.shopName}} <span class="el-icon-arrow-right"></span>
            </div>
            <ul class="order-detail-list">
              <li v-for="item in productList" :key="item.productId + '-' + item.specId" class="order-detail-item">
                <el-row>
                  <el-col :span="2">
                    <el-avatar shape="square" :src="item.productImg"></el-avatar>
                  </el-col>
                  <el-col :span="22">
                    <span style="float:right">￥{{item.productPrice}}</span>
                    <span style="float:right;color:#666;margin-right:20px">X {{item.productQuantity}}</span>
                    <h4>黄焖鸡米饭</h4>
                    <span class="spec-name">{{item.specName}}</span>
                  </el-col>
                </el-row>
              </li>
              <el-divider></el-divider>
              <li class="order-detail-item">
                <el-row>
                  <el-col :span="2">
                    <el-tag size="medium" effect="dark">配送费</el-tag>
                  </el-col>
                  <el-col :span="22">
                    <span style="float:right">￥{{shopInfo.deliveryFee}}</span>
                    <span style="float:right;color:#666;margin-right:20px">X 1</span>
                  </el-col>
                </el-row>
              </li>
              <li class="order-detail-item">
                <el-row>
                  <el-col :span="2">
                    <el-tag size="medium">包装费</el-tag>
                  </el-col>
                  <el-col :span="22">
                    <span style="float:right">￥{{getTotalPackageFee()}}</span>
                    <span style="float:right;color:#666;margin-right:20px">X 1</span>
                  </el-col>
                </el-row>
              </li>
              <li class="order-detail-item">
               <el-row>
                  <el-col :span="2">
                    <el-tag size="medium" type="danger">实付</el-tag>
                  </el-col>
                  <el-col :span="22">
                    <span style="float:right">￥{{getTotalAmount()}}</span>
                  </el-col>
                </el-row>
              </li>
            </ul>
          </el-card>
  </div>
</template>

<script>
import productService from '../../service/ProductService'
export default {
  props: ['shopInfo', 'productList'],
  data () {
    return {}
  },
  methods: {
    getTotalPackageFee () {
      return productService.calcTotalPackageFee(this.productList)
    },
    getTotalAmount () {
      return productService.calcTotalAmount(this.productList)
    }
  }
}
</script>

<style lang="less" scoped>
  .order-detail-item {
    margin-bottom: 20px;
    h4 {
      margin: 0;
      font-size: 14px;
    }
    .spec-name {
      font-size: 12px;
    }
  }
</style>
