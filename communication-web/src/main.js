import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import 'element-ui/lib/theme-chalk/index.css'

import { postRequest, putRequest, getRequest, deleteRequest } from '@/utils/api'

Vue.config.productionTip = false

// 插件形式使用请求
Vue.prototype.postRequest = postRequest
Vue.prototype.putRequest = putRequest
Vue.prototype.getRequest = getRequest
Vue.prototype.deleteRequest = deleteRequest

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
