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
                  <el-input v-model="registerForm.name"></el-input>
                  <el-amap class="amap-box" :vid="'amap-vue'" :zoom="10" :center="[117.5,24]">
                    <el-amap-marker vid="component-marker" :position="[117.5,24]"></el-amap-marker>
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
                    :options="options"
                    :props="{ expandTrigger: 'hover' }"
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
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="店铺slogan" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="营业时间" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="店铺联系电话" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="配送费" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                 <el-form-item label="起送价" prop="name">
                  <el-input v-model="registerForm.name"></el-input>
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
export default {
  data () {
    return {
      registerForm: {},
      registerFormRules: [],
      options: [
        {
          value: '中国',
          label: '中国',
          children: [
            {
              value: '福建',
              label: '福建',
              children: [
                {
                  value: '漳州', label: '漳州'
                }
              ]
            }
          ]
        }
      ],
      fixedSteps: false
    }
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
