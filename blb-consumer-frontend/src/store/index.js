import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cartShow: false
  },
  mutations: {
    toggleCart () {
      this.state.cartShow = !this.state.cartShow
    }
  },
  actions: {
  },
  modules: {
  }
})
