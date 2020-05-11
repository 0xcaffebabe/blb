<template>
  <div>
    <el-menu
      default-active="2" :collapse="isCollapse" :collapse-transition="false" class="aside-menu" :unique-opened="true">
      <span class="collapse el-icon-more" @click="toggleAside"></span>
      <span v-if="categoryList.length == 0">暂无数据</span>
      <el-submenu :index="item.categoryId + ''" v-for="item in categoryList" :key="item.categoryId">
        <template slot="title">
          <div class="food-category-title">
            <img :src="item.categoryImg" alt="" width="20">
            <span> {{item.categoryName}}</span>
            <el-tag type="info" size="mini">2264</el-tag>
          </div>
        </template>
        <el-menu-item :index="item1.categoryId + ''" v-for="item1 in item.subCategoryList" :key="item1.categoryId">
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
      categoryList: []
    }
  },
  methods: {
    toggleAside () {
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
