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
      <div class="nav-btns">
        <el-button-group>
          <el-button type="primary" icon="el-icon-back" circle @click="handleRouterBack"></el-button>
          <el-button type="primary" icon="el-icon-right" circle @click="handleRouterGo"></el-button>
        </el-button-group>
      </div>
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
      isCollapse: true
    }
  },
  methods: {
    handleSideToggle () {
      this.isCollapse = !this.isCollapse
    },
    handleRouterBack () {
      this.$router.go(-1)
    },
    handleRouterGo () {
      this.$router.go(1)
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
  .nav-btns {
    position: fixed;
    float: right;
    top: 75%;
    right: 20px;
    .el-button{
      box-shadow: 2px 7px 13px #ccc;
    }
  }
</style>
