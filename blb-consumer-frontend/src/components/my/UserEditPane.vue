<template>
  <el-dialog
    title="编辑资料"
    :visible="$store.state.userEditPanelShow"
    width="50%"
    @close="$store.commit('toggleUserEditPanel')"
    >
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="收货地址" name="delivery">
        <ul class="delivery-list">
          <li v-for="item in deliveryList" :key="item.deliveryId" class="delivery-item">
            <el-row>
              <el-col :span="16">
                <h4>{{item.building}} {{item.detail}} <el-tag  v-if="item.defaultDelivery" effect="dark" size="medium">默认地址</el-tag></h4>
              </el-col>
              <el-col :span="8">
                <el-button class="default-btn" type="primary" size="mini"
                style="floar:right"
                v-if="!item.defaultDelivery"
                @click="updateDelivery(item)"
                >设为默认地址</el-button>
                <el-button type="danger" size="mini" @click="handleDelteDelivery(item.deliveryId)">删除</el-button>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </li>
        </ul>
        <div class="new-delivery">
          <el-form :model="deliveryForm" :rules="deliveryFormRules" ref="deliveryForm" label-width="80px">
            <el-form-item label="地址" prop="building">
              <el-input v-model="deliveryForm.building" placeholder="在地图上点击以选择地点" readonly></el-input>
              <el-amap class="amap-box"
              :vid="'amap-vue'"
              :zoom="10"
              :events="mapEvents"
              :plugin="plugin"
              >
                <!-- <el-amap-marker vid="component-marker" :position="[117.5,24]"></el-amap-marker> -->
              </el-amap>
            </el-form-item>
            <el-form-item label="详细地址" prop="detail">
              <el-input v-model="deliveryForm.detail" placeholder="详细地址"></el-input>
            </el-form-item>
            <el-checkbox v-model="deliveryForm.defaultDelivery">设为默认收货地址</el-checkbox>
            <el-button type="success" size="small" style="float:right" @click="handleSaveDelivery">保存</el-button>
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
      <el-tab-pane label="个人信息" name="phone">
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
          </el-form>
        <el-button type="success" size="small" style="float:right;margin-top:20px">确认修改</el-button>
      </el-tab-pane>
    </el-tabs>
  </el-dialog>
</template>

<script>
import deliveryService from '../../service/DeliveryService'
export default {
  data () {
    return {
      activeName: 'delivery',
      deliveryForm: {
        building: '',
        detail: '',
        defaultDelivery: false
      },
      deliveryFormRules: {
        building: [
          { required: true, message: '请选择地点', trigger: 'blur' }
        ],
        detail: [
          { required: true, message: '情输入详细地址', trigger: 'blur' }
        ]
      },
      deliveryList: [],
      mapEvents: {
        click (e) {
          const { lng, lat } = e.lnglat
          this.lng = lng
          this.lat = lat

          // 这里通过高德 SDK 完成。
          const geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: 'all'
          })
          geocoder.getAddress([lng, lat], (status, result) => {
            if (status === 'complete' && result.info === 'OK') {
              if (result && result.regeocode) {
                deliveryService.editPanel.deliveryForm.building = result.regeocode.formattedAddress
              }
            }
          })
        }
      },
      plugin: [{
        pName: 'Geolocation',
        events: {
          init (o) {
            // o 是高德地图定位插件实例
            o.getCurrentPosition((status, result) => {
              if (result && result.position) {
                self.lng = result.position.lng
                self.lat = result.position.lat
                self.center = [self.lng, self.lat]
                self.loaded = true
                self.$nextTick()
              }
            })
          }
        }
      }]
    }
  },
  methods: {
    async getDeliveryList () {
      try {
        this.deliveryList = await deliveryService.getDeliveryList()
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async addDelivery () {
      try {
        if (await deliveryService.addDelivery(this.deliveryForm)) {
          this.$message.success('添加收货信息成功')
          this.getDeliveryList()
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handleSaveDelivery () {
      this.$refs.deliveryForm.validate(v => {
        if (v) {
          this.addDelivery()
        } else {
          this.$message.error('请将信息填写完整')
        }
      })
    },
    async updateDelivery (delivery) {
      delivery.defaultDelivery = true
      try {
        if (await deliveryService.updateDelivery(delivery)) {
          this.$message.success('设为默认地址成功')
          this.getDeliveryList()
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async handleDelteDelivery (deliveryId) {
      try {
        if (await deliveryService.deleteDelivery(deliveryId)) {
          this.$message.success('删除收货信息成功')
          this.getDeliveryList()
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    deliveryService.editPanel = this
    this.getDeliveryList()
  }
}
</script>

<style lang="less" scoped>
  .amap-box {
    width: 100%;
    height: 200px;
  }
  .delivery-list {
    .el-icon-edit {
      font-size: 20px;
      height: 28px;
      line-height: 28px;
    }
    h4 {
      margin: 0;
    }
  }
</style>
