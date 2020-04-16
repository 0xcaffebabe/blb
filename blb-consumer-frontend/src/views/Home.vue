<template>
  <el-container>
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
