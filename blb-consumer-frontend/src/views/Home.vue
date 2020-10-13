<template>
  <el-container class="container">
    <el-header style="padding:0">
      <main-header></main-header>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse ? '80px':'200px'" style="overflow:hidden">
         <main-aside :is-collapse="isCollapse" @toggle="handleSideToggle"></main-aside>
      </el-aside>
      <el-container>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    <login/>
    <el-footer class="footer">
      BLB © 2020 版权所有
    </el-footer>
  </el-container>
</template>

<script>
import MainHeader from '../components/home/MainHeader'
import MainAside from '../components/home/MainAside'
import Login from '../components/Login'
export default {
  data () {
    return {
      isCollapse: false
    }
  },
  methods: {
    handleSideToggle () {
      this.isCollapse = !this.isCollapse
    }
  },
  components: {
    MainHeader, MainAside, Login
  }
}
</script>

<style lang="less" scoped>
  .container {
    min-width: 1080px;
  }
  .footer {
    bottom: 0;
    font-size: 18px;
    color: #ccc;
    width: 100%;
    text-align: center;
  }
</style>
