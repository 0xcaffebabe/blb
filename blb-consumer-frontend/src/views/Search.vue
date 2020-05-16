<template>
  <div>
    <el-card style="text-align:center">
      <el-autocomplete
        style="width:800px"
        class='inline-input'
        v-model='input'
        :fetch-suggestions='querySearch'
        placeholder='请输入内容'
        @select='handleSelect'
        @keyup.enter="search"
        clearable
      ></el-autocomplete>
      <el-button type="primary" style="margin-left:20px" @click="search">搜索</el-button>
    </el-card>
    <shop-list style="margin-top:10px" :title="'搜索结果'" :shopList="shopList"></shop-list>
  </div>
</template>

<script>
import ShopList from '../components/takeaway/ShopList'
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      input: '',
      shopList: []
    }
  },
  components: {
    ShopList
  },
  methods: {
    querySearch (queryString, cb) {
      cb(this.loadAll())
    },
    loadAll () {
      return [
        { value: '黄焖鸡米饭' },
        {
          value: '沙县小吃'
        },
        {
          value: '饿点外卖'
        }
      ]
    },
    handleSelect (item) {
      this.$message.success(item.value)
    },
    async search () {
      try {
        const data = await shopService.searchShop({
          kw: this.input
        })
        this.shopList = data.data
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  }
}
</script>

<style lang='less' scoped>
</style>
