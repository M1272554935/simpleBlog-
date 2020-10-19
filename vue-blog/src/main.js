import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Axios from 'axios'
import 'gitalk/dist/gitalk.css'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'



Vue.use(mavonEditor)
Vue.config.productionTip = false
Vue.prototype.$axios = Axios;


Vue.use(ElementUI);
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
