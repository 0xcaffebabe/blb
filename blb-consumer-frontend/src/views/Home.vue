<template>
  <el-container>
    <el-header>
      <el-row>
        <el-col :span="4">
          <span class="logo">饱了吧
          </span>
        </el-col>
        <el-col :span="16">
          <el-menu :default-active="'takeaway'"
                    mode="horizontal"
                    background-color="#3190e8"
                    text-color="#fff"
                    active-text-color="#ffd04b"
                    :router="true"
                    >
            <el-menu-item index="takeaway" class="el-icon-eleme"> 外卖</el-menu-item>
            <el-menu-item index="2" class="el-icon-search"> 搜索</el-menu-item>
            <el-menu-item index="3" class="el-icon-s-order"> 订单</el-menu-item>
            <el-menu-item index="4" class="el-icon-user"> 我的</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="4">
            <span class="el-icon-location location"> 漳州漳浦 <span class="el-icon-caret-bottom"></span></span>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '80px':'200px'">
         <el-menu
          default-active="2" :collapse="isCollapse" :collapse-transition="false" class="aside-menu" :unique-opened="true">
          <span class="collapse el-icon-more" @click="toggleSideMenu"></span>
          <el-submenu index="1">
            <template slot="title">
              <div class="food-category-title">
                <img src="https://fuss10.elemecdn.com/b/ff/533cf9617bd57fe1dfb05603bebcfpng.png" alt="" width="20">
                <span> 快餐便当</span>
                <el-tag type="info" size="mini">2264</el-tag>
              </div>
            </template>
            <el-menu-item index="1-4-1">
              简餐
              <span style="float:right">2075</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <div class="food-category-title">
                <img src="https://fuss10.elemecdn.com/4/35/a7eda7659bac613e524ca7c1ae12epng.png" alt="" width="20">
                <span> 小吃夜宵</span>
                <el-tag type="info" size="mini">179</el-tag>
              </div>
            </template>
            <el-menu-item index="1-4-1">
              小龙虾
              <span style="float:right">26</span>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      isCollapse: false
    }
  },
  methods: {
    toggleSideMenu () {
      this.isCollapse = !this.isCollapse
    }
  }
}
</script>

<style lang="less" scoped>
  .logo {
    height: 56px;
    line-height: 56px;
    color: #fff;
    font-size: 20px;
    text-shadow: 2px 2px 3px rgba(0,0,0,.5);
  }
  .el-header {
    background-color: #3190e8;
  }
  .location {
    cursor: pointer;
    height: 56px;
    line-height: 56px;
    font-size: 16px;
    color:white;
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
