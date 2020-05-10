import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/global.css'
import VueAMap from 'vue-amap'

import axios from 'axios'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(VueAMap)
Vue.use(ElementUI)
Vue.config.productionTip = false

// 初始化vue-amap
VueAMap.initAMapApiLoader({
  // 高德的key
  key: '8ddddd2be37d4719ab2262e6fe01edda',
  // 插件集合
  plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
  // 高德 sdk 版本，默认为 1.4.4
  v: '1.4.4'
})

// 添加上方进度条
axios.interceptors.request.use(config => {
  NProgress.start()
  return config
})
// 结束上方进度条
axios.interceptors.response.use(config => {
  NProgress.done()
  return config
},error=>{
  NProgress.done()
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
