<template>
  <el-dialog
    title="发起投诉"
    :visible="$store.state.submitComplaintPaneShow"
    width="50%"
    @close="$store.commit('closeSubmitComplaintPane')"
    >
    <div>
      <el-form ref="form" label-width="80px">
        <el-form-item label="投诉订单">
          <el-select v-model="complaint.orderId">
            <el-option
                  v-for="item in orderList"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
                  <span>{{item.id}}</span>
                  <span style="margin-left:10px;color:#999">{{item.name}}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投诉原因">
          <el-input v-model="complaint.reason" placeholder="简短描述投诉主题"></el-input>
        </el-form-item>
        <el-form-item label="更多描述">
          <el-input v-model="complaint.description" type="textarea" :rows="3" placeholder="请详细描述投诉内容"></el-input>
        </el-form-item>
        <el-form-item label="图片证据">
          <el-upload
            class="upload"
            action="/upload"
            :auto-upload="false"
            :file-list="complaint.fileList"
            list-type="picture">
            <el-button size="small" type="primary">添加照片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="处理状态">
          <el-timeline>
              <el-timeline-item type="primary" timestamp='2020-11-18 12:46' :key="1">顾客发起投诉, 投诉原因: {{complaint.reason}}</el-timeline-item>
            </el-timeline>
        </el-form-item>
        <el-form-item>
          <el-button style="float:right" type="primary" @click="submitComplaint">提交投诉</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      complaint: {
        orderId: null,
        reason: '',
        description: '',
        fileList: []
      },
      orderList: [
        {
          id: '366 899 56012',
          name: '黄焖鸡 等1件商品'
        },
        {
          id: '366 899 56013',
          name: '黄焖鸡 + 黄焖鸭 等2件商品'
        },
        {
          id: '366 899 56014',
          name: '豪大大鸡排 + 汉堡 + 可乐 等3件商品'
        }
      ]
    }
  },
  methods: {
    submitComplaint () {
      this.$message.success('提交投诉成功')
      this.$store.commit('closeSubmitComplaintPane')
    }
  }
}
</script>

<style lang="less" scoped>

</style>
