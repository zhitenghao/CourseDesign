import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
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
        path: 'qita',
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
        name: 'MyConcerns',
        redirect: 'fans/follows',
        component: () => import('../components/Personal/MyConcerns.vue'),
        // component: () => import('../views/test'),
        children: [
            {
              path: 'follows',
              name: 'follows',
              component: () => import('../components/Personal/FollowList/index.vue')
            },
            {
              path: 'fans',
              name: 'fans',
              component: () => import('../components/Personal/FollowList/index.vue')
            }
        ]
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
    component: () => import('../views/test'),
  },
  {
    path: '/testA',
    name: 'TestA',
    component: () => import('../views/testA'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  //?????????token,??????????????????????????????????????????;??????????????????????????????
  //????????????token??????????????????????????????????????????????????????????????????????????????
  if(window.localStorage.getItem('tokenStr')){
    if (to.name === 'Login') next('/')
    else next()
  } else if(to.name !== 'Login')
    next({name:'Login'})
  else next()
})

export default router
