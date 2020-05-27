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
                <el-form-item label="门店名称" prop="shopName">
                  <el-input v-model="registerForm.shopName"></el-input>
                </el-form-item>
                 <el-form-item label="门店地址" prop="shopAddress">
                  <el-input v-model="registerForm.shopAddress" readonly placeholder="请在下方的地图中选择店铺地址"></el-input>
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
                <el-form-item label="店铺分类" prop="categoryId">
                  <el-cascader
                    v-model="registerForm.categoryId"
                    :options="categoryList"
                    :props="{ expandTrigger: 'hover', value: 'categoryId', label: 'categoryName', children: 'subCategoryList'}"></el-cascader>
                </el-form-item>
                <el-form-item label="店铺logo" prop="shopLogo">
                  <input type="file" ref="shopLogoFile"/>
                  <el-button size="mini" type="primary" @click="handleUploadShopLogo">上传店铺LOGO</el-button>
                  <img v-if="registerForm.shopLogo" :src="registerForm.shopLogo" class="avatar">
                </el-form-item>
                 <el-form-item label="店铺简介" prop="shopDesc">
                  <el-input v-model="registerForm.shopDesc" type="textarea"></el-input>
                </el-form-item>
                 <el-form-item label="店铺slogan" prop="shopSlogan">
                  <el-input v-model="registerForm.shopSlogan"></el-input>
                </el-form-item>
                 <el-form-item label="营业时间" prop="bussinessHour">
                  <el-time-picker
                    is-range
                    v-model="registerForm.bussinessHour"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    placeholder="选择时间范围">
                  </el-time-picker>
                </el-form-item>
                 <el-form-item label="店铺联系电话" prop="phone">
                  <el-input v-model="registerForm.phone"></el-input>
                </el-form-item>
                 <el-form-item label="配送费" prop="deliveryFee">
                  <el-input-number v-model="registerForm.deliveryFee" size="medium"></el-input-number>
                </el-form-item>
                 <el-form-item label="起送价" prop="startingPrice">
                  <el-input-number v-model="registerForm.startingPrice" size="medium"></el-input-number>
                </el-form-item>
                <el-form-item label="营业执照" prop="businessLicense">
                  <input type="file" ref="bussinessFile">
                  <el-button size="mini" type="primary" @click="handleUploadBussinessLicense">上传店铺营业执照</el-button>
                  <img v-if="registerForm.businessLicense" :src="registerForm.businessLicense" class="avatar">
                </el-form-item>
                <el-form-item label="餐饮服务许可证" prop="foodServiceLicense">
                  <input type="file" ref="foodLicenseFile">
                  <el-button size="mini" type="primary" @click="handleUploadFoodLicense">上传餐饮服务许可证</el-button>
                  <img v-if="registerForm.foodServiceLicense" :src="registerForm.foodServiceLicense" class="avatar">
                </el-form-item>
              </el-form>
              <el-button type="primary" style="float:right" @click="registerShop">注册店铺</el-button>
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
        deliveryFee: '0',
        startingPrice: '0',
        businessLicense: '',
        foodServiceLicense: ''
      },
      registerFormRules: {
        shopName: [
          { required: true, message: '门店名称不得为空', trigger: 'blur' }
        ],
        shopAddress: [
          { required: true, message: '门店地址不得为空', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '店铺分类不得为空', trigger: 'blur' }
        ],
        shopLogo: [
          { required: true, message: '店铺logo不得为空', trigger: 'blur' }
        ],
        shopDesc: [
          { required: true, message: '店铺简介不得为空', trigger: 'blur' }
        ],
        bussinessHour: [
          { required: true, message: '营业时间不得为空', trigger: 'blur' }
        ]
      },
      categoryList: [],
      fixedSteps: false,
      mapEvents: {
        click (e) {
          const { lng, lat } = e.lnglat
          this.lng = lng
          this.lat = lat
          shopService.register.registerForm.location = this.lng + ',' + this.lat
          // 这里通过高德 SDK 完成。
          const geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: 'all'
          })
          geocoder.getAddress([lng, lat], (status, result) => {
            if (status === 'complete' && result.info === 'OK') {
              if (result && result.regeocode) {
                shopService.register.registerForm.shopAddress = result.regeocode.formattedAddress
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
    },
    async handleUploadShopLogo () {
      try {
        const data = await shopService.upload(this.$refs.shopLogoFile.files)
        this.registerForm.shopLogo = data
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async handleUploadBussinessLicense () {
      try {
        const data = await shopService.upload(this.$refs.bussinessFile.files)
        this.registerForm.businessLicense = data
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async handleUploadFoodLicense () {
      try {
        const data = await shopService.upload(this.$refs.foodLicenseFile.files)
        this.registerForm.foodServiceLicense = data
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    registerShop () {
      this.$refs.registerForm.validate(async valid => {
        if (valid) {
          try {
            const data = await shopService.registerShop(this.registerForm)
            this.$message.success('注册成功，您的店铺编号为:' + data)
            this.$router.push('/index')
          } catch (e) {
            this.$message.error(e.message)
          }
        } else {
          this.$message.error('请将注册信息填写完整')
        }
      })
      shopService.registerShop(this.registerForm)
    }
  },
  created () {
    this.getCategoryList()
    shopService.register = this
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
