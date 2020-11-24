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
  </el-container>
</template>

<script>
const MainHeader = () => import('../components/home/MainHeader')
const MainAside = () => import('../components/home/MainAside')
const Login = () => import('../components/Login')
export default {
  data () {
    return {
      isCollapse: true
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
    min-width: 1400px;
  }
  .main {
    width: 1280px;
    margin: auto;
  }
  .footer {
    display: inline-block;
    bottom: 0;
    font-size: 18px;
    color: #ccc;
    width: 100%;
    text-align: center;
    color: #fff;
    padding: 0;
    background-color: #3190e8;
  }
  .side-menu {
    position: fixed;
    overflow:hidden;
    z-index: 999;
    margin-top: -10px;
    margin-left: -64px;
    transition: all 0.2s;
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
