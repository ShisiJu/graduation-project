import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/Login'
import Student from '@/components/student/Student'
import StudentInfo from '@/components/student/StudentInfo'
import CommonBar from '@/components/common/CommonBar'
Vue.use(Router)

export default new Router({
	routes: [{
		path: '/',
		component: Login
	}, {
		path: '/common',
		name: 'CommonBar',
		component: CommonBar,
		children: [{
			path: '/student',
			component: Student,
			children: [{
				path: 'info',
				component: StudentInfo
			}]
		}]
	}]
})
