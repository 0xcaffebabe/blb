<template>
  <div>
    <el-card class="my">
      <div slot="header">我的</div>
      <el-row>
        <el-col :span="8" class="my-left">
          <el-row>
            <el-col :span="6">
              <el-avatar circle :size="50"></el-avatar>
            </el-col>
            <el-col :span="18">
              <h3 v-show="nickNameShow" @click="nickNameShow = !nickNameShow">0xcaffebabe</h3>
              <el-input clearable v-show="!nickNameShow" style="width:200px" @blur="nickNameShow = !nickNameShow" value="0xcaffebabe"></el-input>
            </el-col>
          </el-row>
          <el-card class="info-card">
            <div><span class="el-icon-arrow-right" style="float:right"></span> 收货地址</div>
            <el-divider></el-divider>
            <div><span class="el-icon-arrow-right" style="float:right"></span> 登录密码</div>
          </el-card>
        </el-col>
        <el-col :span="16" class="my-right">
          xxx
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      nickNameShow: true
    }
  }
}
</script>

<style lang="less" scoped>
  .my-left {
    h3 {
      margin-top: 10px;
      margin-left: 10px;
    }
  }
  .info-card {
    margin-top: 10px;
    font-size: 14px;
  }
</style>
