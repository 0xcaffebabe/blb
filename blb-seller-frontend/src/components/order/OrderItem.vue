<template>
  <el-card class="order-item">
          <div slot="header" class="header">
            <span>等待处理</span>
            <div>
              <h1 class="primary">{{order.orderId}}</h1>
              希望尽快送达,建议11:15之前出餐
            </div>
          </div>
          <div class="consumer-info">
            <div class="delivery-info">
              <div><span class="el-icon-phone" style="color:#409EFF"></span>{{order.consumerPhone}}</div>
              <div style="float:right"><span class="el-icon-location" style="color:#409EFF"></span>地图</div>
            </div>
            <h3>{{order.consumerName}}</h3> <span>#90天内第3次下单</span>
            <p>{{order.consumerAddress}}</p>
            <p>骑手取餐码:2981</p><el-button size="mini" type="primary" @click="dinnerOut(order.orderId)">出餐</el-button>
          </div>
          <el-divider></el-divider>
          <div class="order-note">
            <p><span>备注：</span>{{order.orderNote}}</p>
          </div>
          <el-collapse v-model="expand">
            <el-collapse-item title="商品" name="1">
              <el-table
                :show-header="false"
                :data="order.productList"
                style="width: 100%">
                <el-table-column
                  width="500">
                  <template slot-scope="scope">
                    <p class="product-name">{{ scope.row.productName }}</p>
                    <span class="product-spec primary">[{{scope.row.specName}}]</span>
                  </template>
                </el-table-column>
                <el-table-column
                  width="100">
                  <template slot-scope="scope">
                    <h4 class="product-num">X{{scope.row.productQuantity}}</h4>
                  </template>
                </el-table-column>
                <el-table-column :fixed="'right'">
                  <template slot-scope="scope">
                    <h4 class="product-num">{{scope.row.productPrice}}</h4>
                  </template>
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
        </el-card>
</template>

<script>
import orderService from '../../service/OrderService'
export default {
  props: ['order'],
  data () {
    return {
      expand: '1'
    }
  },
  methods: {
    async dinnerOut (orderId) {
      try {
        if (await orderService.dinnerOut(orderId)) {
          this.$message.success('出餐成功')
          this.$emit('refreshOrder')
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .order-item {
    margin-top:20px;
  }
  .header {
    h1 {
      display: inline;
    }
    span {
      float: right;
      font-size: 14px;
      height: 30px;
      line-height: 30px;
    }
  }
   .consumer-info {
    h3 {
      margin: 0;
      display: inline;
    }
    span {
      font-size: 14px;
      color: #666;
    }
    p {
      margin-top: 10px;
      color: #666;
      font-size: 14px;
    }
  }
  .order-note {
    span {
      color: #E6A23C;
    }
  }
  .delivery-info {
    float:right;
    color:#409EFF
  }
  .product-name {
    font-size: 16px;
    margin: 0;
    padding: 0;
  }
  .product-spec {
    font-size: 12px;
    font-weight: 600;
  }
  .product-num {
    font-weight: 700;
  }
  .overview {
    margin-top: 20px;
    p {
      margin: 0;
      padding: 5px;
      font-weight: 600;
    }
  }
  .dilivery-overview {
    font-size: 14px;
    li {
      padding: 5px;
    }
  }
</style>
