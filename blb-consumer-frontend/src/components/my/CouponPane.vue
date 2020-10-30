<template>
  <el-dialog
    title="优惠券"
    :visible="$store.state.couponPaneShow"
    width="50%"
    @close="$store.commit('toggleCouponPaneShow')"
    >
  <el-tabs v-model="activeName">
    <el-tab-pane label="红包" name="redEnv">
      有 <span style="color:#ff5340">3</span> 个红包即将过期
      <ul class="red-env-list">
        <li v-for="item in 4" :key="item + ''">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="red-env-left">
                <h2>￥5.0</h2>
                <p>满20元可用</p>
              </div>
            </el-col>
            <el-col :span="14">
              <div class="red-env-right">
                <p style="font-size:16px;color:#666">吃货联盟超级红包</p>
                <p>{{new Date().toLocaleDateString()}} 过期</p>
                <p>限收货手机号: 17359561234</p>
              </div>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" size="medium" style="margin-top:50%">去使用</el-button>
            </el-col>
          </el-row>
          <el-divider></el-divider>
        </li>
      </ul>
    </el-tab-pane>
    <el-tab-pane label="商家代金券" name="sellerToken">配置管理</el-tab-pane>
  </el-tabs>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'redEnv'
    }
  }
}
</script>

<style lang="less" slot-scope>
  .red-env-list {
    .red-env-left {
      width: 100%;
      border-right: 1px solid #ccc;
      text-align: center;
    }
    h2 {
      color: #ff5340;
      font-size: 30px;
    }
    p {
      color: #999;
    }
  }
</style>
