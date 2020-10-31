<template>
  <el-dialog
    title="优惠券"
    :visible="$store.state.couponPaneShow"
    width="50%"
    @close="$store.commit('toggleCouponPaneShow')"
    >
  <el-tabs v-model="activeName">
    <el-tab-pane label="红包" name="redEnv">
      有 <span style="color:#ff5340">{{redEnv.expireSonn}}</span> 个红包即将过期
      <ul class="red-env-list">
        <li v-for="item in redEnv.envList" :key="item.id">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="red-env-left">
                <h2>￥{{item.amount}}</h2>
                <p>满{{item.leastAmount}}元可用</p>
              </div>
            </el-col>
            <el-col :span="14">
              <div class="red-env-right">
                <p style="font-size:16px;color:#666">{{item.name}}</p>
                <p>{{item.expireDate}} 过期</p>
                <p>{{item.desc}}</p>
              </div>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" size="medium" style="margin-top:50%">去使用</el-button>
            </el-col>
          </el-row>
          <el-divider></el-divider>
        </li>
      </ul>
    </el-tab-pane>
    <el-tab-pane label="商家代金券" name="sellerToken">
      有 <span style="color:#ff5340">{{voucher.expireSonn}}</span> 个红包即将过期
      <ul class="red-env-list">
        <li v-for="item in voucher.list" :key="item.id">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="red-env-left">
                <h2>￥{{item.amount}}</h2>
                <p>满{{item.leastAmount}}元可用</p>
              </div>
            </el-col>
            <el-col :span="14">
              <div class="red-env-right">
                <p style="font-size:16px;color:#666">{{item.name}}</p>
                <p>{{item.expireDate}} 过期</p>
                <p>{{item.desc}}</p>
              </div>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" size="medium" style="margin-top:50%">去使用</el-button>
            </el-col>
          </el-row>
          <el-divider></el-divider>
        </li>
      </ul>
    </el-tab-pane>
  </el-tabs>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'redEnv',
      redEnv: {
        expireSonn: 3,
        envList: [
          {
            id: 1,
            amount: 5.0,
            name: '吃货联盟超级红包',
            leastAmount: 20,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234'
          },
          {
            id: 2,
            amount: 5.0,
            name: '商超联盟红包',
            leastAmount: 10,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234及指定商家'
          },
          {
            id: 3,
            amount: 20.0,
            name: '吃货联盟超级红包',
            leastAmount: 30,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234'
          },
          {
            id: 4,
            amount: 15.0,
            name: '吃货联盟超级红包',
            leastAmount: 20,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234'
          }
        ]
      },
      voucher: {
        expireSonn: 3,
        list: [
          {
            id: 1,
            amount: 2.0,
            name: '黄焖鸡米饭代金券',
            leastAmount: 20,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234及指定商家'
          },
          {
            id: 2,
            amount: 15.0,
            name: '阿牛很芒代金券',
            leastAmount: 20,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234及指定商家'
          },
          {
            id: 3,
            amount: 29.9,
            name: '网红奶茶店代金券',
            leastAmount: 30,
            expireDate: new Date().toLocaleDateString(),
            desc: '限收货手机号: 17359561234'
          }
        ]
      }
    }
  }
}
</script>

<style lang="less" slot-scope>
  .red-env-list {
    .red-env-left {
      width: 100%;
      border-right: 1px solid #ccc;
      text-align: center;
    }
    h2 {
      color: #ff5340;
      font-size: 30px;
    }
    p {
      color: #999;
    }
  }
</style>
