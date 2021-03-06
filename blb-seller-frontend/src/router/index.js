import Vue from 'vue'
import '../assets/global.css'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import Index from '../views/Index'
import Order from '../views/order/Order'
import NewOrder from '../views/order/NewOrder'
import OrderManage from '../views/order/OrderManage'
import Product from '../views/Product'
import Login from '../views/Login'
import Register from '../views/Register'
import Shop from '../views/Shop'
import Category from '../views/Category'
import Consumers from '../views/Consumers'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: '/login',
    children: [
      { path: 'index', component: Index },
      { path: 'product', component: Product },
      { path: 'shop', component: Shop },
      { path: 'category', component: Category },
      { path: 'consumers', component: Consumers },
      {
        path: 'order',
        component: Order,
        redirect: '/order/new',
        children: [
          { path: 'new', component: NewOrder },
          { path: 'manage', component: OrderManage }
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
