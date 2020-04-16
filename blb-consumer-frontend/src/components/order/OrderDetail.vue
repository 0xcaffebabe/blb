<template>
  <el-dialog
    title="订单详情"
    :visible="$store.state.orderDetailShow"
    @close="$store.commit('toggleOrderDetail')"
    width="80%">
      <el-row :gutter="20">
        <el-col :span="20">
          <el-card>
            <div slot="header">
              华莱士-全🍔汉堡(旧镇店) <span class="el-icon-arrow-right"></span>
            </div>
            <ul class="order-detail-list">
              <li v-for="item in 3" :key="item" class="order-detail-item">
                <el-row>
                  <el-col :span="2">
                    <el-avatar shape="square"></el-avatar>
                  </el-col>
                  <el-col :span="22">
                    <span style="float:right">￥25.09</span>
                    <span style="float:right;color:#666;margin-right:20px">X 1</span>
                    <h4>牛肉汉堡</h4>
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
                    <span style="float:right">￥25.09</span>
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
                    <span style="float:right">￥25.09</span>
                    <span style="float:right;color:#666;margin-right:20px">X 1</span>
                  </el-col>
                </el-row>
              </li>
            </ul>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card class="order-detail-header">
            <el-avatar :size="80"></el-avatar>
            <h2>支付超时</h2>
            <el-button type="primary" size="mini" @click="$router.push('shop')">再来一单</el-button>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top:10px">
        <el-col :span="12">
          <el-card>
            <div slot="header">配送信息</div>
            <ul class="delivery-info">
              <li>送达时间：尽快送达</li>
              <li>送货地址：漳浦三中 旧镇镇城外村</li>
              <li>收货人：蔡徐坤</li>
              <li>手机：1731111111</li>
            </ul>
            <el-divider></el-divider>
            <el-timeline>
              <el-timeline-item type="success" timestamp='2020-04-03 20:46' :key="1">顾客下单</el-timeline-item>
              <el-timeline-item type="success" timestamp='2020-04-03 21:46' :key="2">商家制作</el-timeline-item>
              <el-timeline-item type="primary" timestamp='2020-04-03 22:46' :key="3">骑手配送</el-timeline-item>
              <el-timeline-item type="info" timestamp='2020-04-03 23:46' :key="4">订单完结</el-timeline-item>
            </el-timeline>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="order-info">
            <div slot="header">订单信息</div>
            <ul>
              <li>订单号: 355 600 789 123</li>
              <li>创建时间：2020-03-28 20::07</li>
            </ul>
          </el-card>
          <el-card style="margin-top:10px">
            <div slot="header">地图</div>
            <el-amap class="amap-box" :vid="'amap-vue'" :zoom="10" :center="[117.5,24]">
              <el-amap-marker vid="component-marker" :position="[117.5,24]"></el-amap-marker>
            </el-amap>
          </el-card>
        </el-col>
      </el-row>
  </el-dialog>
</template>

<script>
export default {
  props: ['show'],
  data () {
    return { }
  }
}
</script>

<style lang="less" scoped>
  .amap-box {
    width: 100%;
    height: 200px;
  }
  ul {
    margin: 0;
    padding: 0;
  }
  .order-detail-header {
    text-align: center;
  }
  .order-detail-item {
    margin-bottom: 20px;
    h4 {
      margin: 0;
      margin-top: 10px;
      font-size: 14px;
    }
  }
  .delivery-info, .order-info {
      font-size: 14px;
      color: #666;
      li {
        margin-bottom: 10px;
      }
  }
</style>
