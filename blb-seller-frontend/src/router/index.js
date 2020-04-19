import Vue from 'vue'
import '../assets/global.css'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import Index from '../views/Index'
import Order from '../views/Order'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: '/index',
    children: [
      { path: 'index', component: Index },
      { path: 'order', component: Order }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
