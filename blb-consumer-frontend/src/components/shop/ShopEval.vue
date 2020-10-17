<template>
  <el-row :gutter='20'>
    <el-col :span='18'>
      <el-card>
        <el-tag class="eval-tag" type='primary' :effect='currentEval == -1 ?"dark":""' @click="handleTagClick(-1)">全部评价({{allEvalSum}})</el-tag>
        <el-tag class="eval-tag"
        v-for='item in shopEval.wordCloud'
        :key='item.id'
        :type="!item.positive?'info':'primary'"
        :effect="currentEval === item.id?'dark':''"
        @click="handleTagClick(item.id)"
        >{{item.content}}({{item.count}})</el-tag>
        <el-divider></el-divider>
        <ul class="eval-list">
          <li v-for="item in evalList" :key="item.evalId" class="eval-list-item">
            <el-row>
              <el-col :span="2">
                <el-avatar>{{item.nickName}}</el-avatar>
              </el-col>
              <el-col :span="22">
                <span class="eval-date">{{item.createTime}}</span>
                <h3>{{item.phone}}</h3>
                <el-rate :value="item.ranking" disabled></el-rate>
                <p>{{item.content}}</p>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </li>
        </ul>
        <el-pagination
          @size-change="handleSizeChanged"
          background
          @current-change="handlePageChanged"
          :current-page="evalPage"
          :page-sizes="[2, 10, 20, 30]"
          :page-size="evalPageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="evalTotal">
        </el-pagination>
      </el-card>
    </el-col>
    <el-col :span='6'>
      <el-card class='eval-info'>
        <h2>{{shopEval.rating}}</h2>
        <p class='eval-avg'>综合评价</p>
        <p class='rating'>高于周围{{shopEval.wingRate}}%的商家</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import shopService from '../../service/ShopService'
export default {
  props: ['shopId'],
  data () {
    return {
      shopEval: {},
      evalList: [],
      currentEval: -1,
      evalPage: 1,
      evalPageSize: 10,
      evalTotal: 0
    }
  },
  computed: {
    allEvalSum () {
      if (!this.shopEval.wordCloud) {
        return 0
      }
      let sum = 0
      for (let i = 0; i < this.shopEval.wordCloud.length; i++) {
        sum += this.shopEval.wordCloud[i].count
      }
      return sum
    }
  },
  methods: {
    async getShopEval () {
      try {
        const shopEval = await shopService.getShopEval(this.shopId)
        this.shopEval = shopEval
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    async getShopEvalList () {
      try {
        const data = await shopService.getShopEvalList({
          shopId: this.shopId,
          tagId: this.currentEval,
          page: this.evalPage,
          size: this.evalPageSize
        })
        this.evalList = data.data
        this.evalTotal = data.total
        console.log(this.evalList)
      } catch (e) {
        this.$message.error(e.message)
      }
    },
    handlePageChanged (val) {
      this.evalPage = val
      this.getShopEvalList()
    }
    ,
    handleSizeChanged (val) {
      this.evalPageSize = val
      this.getShopEvalList()
    },
    handleTagClick (tagId) {
      this.currentEval = tagId
      this.getShopEvalList()
    }
  },
  created () {
    this.getShopEval()
    this.getShopEvalList()
  }
}
</script>

<style lang='less' scoped>
.eval-info {
  h2 {
    margin: 0;
    text-align: center;
    color: #f60;
  }
  .eval-avg {
    margin: 0;
    text-align: center;
    font-size: 16px;
  }
  .rating {
    font-size: 14px;
    color: #666;
    text-align: center;
  }
}
.eval-tag {
  margin-right: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  transition: all 0.2s;
}
.eval-list-item {
  h3 {
    margin: 0;
    font-size: 14px;
  }
  .el-rate {
    margin: 5px 0;
  }
  p {
    margin: 0;
    font-size: 14px;
  }
  .eval-date {
    float: right;
    font-size: 12px;
    color: #666;
  }
}
</style>
