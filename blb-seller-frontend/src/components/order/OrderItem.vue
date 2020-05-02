<template>
  <el-card class="order-item">
          <div slot="header" class="header">
            <span>等待处理</span>
            <div>
              <h1 class="primary">31</h1>
              希望尽快送达,建议11:15之前出餐
            </div>
          </div>
          <div class="consumer-info">
            <div class="delivery-info">
              <div><span class="el-icon-phone" style="color:#409EFF"></span>17319561234</div>
              <div style="float:right"><span class="el-icon-location" style="color:#409EFF"></span>地图</div>
            </div>
            <h3>王小明</h3> <span>#90天内第3次下单</span>
            <p>漳浦县旧镇镇 漳浦第三中学 高三八班</p>
            <p>骑手取餐码:2981</p><el-button size="mini" type="primary">出餐</el-button>
          </div>
          <el-divider></el-divider>
          <div class="order-note">
            <p><span>备注：</span>不要辣，不要一次性筷子</p>
          </div>
          <el-collapse v-model="expand">
            <el-collapse-item title="商品" name="1">
              <el-table
                :show-header="false"
                :data="tableData"
                style="width: 100%">
                <el-table-column
                  width="500">
                  <template slot-scope="scope">
                    <p class="product-name">{{ scope.row.name }}</p>
                    <span class="product-spec primary">[{{scope.row.spec}}]</span>
                  </template>
                </el-table-column>
                <el-table-column
                  width="100">
                  <template slot-scope="scope">
                    <h4 class="product-num">X{{scope.row.num}}</h4>
                  </template>
                </el-table-column>
                <el-table-column :fixed="'right'">
                  <template slot-scope="scope">
                    <h4 class="product-num">{{scope.row.price}}</h4>
                  </template>
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
        </el-card>
</template>

<script>
export default {
  data () {
    return {
      expand: '1',
      tableData: [{
        name: '黄焖鸡',
        num: 1,
        price: 18.0,
        spec: '大份'
      }, {
        name: '黄焖鸭',
        num: 1,
        price: 13.0,
        spec: '正常规格'
      }, {
        name: '黄焖猪蹄',
        num: 2,
        price: 13.0,
        spec: '正常规格'
      }, {
        name: '菊花茶',
        num: 1,
        price: 6.0,
        spec: '大份'
      }]
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
