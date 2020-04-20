<template>
  <div>
    <el-menu :default-active="'/order/new'" mode="horizontal" router>
      <el-menu-item index="/order/new">
        <el-badge :value="12">
          新订单
        </el-badge>
      </el-menu-item>
      <el-menu-item index="2">
        <el-badge>
          订单管理
        </el-badge>
      </el-menu-item>
    </el-menu>
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return { }
  }
}
</script>

<style lang="less" scoped>
  .el-menu {
    height: 42px;
    .el-menu-item {
      height: 42px;
      line-height: 42px;
      color: black;
    }
  }
</style>
