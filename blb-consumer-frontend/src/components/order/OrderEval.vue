<template>
  <el-dialog
    title="订单评价"
    :visible="$store.state.evalPaneShow"
    width="35%"
    @close="$store.commit('closeEvalPaneShow')"
    >
    <el-row>
      <el-col :span="4">
        <el-avatar src="/imgs/foods/huangmenji.jpg" fit="cover" shape="square"  :size="48"/>
      </el-col>
      <el-col :span="16">
        <h4 class="shop-name">黄焖鸡米饭 <span class="el-icon-arrow-right"></span></h4>
      </el-col>
      <el-col :span="4">
        <div class="annoymous-wrapper">
          <el-checkbox label="匿名评价"/>
        </div>
      </el-col>
    </el-row>
    <div class="shop-rate-wrapper">
      <div class="shop-main-rate">
        <el-rate
        v-model="mainRate"
        :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
        show-text
        allow-half/>
      </div>
      <div class="shop-package-rate" v-if="showSubRate">
        <span>包装 </span>
        <el-rate
        v-model="packageRate"
        :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
        show-text
        allow-half/>
      </div>
      <div class="shop-package-rate" v-if="showSubRate">
        <span>味道 </span>
        <el-rate
        v-model="tasteRate"
        :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
        show-text
        allow-half/>
      </div>
    </div>
    <div style="margin-top:20px">
      <el-input
        type="textarea"
        v-model="evalContent"
        :autosize="{ minRows: 4, maxRows: 6}"
        placeholder="满意你就夸一夸"/>
      <el-upload
        class="upload"
        action="/upload"
        :auto-upload="false"
        :file-list="fileList"
        list-type="picture">
        <el-button size="small" type="primary">添加照片</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
    <el-divider/>
    <el-row>
      <el-col :span="4">
        <el-avatar src="/imgs/rider.jpg" fit="cover" shape="square"  :size="48"/>
      </el-col>
      <el-col :span="14">
        <h4 class="shop-name">杨超锋 <span class="fengniao-delivery">蜂鸟专送</span></h4>
      </el-col>
      <el-col :span="6">
        <div class="annoymous-wrapper">
          <span class="el-icon-question"> 已对骑手匿名</span>
        </div>
      </el-col>
    </el-row>
    <ul class="rider-eval-list">
      <li class="rider-eval-item">
        <span @click="riderRate = 'bad'" class="el-icon-heavy-rain rider-eval-icon" :class="{ 'active': riderRate === 'bad'}"></span>
        <p>非常差</p>
      </li>
      <li class="rider-eval-item">
        <span @click="riderRate = 'normal'" class="el-icon-sunrise rider-eval-icon" :class="{ 'active': riderRate === 'normal'}"></span>
        <p>一般</p>
      </li>
      <li class="rider-eval-item">
        <span @click="riderRate = 'good'" class="el-icon-sunny rider-eval-icon" :class="{ 'active': riderRate === 'good'}"></span>
        <p>超赞</p>
      </li>
    </ul>
    <el-button type="primary" style="width:100%" @click="submitEval">提交评价</el-button>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      mainRate: 0,
      packageRate: 0,
      tasteRate: 0,
      evalContent: '',
      fileList: [],
      riderRate: ''
    }
  },
  methods: {
    beforeUpload () {
      return false
    },
    submitEval () {
      this.$notify({
        title: '成功',
        message: '提交评价成功',
        type: 'success'
      })
      this.$store.commit('closeEvalPaneShow')
    }
  },
  computed: {
    showSubRate () {
      return this.mainRate !== 0
    }
  }
}
</script>

<style lang="less" scoped>
  .shop-name {
    margin: 0;
    padding: 0;
    vertical-align: middle;
    font-size: 16px;
    height: 48px;
    font-weight: 550;
    line-height: 48px;
    cursor: pointer;
  }
  .annoymous-wrapper {
    height: 48px;
    line-height: 48px;
  }
  .shop-rate-wrapper {
    text-align: center;
  }
  .shop-main-rate /deep/ .el-rate__icon{
    font-size: 32px;
  }
  .shop-package-rate {
    margin-top: 20px;
    /deep/ .el-rate__icon {
      font-size: 26px;
    }
    .el-rate {
      display: inline-block;
    }
  }
  .upload {
    margin-top: 20px;
  }
  .fengniao-delivery {
    background-color: #409EFF;
    color: white;
    font-weight: 400;
    margin-left: 10px;
    font-size: 12px;
    padding: 2px;
    border-radius: 5px;
  }
  .rider-eval-list {
    display: flex;
    justify-content: space-around;
    .rider-eval-item {
      flex: 0 0 33.33%;
      text-align: center;
    }
    .rider-eval-icon {
      font-size: 36px;
      color: #ccc;
      cursor: pointer;
      transition: all 0.2s;
    }
    .rider-eval-icon:hover {
      color: #E6A23C;
    }
    .active {
      color: #E6A23C;
    }
  }
</style>
