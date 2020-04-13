<template>
  <div>
    <el-menu
      default-active="2" :collapse="isCollapse" :collapse-transition="false" class="aside-menu" :unique-opened="true">
      <span class="collapse el-icon-more" @click="toggleAside"></span>
      <el-submenu :index="item+''" v-for="item in 8" :key="item">
        <template slot="title">
          <div class="food-category-title">
            <img src="https://fuss10.elemecdn.com/b/ff/533cf9617bd57fe1dfb05603bebcfpng.png" alt="" width="20">
            <span> 快餐便当</span>
            <el-tag type="info" size="mini">2264</el-tag>
          </div>
        </template>
        <el-menu-item :index="item+'-'+item1" v-for="item1 in 6" :key="item1">
          简餐
          <span style="float:right">2075</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  props: ['isCollapse'],
  data () {
    return {}
  },
  methods: {
    toggleAside () {
      this.$emit('toggle')
    }
  }
}
</script>

<style lang="less" scoped>
  .collapse {
    width: 100%;
    height: 24px;
    line-height: 24px;
    text-align: center;
    background-color: #3190e8;
    color: #fff;
    cursor: pointer;
  }
  .el-aside {
    margin-top: 5px;
    transition: width 0.5s;
  }
  .food-category-title{
    position: relative;
    .el-tag {
      position: absolute;
      right: 15px;
      margin-top: 20px;
      border-radius: 50px;
    }
  }
</style>
