<template>
  <div>
    <el-card>
      <div slot="header">
        {{title}}
        <div style="float:right">
          排序：
          <el-select v-model="orderValue" placeholder="请选择">
            <el-option
              v-for="item in orderSelectItems"
              :key="item.text"
              :label="item.text"
              :value="item.text">
              <div class="order-select-item">
                <i :class="item.icon" :style="{'color': item.color}"></i><span style="margin-left:16px">{{item.text}}</span>
              </div>
            </el-option>
          </el-select>
          &nbsp;筛选：
          <el-select v-model="filterValue" placeholder="请选择" multiple :style="{'width': calcFilterSelectWidth() + 'px'}" class="filter-select">
            <el-option-group :label="group.group" v-for="group in filterSelectItems" :key="group.group">
              <el-option
                v-for="item in group.selectList"
                :key="group.group + item.name"
                :label="item.name"
                :value="item.name">
                <span :style="{'color': item.color, 'border': '2px solid ' + item.color}" style="padding:2px;border-radius:5px">{{getFirstLetter(item.name)}}</span>
                <span style="font-size: 16px;margin-left:15px">{{ item.name }}</span>
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
      filterValue: [],
      orderSelectItems: [
        { icon: 'el-icon-sort', color: '#3b87c8', text: '智能排序' },
        { icon: 'el-icon-location-outline', color: '#2a9bd3', text: '距离最近' },
        { icon: 'el-icon-s-order', color: '#f07373', text: '销量最高' },
        { icon: 'el-icon-money', color: '#e6b61a', text: '起送价最低' },
        { icon: 'el-icon-time', color: '#37c7b7', text: '配送速度最快' },
        { icon: 'el-icon-star-off', color: '#eba53b', text: '评分最高' }
      ],
      filterSelectItems: [
        {
          group: '配送方式',
          selectList: [
            { name: '蜂鸟专送', color: '#0089cf' },
            { name: '商家自送', color: 'rgb(232, 132, 45)' }
          ]
        },
        {
          group: '商家属性',
          selectList: [
            { name: '品牌商家', color: 'rgb(63, 189, 230)' },
            { name: '外卖保', color: 'rgb(153, 153, 153)' },
            { name: '准时达', color: 'rgb(87, 169, 255)' },
            { name: '新店', color: 'rgb(232, 132, 45)' },
            { name: '在线支付', color: 'rgb(255, 78, 0)' },
            { name: '开发票', color: 'rgb(153, 153, 153)' }
          ]
        }
      ]
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
    },
    getFirstLetter (val) {
      if (!val) {
        return ''
      }
      return val.charAt(0)
    },
    calcFilterSelectWidth () {
      const width = 90 * (this.filterValue.length + 1) + 40
      return width > 200 ? width : 200
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
  .order-select-item {
    font-size: 16px;
  }
  .filter-select {
    transition: width 0.3s;
  }
</style>
