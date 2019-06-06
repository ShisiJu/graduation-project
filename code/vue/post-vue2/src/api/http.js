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

axios.defaults.timeout = 1000000;

const jsonHeader = {
	headers: {
		'Content-Type': 'application/json;charset=UTF-8'
	}
};


export function downloadFile(url, params) {
	return axios({
		method: 'post',
		url: url,
		data: params,
		responseType: 'blob'
	}).then(response => {
		let fileName = response.headers['content-disposition'].split("=")[1];
		download(response.data, fileName)
	}).catch((error) => {})
}

// 下载文件 并且 携带参数
function download(data, fileName) {
	if (!data) {
		return
	}
	let url = window.URL.createObjectURL(new Blob([data]))
	let link = document.createElement('a')
	link.style.display = 'none'
	link.href = url
	link.setAttribute('download', fileName)
	document.body.appendChild(link)
	link.click()
}

export function get(url, params) {
	return new Promise((resolve, reject) => {
		axios.get(url, {
			params: params
		}).then(res => {
			resolve(res);
		}).catch(err => {
			reject(err)
		})
	});
}

export function post(url, params) {
	return new Promise((resolve, reject) => {
		axios.post(url, qs.stringify(params))
			.then(res => {
				resolve(res);
			})
			.catch(err => {
				reject(err)
			})
	});
}

export function postJson(url, params) {
	return new Promise((resolve, reject) => {
		axios.post(url, params, jsonHeader)
			.then(res => {
				resolve(res);
			})
			.catch(err => {
				reject(err)
			})
	});
}

export default {

}
