import Vue from 'vue'
import Router from 'vue-router'


import IndexPage from '@/components/index/IndexPage'

import Login from '@/components/login/Login'
import CommonBar from '@/components/common/CommonBar'
import UpdatePwd from '@/components/common/UpdatePwd'


import Student from '@/components/student/Student'
import StudentInfo from '@/components/student/StudentInfo'
import StudentTutor from '@/components/student/StudentTutor'
import StudentCourse from '@/components/student/StudentCourse'
import Quiz from '@/components/student/Quiz'
import ChooseCourse from '@/components/student/ChooseCourse.vue'

import Tutor from '@/components/tutor/Tutor'
import TutorInfo from '@/components/tutor/TutorInfo'
import TutorStudents from '@/components/tutor/TutorStudents'
import TutorCourseDetail from '@/components/tutor/TutorCourseDetail'
import TutorCourseOutline from '@/components/tutor/TutorCourseOutline'

import Admin from '@/components/admin/Admin'
import AdminInfo from '@/components/admin/AdminInfo'
import AdminStudentsList from '@/components/admin/AdminStudentsList'
import AdminGroupsList from '@/components/admin/AdminGroupsList'
import AdminTutorsList from '@/components/admin/AdminTutorsList'
import AdminInstitutesList from '@/components/admin/AdminInstitutesList'
import AdminCoursesList from '@/components/admin/AdminCoursesList'
import AdminUserList from '@/components/admin/AdminUserList'
import Statistics from '@/components/admin/Statistics'
import AdminStudentCoursesList from '@/components/admin/AdminStudentCoursesList'
//  
//
Vue.use(Router)

export default new Router({
	mode: 'hash',
	routes: [{
		path: '/',
		component: IndexPage
	}, {
		path: '/post',
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
				},
				{
					path: 'tutor',
					component: StudentTutor
				}, {
					path: 'course',
					component: StudentCourse
				}, {
					path: 'quiz',
					component: Quiz
				}, {
					path: 'choose-course',
					component: ChooseCourse
				}
			]
		}, {
			path: '/tutor',
			component: Tutor,
			children: [{
					path: 'info',
					component: TutorInfo
				}, {
					path: 'course',
					component: TutorCourseOutline
				}, {
					path: 'course-detail',
					component: TutorCourseDetail
				},
				{
					path: 'course-outline',
					component: TutorCourseOutline 
				}, {
					path: 'student-course',
					component: AdminStudentCoursesList
				},
			]
		}, {
			path: '/admin',
			component: Admin,
			children: [{
				path: 'info',
				component: AdminInfo
			}, {
				path: 'students',
				component: AdminStudentsList
			}, {
				path: 'groups',
				component: AdminGroupsList
			}, {
				path: 'tutors',
				component: AdminTutorsList
			}, {
				path: 'institutes',
				component: AdminInstitutesList
			}, {
				path: 'courses',
				component: AdminCoursesList
			}, {
				path: 'users',
				component: AdminUserList
			},  {
				path: 'statistics',
				component: Statistics 
			}, {
				path: 'student-course',
				component: AdminStudentCoursesList
			}, ]
		}]
	}]
})
