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
      login: false
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
    toggleUserEditPanel () {
      this.state.userEditPanelShow = !this.state.userEditPanelShow
    }
  },
  actions: {
  },
  modules: {
  }
})
