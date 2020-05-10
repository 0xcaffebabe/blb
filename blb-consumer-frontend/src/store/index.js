import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cartShow: false,
    orderDetailShow: true,
    locationChooserShow: false,
    loginPanelShow: false,
    userEditPanelShow: true,
    user: {
      login: false,
      info: {}
    },
    location: '漳州'
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
    }
  },
  actions: {
  },
  modules: {
  }
})
