<template>
    <div class="container">
        <el-row>
        <el-col :span="4">
          <span class="logo">饱了吧
          </span>
        </el-col>
        <el-col :span="16">
          <el-menu :default-active="'my'"
                    mode="horizontal"
                    background-color="#3190e8"
                    text-color="#fff"
                    active-text-color="#ffd04b"
                    :router="true"
                    >
            <el-menu-item index="takeaway" class="el-icon-eleme"> 外卖</el-menu-item>
            <el-menu-item index="search" class="el-icon-search"> 搜索</el-menu-item>
            <el-menu-item index="order" class="el-icon-s-order"> 订单</el-menu-item>
            <el-menu-item index="my" class="el-icon-user"> 我的</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="4">
            <span class="el-icon-location location" @click="$store.commit('toggleLocationChooser')"> 漳州漳浦
              <span class="el-icon-caret-bottom" v-if="!$store.state.locationChooserShow"></span>
              <span class="el-icon-caret-top" v-else></span>
            </span>
        </el-col>
      </el-row>
      <location-chooser></location-chooser>
    </div>
</template>

<script>
import LocationChooser from './LocationChooser'
export default {
  data () {
    return {}
  },
  components: {
    LocationChooser
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
  .container {
    padding: 0 10px;
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
