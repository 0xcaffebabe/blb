<template>
  <div class="shop-chat">
    <el-dialog
    :visible="$store.state.shopChatShow"
    width="570px"
    @close="closeDialog"
    >
    <JwChat-index
          :taleList='list'
          @enter='handleEnter'
          v-model='msg'
          :toolConfig='config'
          :config="config"
          scrollType="scroll"
          >
          <div slot="header" style="margin-left:460px">
            <el-button type="primary" size="small" icon="el-icon-close" @click="closeDialog"></el-button>
          </div>
            <JwChat-rightbox :config="rightConfig" @click="rightClick" />
    </JwChat-index>
  </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      list: [
        {
          date: new Date().toISOString(),
          text: { text: '您好, 欢迎光临黄焖鸡米饭' },
          mine: false,
          name: '黄焖鸡米饭',
          img: 'https://zbq.ismy.wang/img/anonymous.jpg'
        }
      ],
      config: {
        img: 'https://zbq.ismy.wang/img/anonymous.jpg',
        name: '黄焖鸡米饭',
        dept: '为生活增添点快乐',
        callback: this.headerEvent
      },
      rightConfig: {
        listTip: '当前在线客服',
        tip: '店铺公告',
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
    closeDialog () {
      if (this.$store.state.shopChatShow) {
        this.$store.commit('toggleShopChatShow')
      }
    }
  }
}
</script>

<style lang="less">
  .shop-chat .el-dialog {
    margin: 0 auto;
  }
  .shop-chat .el-dialog__header, .shop-chat  .el-dialog__title {
    display: none;
  }
  .shop-chat .el-dialog__body {
    padding: 0;
  }
</style>
