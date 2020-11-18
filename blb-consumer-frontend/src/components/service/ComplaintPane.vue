<template>
  <el-dialog
    title="投诉详情"
    :visible="$store.state.complaintPaneShow"
    width="50%"
    @close="$store.commit('toggleComplaintPane')"
    >
    <div>
      <el-form ref="form" label-width="80px">
        <el-form-item label="投诉订单">
          <el-tooltip placement="bottom-end" effect="light">
            <template #content>
                <order-item :order="order" :editable="false" style="height:288px;width:1000px"/>
            </template>
            <el-link type="primary"> 399 288 600 456</el-link>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="投诉原因">
          <el-input v-model="complaint.reason" disabled></el-input>
        </el-form-item>
        <el-form-item label="更多描述">
          <el-input v-model="complaint.description" disabled type="textarea" :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="图片证据">
          <ul>
            <li v-for="img in imgList" :key="img + Math.random()" class="order-img-item">
              <el-image fit="cover" :src="img" :preview-src-list="[img]"/>
            </li>
          </ul>
        </el-form-item>
        <el-form-item label="处理状态">
          <el-timeline>
              <el-timeline-item type="danger" timestamp='2020-11-18 20:00' :key="1">平台裁定, 驳回顾客投诉</el-timeline-item>
              <el-timeline-item type="warning" timestamp='2020-11-18 14:38' :key="2">双方无法协商</el-timeline-item>
              <el-timeline-item type="primary" timestamp='2020-11-18 14:06' :key="3">商家反驳, 商家提交证据</el-timeline-item>
              <el-timeline-item type="primary" timestamp='2020-11-18 12:46' :key="4">顾客发起投诉, 投诉原因: {{complaint.reason}}</el-timeline-item>
            </el-timeline>
        </el-form-item>
        <el-form-item>
          <el-button style="float:right" type="info" disabled>此裁定为最终裁定, 不得上诉</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import OrderItem from '@/components/order/OrderItem'
import orderService from '@/service/OrderService'
export default {
  data () {
    return {
      order: {},
      complaint: {
        reason: '以次充好',
        description: '在 Form 组件中，每一个表单域由一个 Form-Item 组件构成，表单域中可以放置各种类型的表单控件，\n包括 Input、Select、Checkbox、Radio、Switch、DatePicker、TimePicker'
      },
      imgList: [
        '/imgs/foods/huangmenji.jpg',
        '/imgs/foods/huangmenzhujiao.jpg',
        '/imgs/foods/huangmenya.jpg',
        '/imgs/foods/jipai.jpg',
        '/imgs/foods/huangmenfuzhu.jpg'
      ]
    }
  },
  components: {
    OrderItem
  },
  async created () {
    const data = await orderService.getOrderList(1, 1)
    this.order = data.data[0]
  }
}
</script>

<style lang="less" scoped>
  .order-img-item {
    display: inline-block;
    padding: 0 5px;
    .el-image {
      height: 100px;
      width: 144px;
      border-radius: 5px;
    }
  }
</style>
