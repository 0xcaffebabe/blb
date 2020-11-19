<template>
  <div>
    <order-list :orderList="orderList" @show-order-detail="handleShowOrderDetail"/>
    <el-card style="text-align:center">
       <el-pagination
      @size-change="handleSizeChange"
      background
      @current-change="handlePageChange"
      :current-page="page"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="size"
      layout="prev, pager, next"
      :total="total">
    </el-pagination>
    </el-card>
    <order-detail :show="orderDetailShow" @close="handleDetailClose" :order="orderDetail"></order-detail>
  </div>
</template>

<script>
import OrderList from '../components/order/OrderList'
import OrderDetail from '../components/order/OrderDetail'
import orderService from '../service/OrderService'
export default {
  data () {
    return {
      orderDetailShow: true,
      page: 1,
      size: 10,
      total: 0,
      orderList: [],
      orderDetail: {}
    }
  },
  components: {
    OrderList, OrderDetail
  },
  methods: {
    handleDetailClose () {
      this.orderDetailShow = false
    },
    async getOrderList () {
      try {
        const data = await orderService.getOrderList(this.page, this.size)
        this.orderList = data.data
        this.total = data.total
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async handleSizeChange (val) {
      this.size = val
      await this.getOrderList()
      this.scrollToTop()
    },
    scrollToTop () {
      window.scrollTo({ top: 0 })
    },
    async handlePageChange (val) {
      this.page = val
      await this.getOrderList()
      this.scrollToTop()
    },
    handleShowOrderDetail (orderId) {
      this.getOrderDetail(orderId)
      this.$store.commit('toggleOrderDetail')
    },
    async getOrderDetail (orderId) {
      try {
        this.orderDetail = await orderService.getOrderDetail(orderId)
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getOrderList()
  }
}
</script>

<style lang="less" scoped>

</style>
