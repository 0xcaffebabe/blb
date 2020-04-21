import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cartShow: false,
    orderDetailShow: true,
    locationChooserShow: false,
    loginPanelShow: true
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
    }
  },
  actions: {
  },
  modules: {
  }
})
