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
          <li class="product-item" v-for="item in productList" :key="item.id">
            <el-image :src="item.img" style="max-width:188px;max-height:144px" fit="cover"></el-image>
            <p class="product-name">{{item.name}}</p>
            <p style="color:#ff6600">{{item.point}} 积分</p>
            <el-button type="success" size="large"  @click="buyProduct">兑换</el-button>
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
            <li v-for="(item, index) in customerList" :key="item.id" class="others-item">
              <el-row :gutter="20">
                <el-col :span="6">
                  <el-avatar :src="item.avatar" style="width:48px;height:48px">user</el-avatar>
                </el-col>
                <el-col :span="18">
                  <h3>{{item.username}}</h3>
                  <p>兑换了 {{item.product}}</p>
                </el-col>
              </el-row>
              <div :class="{ hide : isLast(index, customerList) }">
                <el-divider/>
              </div>
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
      productType: '全部',
      productList: [],
      customerList: [],
      productMetadata: [
        { name: '黑寡妇第二代RGB机械键盘', img: 'https://img3.sycdn.imooc.com/5ae18a990001c72c04180342-200-200.jpg', point: '3926' },
        { name: '美的（Midea）扫地机器人', img: 'https://img2.sycdn.imooc.com/5ae186d7000128f403490349.jpg', point: '3245' },
        { name: '多功能双肩包', img: 'https://img1.sycdn.imooc.com/5aded5bd00011f3a04560447.jpg', point: '595' },
        { name: '罗技（Logitech）无线鼠标', img: 'https://img4.sycdn.imooc.com/5adecf510001e13304260407.jpg', point: '340' },
        { name: 'Kindle Paperwhite电纸书阅读器', img: 'https://img2.sycdn.imooc.com/5adef1f20001adb604000447.jpg', point: '3790' }
      ],
      personMetadata: [
        { username: '谁叫我这么坏', avatar: 'https://img3.sycdn.imooc.com/5333a0c40001088802000200-100-100.jpg' },
        { username: '我不是懂王', avatar: 'https://img2.sycdn.imooc.com/54584d7300011cfa02200220-100-100.jpg' },
        { username: '铁拳玩家', avatar: 'https://img1.sycdn.imooc.com/54584f540001558402200220-100-100.jpg' },
        { username: '初见', avatar: 'https://img2.sycdn.imooc.com/5a38d2f4000199fc01000100-100-100.jpg' }
      ]
    }
  },
  created () {
    this.generateRandomProduct()
    this.generateRandomCustomerList()
  },
  watch: {
    productType (val) {
      this.productType = val
      this.generateRandomProduct()
    }
  },
  methods: {
    randomInt (bound) {
      return Math.ceil(Math.random() * bound) - 1
    },
    generateRandomProduct () {
      const productMetadata = this.productMetadata
      this.productList = []
      const n = 12
      for (let i = 0; i < n; i++) {
        this.productList.push({ id: i, ...productMetadata[this.randomInt(productMetadata.length)] })
      }
    },
    buyProduct () {
      this.$message.error({ message: '兑换失败, 积分不足' })
    },
    generateRandomCustomerList () {
      const personMetadata = this.personMetadata
      const productMetadata = this.productMetadata
      const n = this.randomInt(4) + 2
      this.customerList = []
      for (let i = 0; i < n; i++) {
        this.customerList.push({
          id: i,
          ...personMetadata[this.randomInt(personMetadata.length)],
          product: productMetadata[this.randomInt(productMetadata.length)].name
        })
      }
    },
    isLast (index, list) {
      if (!list) {
        return false
      }
      return index === list.length - 1
    }
  }
}
</script>

<style lang="less" scoped>
  .hide {
    display: none;
  }
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
      position: relative;
      box-sizing: border-box;
      text-align: center;
      padding: 0 20px;
      padding-bottom: 0;
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
      .product-name {
        overflow: hidden;
      }
      .el-button {
        position: absolute;
        margin-left: -50%;
        height: 48px;
        font-weight: 600;
        bottom: 0;
        width: 100%;
      }
    }
  }
</style>
