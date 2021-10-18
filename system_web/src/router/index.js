import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";
import {getToken} from "../utils/auth";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// router.beforeEach((to, from, next) => {
//   //逻辑：如果有token,访问登陆页面直接重定向到主页，访问其他页面均放行
//   //如果没有token，且访问其他页面会重定向到登陆页面，访问登陆页面放行
//   if(getToken()){//!== 'undefined'
//     if (to.name === 'Login') next('/')
//     else next()
//   } else if(to.name !== 'Login')
//     next({name:'Login'})
//   else next()
// })

export default router
