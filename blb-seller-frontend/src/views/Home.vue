<template>
  <div class="home">
    <el-container>
      <el-header>
        <span style="float:right;color:white">黄焖鸡米饭</span>
        <div class="header">饱了吧 · 商家版</div>
      </el-header>
      <el-container>
        <el-aside width="150px">
          <el-menu
            default-active="order"
            router
            >
            <el-menu-item index="/index">
              <i class="el-icon-house"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-menu-item index="/order">
              <i class="el-icon-s-order"></i>
              <span slot="title">订单</span>
            </el-menu-item>
            <el-menu-item index="/product">
              <i class="el-icon-goods"></i>
              <span slot="title">商品</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-user"></i>
              <span slot="title">顾客</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-money"></i>
              <span slot="title">财务</span>
            </el-menu-item>
            <el-menu-item index="/shop">
              <i class="el-icon-s-shop"></i>
              <span slot="title">门店</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data () {
    return {}
  }
}
</script>

<style lang="less" scoped>
  .el-header {
    padding: 10px 20px;
   background-color: rgb(0,145,234);
   height: 42px!important;
  }
  .header {
    color: white;
    font-size: 16px;
    height: 32px;
  }
  .el-menu {
    border-right: none!important;
    font-weight: 600;
  }
  .el-main {
    padding: 20px 10px;
    background-color: rgb(245,245,245);
  }
</style>
