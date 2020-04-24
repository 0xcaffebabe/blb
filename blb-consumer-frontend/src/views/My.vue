<template>
  <div>
    <el-card class="my">
      <div slot="header">我的</div>
      <el-row :gutter="20">
        <el-col :span="8" class="my-left">
          <el-row>
            <el-col :span="6" style="text-align:center">
              <el-avatar circle :size="50"></el-avatar>
              <el-upload
  class="upload-demo"
  action="https://jsonplaceholder.typicode.com/posts/"
  :on-preview="handlePreview"
  :on-remove="handleRemove"
  :before-remove="beforeRemove"
  multiple
  :limit="3"
  :on-exceed="handleExceed"
  :file-list="fileList">
  <el-button size="small" type="primary">点击上传头像</el-button>
  <div slot="tip" class="el-upload__tip">只支持jpg/png</div>
</el-upload>
            </el-col>
            <el-col :span="18">
              <h3 v-show="nickNameShow" @click="nickNameShow = !nickNameShow">0xcaffebabe</h3>
              <el-input clearable v-show="!nickNameShow" style="width:200px" @blur="nickNameShow = !nickNameShow" value="0xcaffebabe"></el-input>
            </el-col>
          </el-row>
          <el-card class="info-card">
            <div @click="$store.commit('toggleUserEditPanel')"><span class="el-icon-arrow-right" style="float:right"></span> 收货地址</div>
            <el-divider></el-divider>
            <div><span class="el-icon-arrow-right" style="float:right"></span> 登录密码</div>
            <el-divider></el-divider>
            <div><span class="el-icon-arrow-right" style="float:right"></span> 手机号码</div>
          </el-card>
        </el-col>
        <el-col :span="16" class="my-right">
          <el-card>
            <div slot="header">任务中心</div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
    <user-edit-pane></user-edit-pane>
  </div>
</template>

<script>
import UserEditPane from '../components/my/UserEditPane'
export default {
  data () {
    return {
      nickNameShow: true
    }
  },
  components: {
    UserEditPane
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
    div {
      cursor: pointer;
    }
  }
</style>
