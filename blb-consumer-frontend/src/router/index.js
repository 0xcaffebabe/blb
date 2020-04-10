import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/Index.vue'
Vue.use(VueRouter)

const routes = [
  { path: '/', component: Index }
]

const router = new VueRouter({
  routes
})

export default router
