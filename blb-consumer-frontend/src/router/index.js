import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Takeaway from '../views/Takeaway'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: 'takeaway',
    children: [
      { path: 'takeaway', component: Takeaway }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
