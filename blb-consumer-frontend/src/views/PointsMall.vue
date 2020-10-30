<template>
  <el-card>
    <div slot="header">
      积分商城
    </div>
    <el-row :gutter="20">
      <el-col :span="16">
        商品类型：
        <el-radio-group v-model="productType">
          <el-radio-button label="全部"></el-radio-button>
          <el-radio-button label="虚拟商品"></el-radio-button>
          <el-radio-button label="实物商品"></el-radio-button>
        </el-radio-group>
        <el-divider/>
        <ul class="product-list">
          <li class="product-item" v-for="item in 12" :key="item + ''">
            <el-image src="https://img3.sycdn.imooc.com/5ae18a990001c72c04180342-200-200.jpg"></el-image>
            <p>黑寡妇第二代RGB机械键盘</p>
            <p style="color:#ff6600">3926积分</p>
            <el-button type="success" size="medium">兑换</el-button>
          </li>
        </ul>
      </el-col>
      <el-col :span="8" class="mall-right">
        <el-row :gutter="20" class="my-info">
          <el-col :span="6">
            <el-avatar src="https://img.mukewang.com/5b76b51000014f3607500562-100-100.jpg" style="width:64px;height:64px">user</el-avatar>
          </el-col>
          <el-col :span="18">
            <h3>CAFEBABE</h3>
            <el-button style="float:right" size="small" type="primary" round>兑换记录</el-button>
            <p>当前积分：128</p>
          </el-col>
        </el-row>
        <el-card style="margin-top:20px" class="others-buys">
          <div slot="header">TA门在买</div>
          <ul>
            <li v-for="item in 4" :key="item + ''" class="others-item">
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-avatar src="https://img3.sycdn.imooc.com/5333a0c40001088802000200-100-100.jpg" style="width:48px;height:48px">user</el-avatar>
                </el-col>
                <el-col :span="18">
                  <h3>谁叫我这么坏</h3>
                  <p>购买了30元全站通用代金券</p>
                </el-col>
              </el-row>
              <el-divider/>
            </li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
export default {
  data () {
    return {
      productType: '全部'
    }
  }
}
</script>

<style lang="less" scoped>
  .my-info {
    h3 {
      padding: 0;
      margin: 0;
      margin-top: 5px;
    }
    p {
      padding-top: 10px;
      margin: 0;
      color: #666;
    }
  }
  .others-buys {
    h3 {
      padding: 0;
      margin: 0;
      font-size: 16px;
    }
    p {
      margin: 0;
      padding-top: 10px;
      color: #666;
      font-size: 14px;
    }
  }
  .product-list {
    display: flex;
    flex-flow: row wrap;
    justify-content: space-around;
    .product-item {
      flex: 0 0 30%;
      box-sizing: border-box;
      text-align: center;
      padding: 0 20px;
      height: 326px;
      width: 100%;
      border: 1px solid #ccc;
      margin-bottom: 20px;
      box-shadow: 0 4px 8px 0 rgba(7,17,27,.1);
      transition: all .3s;
      cursor: pointer;
      .el-image {
        width: 100%;
        height: 180px;
      }
    }
  }
  .product-item:hover {
    box-shadow: 0 8px 8px 0 rgba(7,17,27,.3);
  }
</style>
