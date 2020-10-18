<template>
  <div>
    <el-card class="my">
      <div slot="header">我的</div>
      <el-row :gutter="20">
        <el-col :span="8" class="my-left">
          <el-row>
            <el-col :span="6" style="text-align:center">
              <el-avatar circle :size="64" :src="userInfo.avatar"></el-avatar>
              <input type="file" ref="fileUploader">
              <el-button size="small" type="primary" @click="handleUpload">点击上传头像</el-button>
            </el-col>
            <el-col :span="18">
              <h3 v-show="nickNameShow" @click="nickNameShow = !nickNameShow">{{userInfo.username}}</h3>
              <el-input clearable v-show="!nickNameShow" style="width:200px" @blur="nickNameShow = !nickNameShow" :value="userInfo.username"></el-input>
            </el-col>
          </el-row>
          <el-card class="info-card">
            <div @click="showUserEditPanel('delivery')"><span class="el-icon-arrow-right" style="float:right"></span> 收货地址</div>
            <el-divider></el-divider>
            <div @click="showUserEditPanel('password')"><span class="el-icon-arrow-right" style="float:right"></span> 登录密码</div>
            <el-divider></el-divider>
            <div @click="showUserEditPanel('info')"><span class="el-icon-arrow-right" style="float:right"></span> 手机号码</div>
          </el-card>
        </el-col>
        <el-col :span="16" class="my-right">
          <task-list/>
        </el-col>
      </el-row>
    </el-card>
    <user-edit-pane :currentTab="currentTab"></user-edit-pane>
  </div>
</template>

<script>
import UserEditPane from '../components/my/UserEditPane'
import TaskList from '../components/my/TaskList'
import consumerService from '../service/ConsumerService'
export default {
  data () {
    return {
      nickNameShow: true,
      userInfo: this.$store.state.user.info,
      currentTab: 'devliery'
    }
  },
  methods: {
    async handleUpload () {
      const fileList = this.$refs.fileUploader.files
      try {
        const avatar = await consumerService.uploadAvatar(fileList)
        if (avatar) {
          this.$message.success('上传头像成功')
          this.userInfo.avatar = avatar
          if (await consumerService.updateUserInfo(this.userInfo)) {
            this.$message.success('更新资料成功')
          }
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    showUserEditPanel (tab) {
      this.currentTab = tab
      this.$store.commit('toggleUserEditPanel')
    }
  },
  components: {
    UserEditPane, TaskList
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
