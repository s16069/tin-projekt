import Vue from 'vue'

import './scss/common.scss'

import App from './App.vue'
import router from './router'
import store from './store'
import configureValidation from './validation';

Vue.config.productionTip = false

configureValidation();

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
