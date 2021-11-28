import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/',
    name: 'Home',
    redirect: 'index',
    component: () => import('../views/Homepage.vue'),
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('../components/Articles/shouye.vue')
      },
      {
        path: 'siliuji',
        name: 'Siliuji',
        component: () => import('../components/Articles/siliuji.vue')
      },
      {
        path: 'kaoyan',
        name: 'Kaoyan',
        component: () => import('../components/Articles/kaoyan.vue')
      },
      {
        path: 'kaogong',
        name: 'Kaogong',
        component: () => import('../components/Articles/kaogong.vue')
      },
      {
        path: 'jiaozi',
        name: 'Jiaozi',
        component: () => import('../components/Articles/jiaozi.vue')
      },
      {
        path: 'else',
        name: 'Qita',
        component: () => import('../components/Articles/qita.vue')
      },
    ]
  },
  {
    path: '/personal',
    name: 'MyPage',
    redirect: '/personal/index',
    component: () => import('../views/MyPage.vue'),
    children: [
      {
        path: 'index',
        name: 'index',
        component: () => import('../components/Personal/PersonalIndex.vue')
      },
      {
        path: 'fans',
        name: 'fans',
         component: () => import('../components/Personal/MyConcerns.vue')
      },
      {
        path: 'messages',
        name: 'messages',
        component: () => import('../components/Personal/Message.vue')
      },
      {
        path: 'articleInfo',
        name: 'articleInfo',
        component: () => import('../components/Articles/articleInfo.vue')
      }
    ]
  },
  {
    path: '/visitor',
    name: 'Visitor',
    component: () => import('../components/Visitor/index'),
  },


  {
    path: '/test',
    name: 'Test',
    component: () => import('../components/Personal/Message.vue')
  },
  {
    path: '/testb',
    name: 'Test2',
    component: () => import('../views/testA.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// router.beforeEach((to, from, next) => {
//   //如果有token,访问登陆页面直接重定向到主页;访问其他页面均也放行
//   //如果没有token，且访问其他页面会重定向到登陆页面，访问登陆页面放行
//   if(window.localStorage.getItem('tokenStr')){
//     if (to.name === 'Login') next('/')
//     else next()
//   } else if(to.name !== 'Login')
//     next({name:'Login'})
//   else next()
// })

export default router
