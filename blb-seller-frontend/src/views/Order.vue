<template>
  <div>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">
        <el-badge :value="12">
          新订单
        </el-badge>
      </el-menu-item>
      <el-menu-item index="2">
        <el-badge>
          新订单提醒
        </el-badge>
      </el-menu-item>
      <el-menu-item index="3">
        <el-badge>
          取消订单
        </el-badge>
      </el-menu-item>
      <el-menu-item index="4">
        <el-badge>
          配送异常
        </el-badge>
      </el-menu-item>
      <el-menu-item index="5">
        <el-badge>
          催单
        </el-badge>
      </el-menu-item>
      <el-menu-item index="6">
        <el-badge>
          售后退款
        </el-badge>
      </el-menu-item>
    </el-menu>
    <el-row>
      <el-col :span="16">
        <el-card style="margin-top:20px">
          <div slot="header">
            <span style="float:right">等待处理</span>
            <p style="margin:0">希望尽快送达,建议11:15之前出餐</p>
          </div>
          <div class="consumer-info">
            <div style="float:right;color:#409EFF">
              <div><span class="el-icon-phone" style="color:#409EFF"></span>17319561234</div>
              <div style="float:right"><span class="el-icon-location" style="color:#409EFF"></span>地图</div>
            </div>
            <h3>王小明</h3> <span>#90天内第3次下单</span>
            <p>漳浦县旧镇镇 漳浦第三中学 高三八班</p>
          </div>
          <el-divider></el-divider>
          <div class="order-note">
            <p><span>备注：</span>不要辣，不要一次性筷子</p>
          </div>
          <el-collapse v-model="expand">
            <el-collapse-item title="商品" name="1">
              <el-table
                :show-header="false"
                :data="tableData"
                style="width: 100%">
                <el-table-column
                  prop="name"
                  width="180">
                    <template slot-scope="scope">
                      <p>{{scope}}</p>
                    </template>
                </el-table-column>
                <el-table-column
                  prop="num"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="price">
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      expand: '1',
      tableData: [{
        name: '黄焖鸡',
        num: 1,
        price: 18.0,
        spec: '大份'
      }, {
        name: '黄焖鸭',
        num: 1,
        price: 13.0,
        spec: '正常规格'
      }, {
        name: '黄焖猪蹄',
        num: 2,
        price: 13.0,
        spec: '正常规格'
      }, {
        name: '菊花茶',
        num: 1,
        price: 6.0,
        spec: '大份'
      }]
    }
  }
}
</script>

<style lang="less" scoped>
  .el-menu {
    height: 42px;
    .el-menu-item {
      height: 42px;
      line-height: 42px;
      color: black;
    }
  }
  .consumer-info {
    h3 {
      margin: 0;
      display: inline;
    }
    span {
      font-size: 14px;
      color: #666;
    }
    p {
      margin-top: 10px;
      color: #666;
      font-size: 14px;
    }
  }
  .order-note {
    span {
      color: #E6A23C;
    }
  }
</style>
