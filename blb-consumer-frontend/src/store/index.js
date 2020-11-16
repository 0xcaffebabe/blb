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
    balancePaneShow: false,
    couponPaneShow: false,
    shopChatShow: false,
    evalPaneShow: false,
    complaintPaneShow: true,
    user: {
      login: false,
      info: {}
    },
    location: city,
    lastProductAddTime: 1,
    confirmOrderData: { // 订单确认页需要用到的数据
      shopInfo: {},
      productList: []
    }
  },
  mutations: {
    setConfirmOrderData (state, data) {
      this.state.confirmOrderData.shopInfo = data.shopInfo
      this.state.confirmOrderData.productList = data.productList
    },
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
    },
    toggleCouponPaneShow () {
      this.state.couponPaneShow = !this.state.couponPaneShow
    },
    toggleShopChatShow () {
      this.state.shopChatShow = !this.state.shopChatShow
    },
    closeEvalPaneShow () {
      this.state.evalPaneShow = false
    },
    toogleEvalPaneShow () {
      this.state.evalPaneShow = !this.state.evalPaneShow
    },
    toggleComplaintPane () {
      this.state.complaintPaneShow = !this.state.complaintPaneShow
    }
  },
  actions: {
  },
  modules: {
  }
})
