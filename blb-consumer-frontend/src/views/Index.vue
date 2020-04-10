<template>
  <el-container>
    <el-header>
      <el-row>
        <el-col :span="4">
          <span class="logo">饱了吧
          </span>
        </el-col>
        <el-col :span="16">
          <el-menu :default-active="1" mode="horizontal" background-color="#3190e8" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="1" class="el-icon-eleme"> 外卖</el-menu-item>
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
      <el-aside width="200px" class="a">
         <span class="el-icon-more more">
         </span>
         <el-menu
          default-active="2">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main>Main</el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

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
</style>
