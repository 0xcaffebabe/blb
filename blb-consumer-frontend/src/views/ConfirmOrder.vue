<template>
  <div>
    <el-card>
      <div slot="header">
        确认下单
      </div>
        <el-row :gutter="10">
          <el-col :span="16">
            <order-product/>
          </el-col>
          <el-col :span="8">
            <el-card>
              <el-row>
                <el-col :span="4">
                  <span class="el-icon-location location-icon"></span>
                </el-col>
                <el-col :span="18">
                  <strong>蔡徐坤</strong> 先生 <span class="phone">17359561234</span>
                  <p class="address">翻斗大街42栋 3602</p>
                </el-col>
                <el-col :span="2">
                  <span class="el-icon-arrow-right"></span>
                </el-col>
              </el-row>
            </el-card>
            <el-card class="delivery-time">
              <el-col :span="8"><h1>送达时间</h1></el-col>
              <el-col :span="16">
                <p>尽快送达 | 预计 11:15</p>
                <el-tag effect="dark" type="primary" size="mini">蜂鸟专送</el-tag>
              </el-col>
            </el-card>
            <el-card style="margin-top:10px">
              <ul>
                <li>
                    <span>支付方式</span>
                    <el-select v-model="payValue" placeholder="请选择" style="width:100px;margin-left:100px">
                      <el-option
                        v-for="item in payOptions"
                        :key="item"
                        :label="item"
                        :value="item">
                      </el-option>
                    </el-select>
                </li>
                <el-divider></el-divider>
                <li>
                  <p>订单备注</p>
                  <el-input type="textarea"></el-input>
                </li>
              </ul>
            </el-card>
            <el-card style="margin-top:10px">
              <span>待支付:￥ 405.00</span>
              <el-button type="success" style="margin-left:50px" @click="$router.push('pay')">确认下单</el-button>
            </el-card>
          </el-col>
        </el-row>
    </el-card>
  </div>
</template>

<script>
import OrderProduct from '../components/order/OrderProduct'
export default {
  data () {
    return {
      payValue: '支付宝',
      payOptions: [
        '支付宝', '银联'
      ]
    }
  },
  components: {
    OrderProduct
  }
}
</script>

<style lang="less" scoped>
  .el-icon-location {
    font-size:32px;
    line-height:48px;
    height:48px;
    color:rgb(49, 144, 232);
  }
  .el-icon-arrow-right {
    font-size: 24px;
    line-height:56px;
    height:56px;
  }
  .phone {
    font-size: 14px;
  }
  .address {
    margin-top: 5px;
  }
  .delivery-time {
    margin-top: 10px;
    padding-bottom: 20px;
    h1 {
      margin: 0;
      font-size: 20px;
      height: 44px;
      line-height: 44px;
    }
    p {
      margin: 0;
      color:rgb(49, 144, 232);
    }
    .el-tag {
      float: right;
      margin-top: 10px;
    }
  }
</style>
