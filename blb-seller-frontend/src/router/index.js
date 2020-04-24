import Vue from 'vue'
import '../assets/global.css'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import Index from '../views/Index'
import Order from '../views/order/Order'
import NewOrder from '../views/order/NewOrder'
import Product from '../views/Product'
import Login from '../views/Login'
import Register from '../views/Register'
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
  },
  { path: '/login', component: Login },
  { path: '/register', component: Register }
]

const router = new VueRouter({
  routes
})

export default router
