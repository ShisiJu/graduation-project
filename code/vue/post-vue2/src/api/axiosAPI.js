import {get,post,postJson} from './http.js'

export const userLogin = data => post('login/login', data)

// 登录时查询用户信息
export const findStudenInfo = data => get('student/info',data)

export const findTutorInfo = data => get('tutor/info',data)

export const findAdminInfo = data => get('admin/info',data)

// 学生
export const findStudentCourse = data => post('course/student',data)

export const findTutorCourse = data => postJson('course/tutor',data)

export const insertQuizWithAnswers = data => postJson('quiz/quizMap',data)

// 导师
export const findCoursesByTutor = data => get('course/tutor',data)

export const findCourseDetail = data => postJson('quiz/detail',data)
