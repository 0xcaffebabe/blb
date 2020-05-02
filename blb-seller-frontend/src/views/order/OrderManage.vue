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
    :data="tableData"
    stripe
    style="width: 100%">
    <el-table-column type="expand">
      <template>
        <order-item></order-item>
      </template>
    </el-table-column>
    <el-table-column
      label="订单 ID"
      prop="id">
    </el-table-column>
    <el-table-column
      label="订单名称"
      prop="name">
    </el-table-column>
    <el-table-column
      label="总金额"
      prop="amount">
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
export default {
  data () {
    return {
      tableData: [
        { id: 1 },
        { id: 2 },
        { id: 3 }
      ]
    }
  },
  components: {
    OrderItem
  }
}
</script>

<style lang="less" scoped>
  .amount {
    display: inline-block;
    color: rgb(255,95,0);
  }
</style>
