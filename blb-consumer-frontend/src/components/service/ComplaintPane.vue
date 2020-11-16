<template>
  <el-dialog
    :visible="$store.state.complaintPaneShow"
    width="50%"
    @close="$store.commit('toggleComplaintPane')"
    >
    <el-row>
      <el-col :span="4">
        <el-avatar src="/imgs/foods/huangmenji.jpg" fit="cover" shape="square"  :size="48"/>
      </el-col>
      <el-col :span="16">
        <h4 class="shop-name">黄焖鸡米饭 <span class="el-icon-arrow-right"></span></h4>
      </el-col>
    </el-row>
    <el-divider>投诉详情</el-divider>
    <div class="order-id">
      投诉订单号:
      <el-tooltip placement="top">
        <template #content>
          多行信息<br/>第二行信息
        </template>
        <el-link type="primary"> 399 288 600 456</el-link>
      </el-tooltip>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
  .order-id {
    .el-link {
      font-size: 14px;
    }
  }
</style>
