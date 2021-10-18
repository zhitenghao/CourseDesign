import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import store from './store'
import './plugins/element.js'
import './assets/styles/index.scss'
import request from "@/utils/request";
import '@/assets/icons/index'

Vue.config.productionTip = false
Vue.prototype.$request = request

new Vue({
  router,
  // store,
  render: h => h(App)
}).$mount('#app')
