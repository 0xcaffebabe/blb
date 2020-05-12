import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Takeaway from '../views/Takeaway'
import Shop from '../views/Shop'
import Search from '../views/Search'
import Order from '../views/Order'
import My from '../views/My'
import Pay from '../views/Pay'
import ConfirmOrder from '../views/ConfirmOrder'
import List from '../views/List'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: 'takeaway',
    children: [
      { path: 'takeaway', component: Takeaway },
      { path: 'shop/:shopId', component: Shop },
      { path: 'search', component: Search },
      { path: 'order', component: Order },
      { path: 'my', component: My },
      { path: 'pay', component: Pay },
      { path: 'confirmOrder', component: ConfirmOrder },
      { path: 'list/:categoryId', component: List }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
