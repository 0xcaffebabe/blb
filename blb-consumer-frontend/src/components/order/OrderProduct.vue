<template>
  <div>
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
  .order-detail-item {
    margin-bottom: 20px;
    h4 {
      margin: 0;
      margin-top: 10px;
      font-size: 14px;
    }
  }
</style>
