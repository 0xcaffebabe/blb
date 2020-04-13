<template>
  <el-row :gutter='20'>
    <el-col :span='18'>
      <el-card>
        <el-tag class="eval-tag" type='primary' effect='dark'>全部评价(473)</el-tag>
        <el-tag class="eval-tag" v-for='item in tags' :key='item._id' :type="item.unsatisfied?'info':'primary'">{{item.name}}({{item.count}})</el-tag>
        <el-divider></el-divider>
        <ul class="eval-list">
          <li v-for="item in 6" :key="item" class="eval-list-item">
            <el-row>
              <el-col :span="2">
                <el-avatar>user</el-avatar>
              </el-col>
              <el-col :span="22">
                <span class="eval-date">2020-04-13</span>
                <h3>173****3770</h3>
                <el-rate :value="3.7" disabled></el-rate>
                <p>不错，不错，味道好极了不错，不错，味道好极了不错，不错，味道好极了不错，不错，味道好极了</p>
              </el-col>
            </el-row>
            <el-divider></el-divider>
          </li>
        </ul>
      </el-card>
    </el-col>
    <el-col :span='6'>
      <el-card class='eval-info'>
        <h2>4.1</h2>
        <p class='eval-avg'>综合评价</p>
        <p class='rating'>高于周围87%的商家</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data () {
    return {
      tags: [
        {
          name: '全部',
          _id: '5a22f885ec81ce77ee8449a3',
          unsatisfied: false,
          count: 473
        },
        {
          name: '满意',
          _id: '5a22f885ec81ce77ee8449a2',
          unsatisfied: false,
          count: 453
        },
        {
          name: '不满意',
          _id: '5a22f885ec81ce77ee8449a1',
          unsatisfied: true,
          count: 20
        },
        {
          name: '有图',
          _id: '5a22f885ec81ce77ee8449a0',
          unsatisfied: false,
          count: 2
        },
        {
          name: '味道好',
          _id: '5a22f885ec81ce77ee84499f',
          unsatisfied: false,
          count: 47
        },
        {
          name: '送货快',
          _id: '5a22f885ec81ce77ee84499e',
          unsatisfied: false,
          count: 32
        },
        {
          name: '分量足',
          _id: '5a22f885ec81ce77ee84499d',
          unsatisfied: false,
          count: 18
        },
        {
          name: '包装精美',
          _id: '5a22f885ec81ce77ee84499c',
          unsatisfied: false,
          count: 15
        },
        {
          name: '干净卫生',
          _id: '5a22f885ec81ce77ee84499b',
          unsatisfied: false,
          count: 15
        },
        {
          name: '食材新鲜',
          _id: '5a22f885ec81ce77ee84499a',
          unsatisfied: false,
          count: 15
        },
        {
          name: '服务不错',
          _id: '5a22f885ec81ce77ee844999',
          unsatisfied: false,
          count: 11
        }
      ]
    }
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
