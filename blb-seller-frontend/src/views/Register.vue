<template>
  <el-container>
    <el-header>
      <p style="color:white;text-align:center">饱了吧 · 店铺注册</p>
    </el-header>
    <el-main>
      <div class="steps-wrapper" :class="{'fixed-steps': fixedSteps}">
        <el-steps :active="1" finish-status="success" simple>
          <el-step title="门店信息" ></el-step>
          <el-step title="资质信息" ></el-step>
          <el-step title="开店人信息" ></el-step>
        </el-steps>
      </div>
      <el-row style="margin-top:20px">
            <el-col :span="2">
              基本信息
            </el-col>
            <el-col :span="14">
              <el-form :model="registerForm" :rules="registerFormRules" ref="registerForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="门店名称" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                <el-form-item label="所在城市" prop="name">
                  <el-cascader
                    v-model="value"
                    :options="options"
                    :props="{ expandTrigger: 'hover' }"
                    @change="handleChange"></el-cascader>
                </el-form-item>
                 <el-form-item label="门店地址" prop="name">
                  <el-input v-model="registerForm.shopAddress"></el-input>
                  <el-amap class="amap-box" :vid="'amap-vue'" :zoom="10" :events="mapEvents" :plugin="plugin">
                  </el-amap>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
          <el-divider></el-divider>
          <el-row>
            <el-col :span="2">
              经营信息
            </el-col>
            <el-col :span="14">
              <el-form :model="registerForm" :rules="registerFormRules" ref="registerForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="店铺分类" prop="name">
                  <el-cascader
                    v-model="value"
                    :options="categoryList"
                    :props="{ expandTrigger: 'hover', value: 'categoryId', label: 'categoryName', children: 'subCategoryList' }"
                    @change="handleChange"></el-cascader>
                </el-form-item>
                <el-form-item label="店铺logo" prop="name">
                 <el-upload
                   class="avatar-uploader"
                   action="https://jsonplaceholder.typicode.com/posts/"
                   :show-file-list="false"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload">
                   <img v-if="imageUrl" :src="imageUrl" class="avatar">
                   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                 </el-upload>
                </el-form-item>
                 <el-form-item label="店铺简介" prop="name">
                  <el-input v-model="registerForm.name" type="textarea"></el-input>
                </el-form-item>
                 <el-form-item label="店铺slogan" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="营业时间" prop="name">
                  <el-time-picker
                    is-range
                    v-model="registerForm.bussinessHour"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    placeholder="选择时间范围">
                  </el-time-picker>
                </el-form-item>
                 <el-form-item label="店铺联系电话" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="配送费" prop="name">
                  <el-input-number v-model="registerForm.name" size="medium"></el-input-number>
                </el-form-item>
                 <el-form-item label="起送价" prop="name">
                  <el-input-number v-model="registerForm.name" size="medium"></el-input-number>
                </el-form-item>
                <el-form-item label="营业执照" prop="name">
                 <el-upload
                   class="avatar-uploader"
                   action="https://jsonplaceholder.typicode.com/posts/"
                   :show-file-list="false"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload">
                   <img v-if="imageUrl" :src="imageUrl" class="avatar">
                   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                 </el-upload>
                </el-form-item>
                <el-form-item label="餐饮服务许可证" prop="name">
                 <el-upload
                   class="avatar-uploader"
                   action="https://jsonplaceholder.typicode.com/posts/"
                   :show-file-list="false"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload">
                   <img v-if="imageUrl" :src="imageUrl" class="avatar">
                   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                 </el-upload>
                </el-form-item>
              </el-form>
              <el-button type="primary" style="float:right">注册店铺</el-button>
            </el-col>
          </el-row>
    </el-main>
  </el-container>
</template>

<script>
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      registerForm: {
        shopName: '',
        shopAddress: '',
        categoryId: -1,
        shopLogo: '',
        shopDesc: '',
        shopSlogan: '',
        bussinessHour: '',
        phone: '',
        deliveryFee: '',
        startingPrice: '',
        businessLicense: '',
        foodServiceLicense: ''
      },
      registerFormRules: [],
      categoryList: [],
      fixedSteps: false,
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
                this.registerForm.shopAddress = result.regeocode.formattedAddress
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
    async getCategoryList () {
      try {
        this.categoryList = await shopService.getShopCategory()
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getCategoryList()
  },
  mounted () {
    // const steps = document.querySelector('.steps-wrapper')
    window.addEventListener('scroll', (event) => {
      if (window.scrollY > 70) {
        this.fixedSteps = true
      } else {
        this.fixedSteps = false
      }
    })
  }
}
</script>

<style lang="less" scoped>
  .el-header {
    background: #3094fa;
  }
  .fixed-steps {
    position: fixed;
    top: 0;
  }
  .steps-wrapper {
    width: 100%;
    z-index: 20;
  }
  .amap-box {
    height: 400px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 128px;
    height: 128px;
    display: block;
  }
</style>
