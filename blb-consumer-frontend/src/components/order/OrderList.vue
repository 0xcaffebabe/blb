<template>
  <div>
    <el-card>
      <div slot="header">我的订单</div>
      <div class="order-list">
        <el-card v-for="item in 6" :key="item" class="order-list-item">
            <el-row>
              <el-col :span="2">
                <el-avatar src="" shape="square" :size="64"></el-avatar>
              </el-col>
              <el-col :span="22">
                <span class="order-status">已取消</span>
                <h4 class="shop-name">华莱士-全🥩汉堡(漳浦旧镇店) <span class="el-icon-arrow-right"></span></h4>
                <p class="order-date">2020-03-28 20:07</p>
                <el-divider></el-divider>
                <span style="float:right">￥52.5</span>
                <p class="order-detail">2个香辣鸡腿堡+中可 等2件</p>
                <el-button size="mini" type="primary" style="float:right;margin-top:5px" @click="$store.commit('toggleOrderDetail')">再来一单</el-button>
              </el-col>
            </el-row>
          </el-card>
          </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
  .order-list-item {
    margin-bottom: 20px;
    .shop-name {
      margin: 0;
      padding: 0;
      vertical-align: middle;
      font-size: 14px;
    }
    .order-date {
      font-size: 12px;
      color: #666;
    }
    .order-status {
      font-size: 14px;
      float: right;
    }
    .order-detail {
      font-size: 14px;
    }
  }
</style>
