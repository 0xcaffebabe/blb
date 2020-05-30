<template>
  <div>
    <el-card>
      <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="营业设置" name="first">
        <el-form :model="productForm" :rules="productFormRules" ref="productForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="店铺logo" prop="name">
          <input type="file" ref="shopLogoFile"> <el-button type="primary" size="mini" @click="uploadShopLogo">上传店铺logo</el-button>
          <div>
            <img :src="shopInfo.shopLogo" v-if="shopInfo.shopLogo" alt="" style="max-wdith:100px;max-height:100px">
          </div>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="店铺名称">
          <span v-if="!showShopNameEdit" @click="showShopNameEdit = true">{{shopInfo.shopName}}</span>
          <el-input style="width:200px" size="medium" v-model="shopInfo.shopName" v-if="showShopNameEdit" v-focus="showShopNameEdit" @blur="showShopNameEdit=false"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="店铺简介">
          <span class="long-text" v-if="!showShopDescEdit" @click="showShopDescEdit = true">{{shopInfo.shopDesc}}</span>
          <el-input type="textarea" size="medium" v-model="shopInfo.shopDesc" v-if="showShopDescEdit" v-focus="showShopDescEdit" @blur="showShopDescEdit=false"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="店铺slogan">
          <span class="long-text" v-if="!showShopSloganEdit" @click="showShopSloganEdit = true">{{shopInfo.shopSlogan}}</span>
          <el-input type="textarea" size="medium" v-model="shopInfo.shopSlogan" v-if="showShopSloganEdit" v-focus="showShopSloganEdit" @blur="showShopSloganEdit=false"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="营业时间">
            <el-time-picker
              is-range
              v-model="time"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="配送管理">
          <div>
            起送价<el-input-number size="small" style="width:120px" v-model="shopInfo.startingPrice"></el-input-number>
          </div>
          <div>配送费<el-input-number size="small" style="width:120px" v-model="shopInfo.deliveryFee"></el-input-number></div>
        </el-form-item>
        <el-button type="primary" class="fr" @click="updateShopInfo">保存</el-button>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="资质管理" name="second">资质管理</el-tab-pane>
      <el-tab-pane label="配送管理" name="third">配送管理</el-tab-pane>
    </el-tabs>
    </el-card>
  </div>
</template>

<script>
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      activeName: 'first',
      showShopNameEdit: false,
      showShopDescEdit: false,
      showShopSloganEdit: false,
      time: new Date(2020, 4, 20, 0, 0, 0),
      shopInfo: {}
    }
  },
  methods: {
    async getShopInfo () {
      try {
        this.shopInfo = await shopService.getShopInfo()
      } catch (e) {
        this.$messaege.error(e.message)
      }
    },
    async uploadShopLogo () {
      try {
        const data = await shopService.upload(this.$refs.shopLogoFile.files)
        this.shopInfo.shopLogo = data
        this.$message.success('上传店铺LOGO成功')
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async updateShopInfo () {
      try {
        if (await shopService.updateShopInfo(this.shopInfo)) {
          this.$message.success('更新店铺信息成功')
          this.getShopInfo()
        }
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getShopInfo()
  },
  directives: {
    focus: {
      inserted (el, { value }) {
        if (value) {
          const input = el.querySelector('input')
          if (input) {
            input.focus()
          } else {
            el.querySelector('textarea').focus()
          }
        }
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .long-text {
    display: block;
    height: 16px;
    line-height: 26px;
  }
</style>
