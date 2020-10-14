<template>
  <div>
    <el-menu
      default-active="2"
      :collapse="isCollapse"
      :collapse-transition="false"
      class="aside-menu"
      :unique-opened="true"
      :router="true"
      >
      <span class="collapsev" :class="{'el-icon-caret-right': collapsed, 'el-icon-caret-left': !collapsed}" @click="toggleAside"></span>
      <span v-if="categoryList.length == 0">暂无数据</span>
      <el-submenu :index="item.categoryId + ''" v-for="item in categoryList" :key="item.categoryId">
        <template slot="title">
          <div class="food-category-title">
            <img :src="item.categoryImg" alt="" width="20">
            <span> {{item.categoryName}}</span>
            <el-tag type="info" size="mini">2264</el-tag>
          </div>
        </template>
        <el-menu-item :index="'/list/' + item1.categoryId" v-for="item1 in item.subCategoryList" :key="item1.categoryId">
          {{item1.categoryName}}
          <span style="float:right">2075</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import categoryService from '../../service/CategoryService'
export default {
  props: ['isCollapse'],
  data () {
    return {
      categoryList: [],
      collapsed: false
    }
  },
  methods: {
    toggleAside () {
      this.collapsed = !this.collapsed
      this.$emit('toggle')
    },
    async getShopCategory () {
      try {
        this.categoryList = await categoryService.getShopCategory()
      } catch (e) {
        this.$message.error(e.message)
      }
    }
  },
  created () {
    this.getShopCategory()
  }
}
</script>

<style lang="less" scoped>
  .el-menu {
    transition: all 0.2s;
    border-radius: 5px;
    box-shadow: 2px 2px 13px #ccc;
  }
  .collapse {
    width: 100%;
    height: 24px;
    line-height: 24px;
    text-align: center;
    background-color: #3190e8;
    color: #fff;
    cursor: pointer;
  }
  .collapsev {
    position: absolute;
    font-size: 22px;
    top:50%;
    margin-top: -25px;
    right: -10px;
    width: 24px;
    z-index: 9999;
    height: 50px;
    line-height: 50px;
    background-color: #3190e8;
    border-radius: 5px;
    color: white;
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
