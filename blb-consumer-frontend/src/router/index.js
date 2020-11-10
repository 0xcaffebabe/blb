import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const Home = () => import('../views/Home.vue')
const Takeaway = () => import('../views/Takeaway')
const Shop = () => import('../views/Shop')
const Search = () => import('../views/Search')
const Order = () => import('../views/Order')
const My = () => import('../views/My')
const Pay = () => import('../views/Pay')
const ConfirmOrder = () => import('../views/ConfirmOrder')
const List = () => import('../views/List')
const Service = () => import('../views/Service')
const PointsMall = () => import('../views/PointsMall')
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
      { name: 'pay', path: 'pay/:orderId', component: Pay },
      { name: 'confirmOrder', path: 'confirmOrder', component: ConfirmOrder },
      { name: 'pointsMall', path: 'pointsMall', component: PointsMall },
      { name: 'service', path: 'service', component: Service },
      { path: 'list/:categoryId', component: List }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
