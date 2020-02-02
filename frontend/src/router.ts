import Vue from 'vue'
import VueRouter from 'vue-router'
import store from './store'


import PageMain from './pages/PageMain.vue'
import PageCart from './pages/PageCart.vue'
import PageLogin from './pages/PageLogin.vue'
import PageRegister from './pages/PageRegister.vue'
import PageAccount from './pages/PageAccount.vue'
import PageEditMenu from './pages/PageEditMenu.vue'
import PageEditPizza from './pages/PageEditPizza.vue'
import PageAddPizza from './pages/PageAddPizza.vue'
import PageOrder from './pages/PageOrder.vue'
import PageOrders from './pages/PageOrders.vue'

Vue.use(VueRouter)

const routes = [
  { path: '*', redirect: '/' },
  { name: 'main', path: '/', component: PageMain },
  { name: 'cart', path: '/cart', component: PageCart },
  { name: 'login', path: '/login', component: PageLogin },
  { name: 'register', path: '/register', component: PageRegister },
  { name: 'account', path: '/account', component: PageAccount },
  { name: 'admin-menu', path: '/admin/menu', component: PageEditMenu },
  { name: 'admin-pizza-add', path: '/admin/pizza', component: PageEditPizza },
  { name: 'admin-pizza-edit', path: '/admin/pizza/:pizzaId', component: PageEditPizza },
  { name: 'add-pizza', path: '/cart/pizza/:pizzaId', component: PageAddPizza },
  { name: 'orders', path: '/orders', component: PageOrders },
  { name: 'order', path: '/order/:orderId', component: PageOrder },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/', '/login', '/register'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = !!store.state.user.token

  if (authRequired && !loggedIn) {
    return next('/login');
  }

  next();
})

export default router
