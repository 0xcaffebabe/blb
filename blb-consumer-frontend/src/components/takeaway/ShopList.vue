<template>
  <div>
    <el-card>
      <div slot="header">
        {{title}}
        <div style="float:right">
          排序：
          <el-select v-model="orderValue" placeholder="请选择">
            <el-option
              v-for="item in 4"
              :key="item"
              :label="item"
              :value="item">
              <span style="float: left">{{ item }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item }}</span>
            </el-option>
          </el-select>
          &nbsp;筛选：
          <el-select v-model="whereValue" placeholder="请选择">
            <el-option-group label="配送方式">
              <el-option
                v-for="item in 4"
                :key="item"
                :label="item"
                :value="item">
                <span style="float: left">{{ item }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item }}</span>
              </el-option>
            </el-option-group>
            <el-option-group label="商家属性">
              <el-option
                v-for="item in 4"
                :key="item"
                :label="item"
                :value="item">
                <span style="float: left">{{ item }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item }}</span>
              </el-option>
            </el-option-group>
          </el-select>
        </div>
      </div>
      <el-alert v-if="!shopList" title="没有数据"></el-alert>
      <ul class="shop-list">
        <li class="shop-item-wrapper" v-for="item in shopList" :key="item.shopId">
          <div class="shop-item" @click="handleShopItemClick(item)">
            <el-image fit="cover" :src="item.shopLogo" class="shop-logo"/>
            <h1>{{item.shopName}}</h1>
            <el-rate style="text-align:center"
              :value="item.ranking"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}">
            </el-rate>
            <span class="shop-sales">月销量: <strong>{{item.sales}}</strong></span>
            <div>
              <span class="shop-price">起送: <strong style="color:#ff6600">{{item.startingPrice}}</strong> / 配送费: <strong style="color:#ff6600">{{item.deliveryFee}}</strong></span>
            </div>
            <p class="distance el-icon-time"> {{item.distance}} km / <span>{{item.deliveryTime}}送达</span></p>
            <div class="bottom-btns">
                <el-button circle icon="el-icon-camera-solid"></el-button>
                <el-button circle icon="el-icon-star-off" @click="starShop(item, $event)"></el-button>
                <el-button circle icon="el-icon-chat-line-square"></el-button>
            </div>
          </div>
        </li>
      </ul>
    </el-card>
  </div>
</template>

<script>
export default {
  props: ['title', 'shopList'],
  data () {
    return {
      orderValue: '',
      whereValue: ''
    }
  },
  methods: {
    handleShopItemClick (shop) {
      this.$router.push('/shop/' + shop.shopId)
    },
    starShop (item, e) {
      this.$notify({
        title: '收藏店铺成功',
        message: `收藏店铺 ${item.shopName} 成功`,
        type: 'success'
      })
      e.stopPropagation()
    }
  }
}
</script>

<style lang="less" scoped]>
    .shop-list {
    display: flex;
    flex-flow: row wrap;
    justify-content: space-around;
    .shop-item-wrapper {
      flex: 0 0 25%;
    }
    .shop-item {
      padding: 20px 20px 0 20px;
      width: 100%;
      transition: all 0.2s;
      box-sizing: border-box;
      .shop-logo {
        position: relative;
        height:144px;
        width:144px;
        border-radius:128px;
        left: 50%;
        margin-left: -64px;
        img {
          transition: all 0.8s;
        }
      }
    }
    .shop-item:hover {
      box-shadow: 2px 2px 13px #eee;
      border-bottom: none;
      cursor: pointer;
      .shop-logo{
        box-shadow: 2px 2px 13px #bbb;
        img {
          width: 110%;
          height: 110%;
        }
      }
    }
    li img {
      max-width: 208px;
      max-height: 156px;
    }
    h1 {
      margin: 10px 0;
      font-weight: 400;
      text-align: center;
    }
    .shop-price {
      font-size: 12px;
      color: #666;
    }
    .shop-sales {
      font-size: 12px;
    }
    .distance {
      font-size: 12px;
      span {
        color: #3190e8;
        font-weight: 550;
      }
    }
  }
  .bottom-btns {
    text-align:center;
    padding: 10px 0 20px 0;
    flex: 0 0 33.333%;
  }
</style>
