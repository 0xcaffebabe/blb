<template>
  <div>
    <el-card style="margin-top:10px">
      <span>总收入：<h1 class="amount">2689.48</h1></span>
    </el-card>
    <el-card style="margin-top:10px">
      <div slot="header">
        订单流水
      </div>
      <el-row>
        <el-col :span="6">
          <el-radio-group v-model="radio3" size="small">
            <el-radio-button label="今天"></el-radio-button>
            <el-radio-button label="近7天"></el-radio-button>
            <el-radio-button label="近30天"></el-radio-button>
          </el-radio-group>
        </el-col>
        <el-col :span="8">
          <el-date-picker
      v-model="value1"
      size="small"
      type="daterange"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期">
    </el-date-picker>
        </el-col>
        <el-col :span="6">
          <el-input size="small"></el-input>
        </el-col>
      </el-row>
      <el-table
    :data="orderList"
    stripe
    style="width: 100%"
    @expand-change="handleExpandChange"
    >
    <el-table-column type="expand">
      <template slot-scope="scope">
        <order-item :order="orderDetails[scope.row.orderId]"></order-item>
      </template>
    </el-table-column>
    <el-table-column
      label="订单 ID"
      prop="orderId">
    </el-table-column>
    <el-table-column
      label="总金额"
      prop="orderAmount">
    </el-table-column>
    <el-table-column
      label="订单状态"
      prop="orderStatus">
    </el-table-column>
    <el-table-column
      label="支付状态"
      prop="payStatus">
    </el-table-column>
  </el-table>
    </el-card>
  </div>
</template>

<script>
import OrderItem from '../../components/order/OrderItem'
import OrderService from '../../service/OrderService'
export default {
  data () {
    return {
      tableData: [
        { id: 1 },
        { id: 2 },
        { id: 3 }
      ],
      orderList: [],
      orderDetails: {},
      page: 1,
      size: 5,
      total: 0
    }
  },
  components: {
    OrderItem
  },
  methods: {
    async getOrderList () {
      try {
        const data = await OrderService.getOrderList({ page: this.page, size: this.size })
        this.total = data.total
        this.orderList = data.data
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async getOrderDetail (orderId) {
      try {
        this.orderDetails[orderId] = await OrderService.getOrderDetail(orderId)
        console.log(this.orderDetails)
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handleExpandChange (row) {
      this.getOrderDetail(row.orderId)
    }
  },
  created () {
    this.getOrderList()
  }
}
</script>

<style lang="less" scoped>
  .amount {
    display: inline-block;
    color: rgb(255,95,0);
  }
</style>
