<template>
  <el-card>
    <div slot="header">任务中心</div>
    <el-progress :percentage="25"></el-progress>
    <ul class="task-list">
      <li>
        <el-row>
          <el-col :span="6">
            <span class="el-icon el-icon-user"> 上传头像</span>
          </el-col>
          <el-col :span="18">
            <el-button type="primary" size="mini" style="float: right"
              >去完成</el-button
            >
          </el-col>
        </el-row>
      </li>
      <li>
        <el-row>
          <el-col :span="6">
            <span class="el-icon el-icon-phone"> 绑定手机</span>
          </el-col>
          <el-col :span="18">
            <el-button type="primary" size="mini" style="float: right"
              >去完成</el-button
            >
          </el-col>
        </el-row>
      </li>
      <li>
        <el-row>
          <el-col :span="6">
            <span class="el-icon el-icon-shopping-cart-1"> 完成首笔订单</span>
          </el-col>
          <el-col :span="18">
            <el-button type="primary" size="mini" style="float: right"
              >去完成</el-button
            >
          </el-col>
        </el-row>
      </li>
      <li>
        <el-row>
          <el-col :span="6">
            <span class="el-icon el-icon-share"> <del>分享给好友</del></span>
          </el-col>
          <el-col :span="18">
            <el-button
              type="primary"
              size="mini"
              style="float: right"
              :disabled="true"
              >已完成</el-button
            >
          </el-col>
        </el-row>
      </li>
    </ul>
  </el-card>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
  .task-list li {
    padding: 5px 0;
  }
</style>
