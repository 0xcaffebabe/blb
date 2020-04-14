import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cartShow: false,
    orderDetailShow: true
  },
  mutations: {
    toggleCart () {
      this.state.cartShow = !this.state.cartShow
    },
    toggleOrderDetail () {
      this.state.orderDetailShow = !this.state.orderDetailShow
    }
  },
  actions: {
  },
  modules: {
  }
})
