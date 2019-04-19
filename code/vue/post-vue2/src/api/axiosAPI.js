import Vue from 'vue'
//axios post请求需要JSON参数化
import qs from 'qs'
//axios
import axios from 'axios'
//设置默认路径
axios.defaults.baseURL = '/api'
// 先导入vuex,因为我们要使用到里面的状态对象
import store from '@/store/index';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.timeout = 10000;

// 请求拦截器
axios.interceptors.request.use(
	config => {
		// 每次发送请求之前判断vuex中是否存在token        
		// 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
		// 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断 
		const token = store.state.token;
		token && (config.headers.Authorization = token);
		return config;
	},
	error => {
		return Promise.error(error);
	})


// 响应拦截器
axios.interceptors.response.use(
response => {
	// 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据     
	// 否则的话抛出错误
	if (response.status === 200) {
		return Promise.resolve(response);
	} else {
		return Promise.reject(response);
	}
},
// 服务器状态码不是2开头的的情况
// 这里可以跟你们的后台开发人员协商好统一的错误状态码    
// 然后根据返回的状态码进行一些操作，例如登录过期提示，错误提示等等
// 下面列举几个常见的操作，其他需求可自行扩展
error => {
	if (error.response.status) {
		switch (error.response.status) {
			// 401: 未登录
			// 未登录则跳转登录页面，并携带当前页面的路径
			// 在登录成功后返回当前页面，这一步需要在登录页操作。                
			case 401:
				router.replace({
					path: '/login',
					query: {
						redirect: router.currentRoute.fullPath
					}
				});
				break;

				// 404请求不存在
			case 404:
				Toast({
					message: '网络请求不存在',
					duration: 1500,
					forbidClick: true
				});
				break;
				// 其他错误，直接抛出错误提示
			
		}
		return Promise.reject(error.response);
	}
}
});



export const userLogin = data => axois.post('login/login', qs.stringify(data), formHeader)



export default {

}
