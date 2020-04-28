<template>
  <div>
    <el-card>
      <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="营业设置" name="first">
        <el-form :model="productForm" :rules="productFormRules" ref="productForm" label-width="100px" class="demo-ruleForm">
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
        <el-divider></el-divider>
        <el-form-item label="店铺名称">
          <span v-if="!showShopNameEdit" @click="showShopNameEdit = true">黄焖鸡米饭</span>
          <el-input style="width:200px" size="medium" value="黄焖鸡米饭" v-if="showShopNameEdit" v-focus="showShopNameEdit" @blur="showShopNameEdit=false"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="店铺简介">
          <span class="long-text" v-if="!showShopDescEdit" @click="showShopDescEdit = true">黄焖鸡 黄焖鸡米饭又叫香鸡煲、浓汁鸡煲饭，属于鲁菜系家常菜品，起源于山东省济南市。 主要食材是鸡腿肉，配以青椒、土豆、香菇等焖制而成，味道美妙，具有肉质鲜美嫩滑的特点</span>
          <el-input type="textarea" size="medium" value="黄焖鸡 黄焖鸡米饭又叫香鸡煲、浓汁鸡煲饭，属于鲁菜系家常菜品，起源于山东省济南市。 主要食材是鸡腿肉，配以青椒、土豆、香菇等焖制而成，味道美妙，具有肉质鲜美嫩滑的特点" v-if="showShopDescEdit" v-focus="showShopDescEdit" @blur="showShopDescEdit=false"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="店铺简介">
          <span class="long-text" v-if="!showShopSloganEdit" @click="showShopSloganEdit = true">欢迎来到黄焖鸡米饭，在此感谢小主们对本店一直以来的支持和信任，为了回馈小主们，我们坚持追求用心，责任，高品质，高效率，让小主们看到我们的努力，打心底觉得吃的安全，放心！温馨提醒:1.高峰期提前下单，保障用餐时间。2.下雨天稍有延误请谅解。3.疑问和建议请与本店联系，谢谢</span>
          <el-input type="textarea" size="medium" value="欢迎来到黄焖鸡米饭，在此感谢小主们对本店一直以来的支持和信任，为了回馈小主们，我们坚持追求用心，责任，高品质，高效率，让小主们看到我们的努力，打心底觉得吃的安全，放心！温馨提醒:1.高峰期提前下单，保障用餐时间。2.下雨天稍有延误请谅解。3.疑问和建议请与本店联系，谢谢" v-if="showShopSloganEdit" v-focus="showShopSloganEdit" @blur="showShopSloganEdit=false"></el-input>
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
            起送价<el-input-number size="small" style="width:120px" value="20"></el-input-number>
          </div>
          <div>配送费<el-input-number size="small" style="width:120px" value="20"></el-input-number></div>
        </el-form-item>
        <el-button type="primary" class="fr">保存</el-button>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="资质管理" name="second">资质管理</el-tab-pane>
      <el-tab-pane label="配送管理" name="third">配送管理</el-tab-pane>
    </el-tabs>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'first',
      showShopNameEdit: false,
      showShopDescEdit: false,
      showShopSloganEdit: false,
      time: new Date(2020, 4, 20, 0, 0, 0)
    }
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
