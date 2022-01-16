import Vue from 'vue'
import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const Login = () => import(/* webpackChunkName: "login" */ '../pages/Login.vue') // 登录页

const UserHomePage = () => import(/* webpackChunkName: "user" */ '../pages/user/HomePage.vue')
const UserStart = () => import(/* webpackChunkName: "user" */ '../pages/user/Start.vue') // 用户开始页
const UserDiagnos = () => import(/* webpackChunkName: "user" */ '../pages/user/Diagnos.vue') // 用户填写表单页
const UserDisplay = () => import(/* webpackChunkName: "user" */ '../pages/user/Display.vue') // 用户诊断结果页和医生建议

const DoctorHomePage = () => import(/* webpackChunkName: "doctor" */ '../pages/doctor/HomePage.vue') // 医生主页
const DoctorDiagnos = () => import(/* webpackChunkName: "doctor" */ '../pages/doctor/Diagnosis.vue') // 医生辅助诊断页
const DoctorRecord = () => import(/* webpackChunkName: "doctor" */ '../pages/doctor/Record.vue') // 医生查看所有诊断用户记录页
const DoctorTrend = () => import(/* webpackChunkName: "doctor" */ '../pages/doctor/Trend.vue') // 医生查看诊断趋势

const DoctorDiagnosPage = () => import(/* webpackChunkName: "doctor" */ '../pages/doctor/diagnosis/DiagnosPage.vue') // 医生辅助诊断页
const DoctorSingleDiagnos = () => import(/* webpackChunkName: "doctorDiagnos" */ '../pages/doctor/diagnosis/SingleDiagnos.vue') // 单次诊断
const DoctorMultiDiagnos = () => import(/* webpackChunkName: "doctorDiagnos" */ '../pages/doctor/diagnosis/MultiDiagnos.vue') // 多次诊断
const DoctorHistoryDiagnos = () => import(/* webpackChunkName: "doctorDiagnos" */ '../pages/doctor/diagnosis/HistoryDiagnos.vue') // 诊断历史

Vue.use(VueRouter)

const userRoutes = [
  {
    path: '/',
    name: 'userStart',
    component: UserStart,
    meta: {
      main: true
    }
  },
  {
    path: 'diagnos',
    name: 'userDiagnos',
    component: UserDiagnos
  },
  {
    path: 'diagnos/result',
    name: 'userResult',
    component: UserDisplay
  }
]

const doctorRoutes = [
  {
    path: '/',
    component: DoctorDiagnosPage,
    children: [
      {
        path: '/',
        name: 'doctorDiagnos',
        component: DoctorDiagnos,
        meta: {
          main: true
        }
      },
      {
        path: 'diagnos/single',
        name: 'doctorSingleDiagnos',
        component: DoctorSingleDiagnos,
        meta: {
          index: 0
        }
      },
      {
        path: 'diagnos/multi',
        name: 'doctorMultiDiagnos',
        component: DoctorMultiDiagnos,
        meta: {
          index: 1
        }
      },
      {
        path: 'diagnos/history',
        name: 'doctorHistoryDiagnos',
        component: DoctorHistoryDiagnos,
        meta: {
          index: 2
        }
      }
    ]
  },
  {
    path: 'record',
    name: 'doctorRecord',
    component: DoctorRecord
  },
  {
    path: 'tendency',
    name: 'doctorTrend',
    component: DoctorTrend
  }
]

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/user',
    component: UserHomePage,
    children: userRoutes
  },
  {
    path: '/doctor',
    component: DoctorHomePage,
    children: doctorRoutes
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

/**
 * 全局路由拦截
 * 1.判断用户是否登录或者登录过期，登录过则跳过登录页
 * 2.判断用户是否登录或者登录过期，过期则转向登录页
 * 3.判断用户是否跳转到了未定义的页面，是则转到menu页
 */
router.beforeEach((to, form, next) => {
  NProgress.start()
  if (to.name === 'login' && window.localStorage.getItem('expire_time') > new Date().getTime()) {
    if (window.localStorage.getItem('userType') === '1') {
      next('/doctor')
    } else {
      next('/user')
    }
  } else if (to.name !== 'login' && window.localStorage.getItem('expire_time') < new Date().getTime()) {
    next('/login')
  } else if (to.name == null) { // 说明将要跳转到未知页
    if (window.localStorage.getItem('userType') === '1') {
      next('/doctor')
    } else {
      next('/user')
    }
  }
  next()
})
router.afterEach(() => {
  NProgress.done()
})
export default router
