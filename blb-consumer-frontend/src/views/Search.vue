<template>
  <div>
    <el-breadcrumb separator="/" style="margin-bottom:20px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>搜索</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="text-align:center" class="search-box-container">
      <el-autocomplete
        style="width:600px"
        class='search-input'
        v-model='input'
        :fetch-suggestions='querySearch'
        placeholder='请输入内容'
        suffix-icon="el-icon-search"
        @select='handleSelect'
        @keyup.enter="search"
      >
        <template v-slot="{ item }">
          <div class="shop-name">{{ item.value }}</div>
          <div class="shop-addr"> <span class="el-icon-location"></span> {{ item.address }}</div>
        </template>
      </el-autocomplete>
      <!-- <el-button type="primary" style="margin-left:20px" @click="search">搜索</el-button> -->
    </div>
    <shop-list v-if="shopList.length != 0" style="margin-top:10px" :title="'搜索结果'" :shopList="shopList"></shop-list>
    <el-card v-if="shopList.length != 0" style="margin-top:10px;text-align:center">
      <el-pagination
      @size-change="handleSizeChange"
      background
      @current-change="handlePageChange"
      :current-page="page"
      :page-sizes="[5, 12, 20, 30]"
      :page-size="size"
      layout="prev, pager, next"
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
        {
          value: '黄焖鸡米饭',
          address: '鲤城区媒人桥路2号店'
        },
        {
          value: '沙县小吃',
          address: '丰泽区泰禾广场3号店'
        },
        {
          value: '饿点外卖',
          address: '客运中心站4号店'
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
    async handleSizeChange (val) {
      this.size = val
      await this.search()
      this.scrollToTop()
    },
    async handlePageChange (val) {
      this.page = val
      await this.search()
      this.scrollToTop()
    },
    scrollToTop () {
      window.scrollTo({ top: 0 })
    }
  }
}
</script>

<style lang='less' scoped>
  .el-autocomplete /deep/ .el-input__inner {
    padding: 15px;
    font-size: 16px;
    height: 60px;
  }
  .el-autocomplete /deep/ .el-icon-search {
    font-size: 16px;
    color: #333;
    margin-right: 10px;
  }
  .shop-name {
    font-size: 16px;
  }
  .shop-addr {
    color: #999;
  }
</style>
