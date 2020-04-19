<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <div slot="header">待处理订单</div>
          <el-row>
            <el-col :span="6" class="data-item">
              <h1 style="color:#409EFF">3</h1>
              <p>新订单</p>
            </el-col>
             <el-col :span="6" class="data-item">
              <h1>0</h1>
              <p>异常订单</p>
            </el-col>
            <el-col :span="6" class="data-item">
              <h1 class="warning">2</h1>
              <p>催单</p>
            </el-col>
            <el-col :span="6" class="data-item">
              <h1>0</h1>
              <p>退单</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header">
            待处理反馈
          </div>
          <el-row>
            <el-col :span="12" class="data-item">
              <h1 class="danger">12</h1>
              <p>差评</p>
            </el-col>
            <el-col :span="12" class="data-item">
              <h1 class="success">134</h1>
              <p>好评</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="16">
        <el-card>
          <div slot="header">今日总览</div>
          <el-row>
            <el-col :span="12" class="data-item">
              <h1>324</h1>
              <p>今日订单</p>
            </el-col>
            <el-col :span="12" class="data-item">
              <h1 class="primary">￥6038</h1>
              <p>今日营业额</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header">
            活动
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top:20px">
      <div slot="header">任务中心</div>
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
  .primary {
    color: #409EFF!important;
  }
  .success {
    color: #67C23A!important;
  }
  .warning {
    color: #E6A23C!important;
  }
  .danger {
    color: #F56C6C!important;
  }
  .info {
    color: #909399!important;
  }
  .data-item {
    h1 {
      margin: 0;
      text-align:center;
      font-size: 36px;
      color: #909399;
    }
    p {
      text-align: center;
    }
  }
</style>
