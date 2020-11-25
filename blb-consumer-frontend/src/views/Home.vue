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
    <el-footer height="108px" class="footer">
      <ul class="footer-link-list">
            <li v-for="item in linkList" :key="item">
              <el-link>{{item}}</el-link>
            </li>
          </ul>
          <p class="copyright">Copyright © 2020 blb.ismy.wang All Rights Reserved | 闽ICP备17018216号 </p>
    </el-footer>
  </el-container>
</template>

<script>
const MainHeader = () => import('../components/home/MainHeader')
const MainAside = () => import('../components/home/MainAside')
const Login = () => import('../components/Login')
export default {
  data () {
    return {
      isCollapse: true,
      linkList: [
        '网站首页', '企业服务', '人才招聘', '联系我们', '帮助中心', '意见反馈', '友情链接'
      ]
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
    min-height: 800px;
    margin: auto;
  }
  .footer {
    box-sizing: border-box;
    bottom: 0;
    padding: 10px 100px;
    font-size: 18px;
    text-align: center;
    width: 100%;
    color: #fff;
    background-color: #3190e8;
    .footer-link-list {
      li {
        display: inline;
        margin-right: 10px;
      }
    }
    .copyright {
      padding: 0;
      margin: 0;
      font-size: 14px;
      color: #ccc;
    }
    .el-link {
      color: #eee;
      text-decoration: none;
    }
    .el-link:hover {
      color: #fff;
    }
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
    z-index: 999;
    .el-button{
      box-shadow: 2px 7px 13px #ccc;
    }
  }
</style>
