import Vue from 'vue'
import '../assets/global.css'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import Index from '../views/Index'
import Order from '../views/order/Order'
import NewOrder from '../views/order/NewOrder'
import Product from '../views/Product'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: '/index',
    children: [
      { path: 'index', component: Index },
      { path: 'product', component: Product },
      {
        path: 'order',
        component: Order,
        redirect: '/order/new',
        children: [
          { path: 'new', component: NewOrder }
        ]
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
