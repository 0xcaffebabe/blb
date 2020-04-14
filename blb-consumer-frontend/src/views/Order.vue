<template>
  <div>
    <order-list></order-list>
    <order-detail :show="orderDetailShow" @close="handleDetailClose"></order-detail>
  </div>
</template>

<script>
import OrderList from '../components/order/OrderList'
import OrderDetail from '../components/order/OrderDetail'
export default {
  data () {
    return {
      orderDetailShow: true
    }
  },
  components: {
    OrderList, OrderDetail
  },
  methods: {
    handleDetailClose () {
      this.orderDetailShow = false
    }
  }
}
</script>

<style lang="less" scoped>

</style>
