import Vue from 'vue'
import App from './App.vue' // vue最开始的入口
import router from './router' // 导入路由及配置
import axios from 'axios' // 导入axios
import VueAxios from 'vue-axios' // 导入vue-axios以适配axios（axios默认不适配vue）
import ElementUI from 'element-ui' // 导入elementui
import locale from 'element-ui/lib/locale/lang/zh-CN' // 导入中文
import './styles.scss' // element-ui全局主题文件
import 'core-js/stable' // 引入babel/polyfill,适配IE
import 'regenerator-runtime/runtime' // 引入babel/polyfill,适配IE

Vue.use(ElementUI, { locale }).use(VueAxios, axios)

Vue.config.productionTip = false

/** 注入axios配置 */
axios.defaults.baseURL = process.env.VUE_APP_URL
axios.interceptors.response.use(
  response => {
    return Promise.resolve(response)
  },
  e => {
    if (/code [401|301]/.test(e)) {
      window.localStorage.removeItem('expire_time')
      router.replace('/login').catch(() => {})
    }
    return Promise.reject(e)
  }
)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
