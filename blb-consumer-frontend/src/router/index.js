import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Takeaway from '../views/Takeaway'
import Shop from '../views/Shop'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: 'takeaway',
    children: [
      { path: 'takeaway', component: Takeaway },
      { path: 'shop', component: Shop }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
