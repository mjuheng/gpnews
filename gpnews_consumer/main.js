import Vue from 'vue'
import App from './App'
import { $request } from './common/request.js'

Vue.prototype.FILE = 'http://127.0.0.1:9000/'
Vue.prototype.$http = $request
Vue.prototype.CONSUMER = 
Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
