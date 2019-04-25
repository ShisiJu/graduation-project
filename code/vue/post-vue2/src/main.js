// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import store from '@/store/index.js'


Vue.config.productionTip = false
Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
	el: '#app',
	router,
	template: '<App/>',
	store,
	components: {
		App
	}
})

//覆盖库的超时默认值
//现在所有请求将在超时前等待2.5秒
//覆盖此请求的超时，因为它知道需要很长时间