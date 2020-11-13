<template>
  <div>
    <el-breadcrumb separator="/" style="margin-bottom:20px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>搜索</el-breadcrumb-item>
    </el-breadcrumb>
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
    <el-card v-if="shopList.length != 0" style="margin-top:10px">
      <el-pagination
      @size-change="handleSizeChange"
      background
      @current-change="handlePageChange"
      :current-page="page"
      :page-sizes="[5, 12, 20, 30]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    </el-card>
  </div>
</template>

<script>
import ShopList from '../components/takeaway/ShopList'
import shopService from '../service/ShopService'
export default {
  data () {
    return {
      input: '',
      shopList: [],
      page: 1,
      size: 12,
      total: 0
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
      this.search()
    },
    async search () {
      try {
        const data = await shopService.searchShop({
          kw: this.input,
          page: this.page,
          size: this.size
        })
        this.shopList = data.data
        this.total = data.total
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handleSizeChange (val) {
      this.size = val
      this.search()
    },
    handlePageChange (val) {
      this.page = val
      this.search()
    }
  }
}
</script>

<style lang='less' scoped>
</style>
