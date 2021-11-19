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
        component: () => import('../components/Personal/index.vue')
      },
      {
        path: 'fans',
        name: 'fans',
         component: () => import('../components/Personal/MyConcerns.vue')
      },
    ]
  },


  {
    path: '/test',
    name: 'Test',
    component: () => import('../views/test2.vue')
  },
  {
    path: '/testb',
    name: 'Test2',
    component: () => import('../views/testB.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
