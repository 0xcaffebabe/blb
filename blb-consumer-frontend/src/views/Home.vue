<template>
  <el-container class="container">
    <el-header style="padding:0">
      <main-header></main-header>
    </el-header>
    <el-container class="main">
      <el-aside :width="isCollapse ? '80px':'200px'" class="side-menu">
         <main-aside :is-collapse="isCollapse" @toggle="handleSideToggle"></main-aside>
      </el-aside>
      <el-container>
        <el-main>
          <el-collapse-transition>
            <router-view></router-view>
          </el-collapse-transition>
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
  .main {
    width: 1080px;
    margin: auto;
  }
  .footer {
    bottom: 0;
    font-size: 18px;
    color: #ccc;
    width: 100%;
    text-align: center;
  }
  .side-menu {
    position: fixed;
    overflow:hidden;
    z-index: 999;
    margin-top: -10px;
    margin-left: -64px;
  }
  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
</style>
