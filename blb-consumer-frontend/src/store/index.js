import Vue from 'vue'
import Vuex from 'vuex'
import locationService from '../service/LocationService'
Vue.use(Vuex)
const city = locationService.getLocation().city
export default new Vuex.Store({
  state: {
    cartShow: false,
    orderDetailShow: false,
    locationChooserShow: false,
    loginPanelShow: false,
    userEditPanelShow: false,
    vipPanelShow: false,
    balancePaneShow: true,
    user: {
      login: false,
      info: {}
    },
    location: city,
    lastProductAddTime: 1
  },
  mutations: {
    toggleCart () {
      this.state.cartShow = !this.state.cartShow
    },
    toggleOrderDetail () {
      this.state.orderDetailShow = !this.state.orderDetailShow
    },
    toggleLocationChooser () {
      this.state.locationChooserShow = !this.state.locationChooserShow
    },
    toggleLoginPanel () {
      this.state.loginPanelShow = !this.state.loginPanelShow
    },
    closeLoginPanel () {
      this.state.loginPanelShow = false
    },
    toggleUserEditPanel () {
      this.state.userEditPanelShow = !this.state.userEditPanelShow
    },
    setLoginState (state, val) {
      this.state.user.login = val
    },
    setUserInfo (state, info) {
      this.state.user.info = info
    },
    updateLastProductAddTime () {
      this.state.lastProductAddTime = this.state.lastProductAddTime + 1
    },
    toggleVipPanel () {
      this.state.vipPanelShow = !this.state.vipPanelShow
    },
    toggleBalancePane () {
      this.state.balancePaneShow = !this.state.balancePaneShow
    }
  },
  actions: {
  },
  modules: {
  }
})
