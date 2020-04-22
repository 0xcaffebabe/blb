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
        clearable
      ></el-autocomplete>
    </el-card>
    <shop-list style="margin-top:10px" :title="'搜索结果'" :shopList="[1,2,3,4,5,6,7,9]"></shop-list>
  </div>
</template>

<script>
import ShopList from '../components/takeaway/ShopList'
export default {
  data () {
    return {
      input: ''
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
    }
  }
}
</script>

<style lang='less' scoped>
</style>
