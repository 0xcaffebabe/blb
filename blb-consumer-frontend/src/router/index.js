import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Takeaway from '../views/Takeaway'
import Shop from '../views/Shop'
import Search from '../views/Search'
import Order from '../views/Order'
import My from '../views/My'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: 'takeaway',
    children: [
      { path: 'takeaway', component: Takeaway },
      { path: 'shop', component: Shop },
      { path: 'search', component: Search },
      { path: 'order', component: Order },
      { path: 'my', component: My }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
