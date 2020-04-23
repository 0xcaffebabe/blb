<template>
  <el-dialog
    title="编辑资料"
    :visible="$store.state.userEditPanelShow"
    width="30%"
    @close="$store.commit('toggleUserEditPanel')"
    >
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="收货地址" name="delivery">
        <ul class="delivery-list">
          <li v-for="item in 2" :key="item">
            <el-row>
              <el-col :span="20">
                <h4>泉州师范学院软件学院 男B305</h4>
                <p>蔡徐坤(先生) 17359561234</p>
              </el-col>
              <el-col :span="4">
                <span class="el-icon-edit"></span>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </li>
        </ul>
        <div class="new-delivery">
          <el-form :model="deliveryForm" :rules="deliveryFormRules" ref="deliveryForm" label-width="70px">
            <el-form-item label="联系人" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="姓名"></el-input>
              <el-radio-group v-model="radio1" size="mini">
                <el-radio-button label="先生"></el-radio-button>
                <el-radio-button label="女士"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="电话" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="手机号码"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="地址" readonly></el-input>
              <el-amap class="amap-box" :vid="'amap-vue'" :zoom="10" :center="[117.5,24]">
                <el-amap-marker vid="component-marker" :position="[117.5,24]"></el-amap-marker>
              </el-amap>
            </el-form-item>
            <el-form-item label="详细地址" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="详细地址"></el-input>
            </el-form-item>
            <el-button type="success" size="small" style="float:right">保存</el-button>
          </el-form>
        </div>
      </el-tab-pane>
      <el-tab-pane label="登录密码" name="password">
        <el-form :model="deliveryForm" :rules="deliveryFormRules" ref="deliveryForm" label-width="100px">
            <el-form-item label="旧密码" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="旧密码" show-password clearable></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="新密码" show-password clearable></el-input>
            </el-form-item>
            <el-form-item label="重复新密码" prop="name">
              <el-input v-model="deliveryForm.name" placeholder="重复新密码" show-password clearable></el-input>
            </el-form-item>
            <el-button type="success" size="small" style="float:right">确认修改</el-button>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="手机号码" name="phone">
        <el-input value="17359561234"></el-input>
        <el-button type="success" size="small" style="float:right;margin-top:20px">确认修改</el-button>
      </el-tab-pane>
    </el-tabs>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'phone',
      deliveryForm: {},
      deliveryFormRules: []
    }
  }
}
</script>

<style lang="less" scoped>
  .amap-box {
    width: 100%;
    height: 150px;
  }
  .delivery-list {
    .el-icon-edit {
      font-size: 20px;
      height: 56px;
      line-height: 56px;
    }
    h4 {
      margin: 0;
    }
  }
</style>
