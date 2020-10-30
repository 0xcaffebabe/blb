<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <div slot='header'>
            客服中心
          </div>
          <JwChat-index
          :taleList='list'
          @enter='handleEnter'
          v-model='msg'
          :toolConfig='config'
          :config="config"
          scrollType="scroll"
          >
            <JwChat-rightbox :config="rightConfig"/>
          </JwChat-index>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header">
            投诉列表
            <el-button type="primary" size="mini" style="float:right">发起投诉</el-button>
          </div>
          <ul>
            <li>
              <el-tag effect="dark" type="info" size="medium" style="float:right">未处理</el-tag>
              <h4>关于店铺 黄焖鸡米饭 的投诉</h4>
              <el-divider/>
            </li>
            <li>
              <el-tag effect="dark" type="success" size="medium" style="float:right">处理中</el-tag>
              <h4>关于店铺 阿牛很芒 的投诉</h4>
              <el-divider/>
            </li>
            <li>
              <el-tag effect="dark" type="primary" size="medium" style="float:right">已处理</el-tag>
              <h4>关于店铺 网红奶茶店 的投诉</h4>
              <el-divider/>
            </li>
            <li>
              <el-tag effect="dark" type="danger" size="medium" style="float:right">被驳回</el-tag>
              <h4>关于店铺 黄焖鸡米饭 的投诉</h4>
            </li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      msg: '',
      list: [
        {
          date: new Date().toISOString(),
          text: { text: '您好, 请问有什么可以帮助您' },
          mine: false,
          name: '饱了吧智能客服',
          img: 'https://zbq.ismy.wang/img/helper.jpg'
        },
        {
          date: new Date().toISOString(),
          text: { text: '人工客服繁忙, 您的前面仍有 <span style="color:skyblue">208</span> 位用户' },
          mine: false,
          name: '饱了吧智能客服',
          img: 'https://zbq.ismy.wang/img/helper.jpg'
        },
        {
          date: new Date().toISOString(),
          text: { text: '您可以选择自助服务： <ul><li><a href="#" style="color:skyblue">会员充值没有到账?</a></li><li><a href="#" style="color:skyblue">如何进行投诉?</a></li></ul>或者继续等待' },
          mine: false,
          name: '饱了吧智能客服',
          img: 'https://zbq.ismy.wang/img/helper.jpg'
        }
      ],
      config: {
        img: 'https://zbq.ismy.wang/img/helper.jpg',
        name: '饱了吧智能客服',
        dept: '人工智能客服为您服务',
        callback: this.headerEvent
      },
      rightConfig: {
        listTip: '当前在线客服',
        tip: '系统公告',
        list: [
          {
            name: '盼盼',
            img: 'https://pic1.zhimg.com/v2-89b980f785a7d9dd1068fb9d171ed6cd_is.jpg'
          },
          {
            name: '坤坤',
            img: 'https://pic1.zhimg.com/v2-89b980f785a7d9dd1068fb9d171ed6cd_is.jpg'
          },
          {
            name: '丽丽',
            img: 'https://pic1.zhimg.com/v2-89b980f785a7d9dd1068fb9d171ed6cd_is.jpg'
          }
        ]
      }
    }
  },
  methods: {
    handleEnter () {
      console.log(this.$store)
      this.list.push({
        date: new Date().toISOString(),
        img: this.$store.state.user.info.avatar,
        name: this.$store.state.user.info.username,
        mine: true,
        text: { text: this.msg }
      })
    }
  }
}
</script>

<style lang='less' scoped>

</style>
