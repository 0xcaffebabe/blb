<template>
  <div>
    <div style="margin-bottom:20px">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>订单列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <ul class="order-list">
      <li v-for="order in orderList" :key="order.orderId">
        <order-item :order="order" @show-order-detail="$emit('show-order-detail', order.orderId)"/>
      </li>
    </ul>
  </div>
</template>

<script>
import OrderItem from './OrderItem'
export default {
  props: ['orderList'],
  data () {
    return {}
  },
  methods: {},
  components: {
    OrderItem
  }
}
</script>

<style lang="less" scoped>

</style>
