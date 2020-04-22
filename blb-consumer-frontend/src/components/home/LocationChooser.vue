<template>
  <div>
    <el-dialog
      title="选择城市"
      :visible="$store.state.locationChooserShow"
      @close="$store.commit('toggleLocationChooser')"
      width="50%">
      <el-autocomplete
        style="width:100%"
        v-model='input'
        :fetch-suggestions='querySearch'
        placeholder='请输入城市'
        @select='handleSelect'
        clearable
      ></el-autocomplete>
      <el-card  style="margin-top:20px">
        <div slot="header">当前定位城市：</div>
        <div class="city">
          <div><el-link type="primary" @click="handleSelect({value:$store.state.location})">{{$store.state.location}}</el-link></div>
        </div>
      </el-card>
      <el-card style="margin-top:20px">
        <div slot="header">热门城市</div>
        <div class="city">
          <div v-for="item in hotCity" :key="item">
            <el-link type="primary" @click="handleSelect({value:item})">{{item}}</el-link>
          </div>
        </div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      input: '',
      hotCity: ['北京', '上海', '广州', '深圳']
    }
  },
  methods: {
    querySearch (queryString, cb) {
      // 调用 callback 返回建议列表的数据
      cb(this.loadAll())
    },
    loadAll () {
      return [
        { value: '泉州' },
        { value: '福州' }
      ]
    },
    handleSelect (item) {
      this.$message.success(item.value)
    }
  },
  mounted () {
    this.restaurants = this.loadAll()
  }
}
</script>

<style lang="less" scoped>
  .city {
    display: flex;
    div {
      flex: 0 0 10%;
    }
  }
</style>
