import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/Login'
import CommonBar from '@/components/common/CommonBar'

import Student from '@/components/student/Student'
import StudentInfo from '@/components/student/StudentInfo'
import StudentTutor from '@/components/student/StudentTutor'
import StudentCourse from '@/components/student/StudentCourse'
import Quiz from '@/components/student/Quiz'

import Tutor from '@/components/tutor/Tutor'
import TutorInfo from '@/components/tutor/TutorInfo'
import TutorStudents from '@/components/tutor/TutorStudents'
import TutorCourses from '@/components/tutor/TutorCourses'
import TutorCourseDetail from '@/components/tutor/TutorCourseDetail'


import Admin from '@/components/admin/Admin'
import AdminInfo from '@/components/admin/AdminInfo'
import AdminStudentsList from '@/components/admin/AdminStudentsList'
import AdminGroupsList from '@/components/admin/AdminGroupsList'
import AdminTutorsList from '@/components/admin/AdminTutorsList'

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
				component: TutorCourses
			}, {
				path: 'course-detail',
				component: TutorCourseDetail
			}]
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
			},{
				path: 'tutors',
				component: AdminTutorsList
			}]
		}]
	}]
})
