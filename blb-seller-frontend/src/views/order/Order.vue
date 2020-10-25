<template>
  <div>
    <el-menu :default-active="'/order/new'" mode="horizontal" router>
      <el-menu-item index="/order/new">
        <el-badge :value="orderList.length">
          新订单
        </el-badge>
      </el-menu-item>
      <el-menu-item index="/order/manage">
        <el-badge>
          订单管理
        </el-badge>
      </el-menu-item>
    </el-menu>
    <router-view :orderList="orderList" @refreshOrder="getNewOrder"/>
  </div>
</template>

<script>
import orderService from '../../service/OrderService'
export default {
  data () {
    return {
      orderList: []
    }
  },
  methods: {
    async getNewOrder () {
      try {
        this.orderList = await orderService.getNewOrderList()
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getNewOrder()
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
</style>
