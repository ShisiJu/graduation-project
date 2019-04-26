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

// 管理员
export const findStudenPage = data => get('student/page',data)

export const countStudents = data => get('student/count',data)

export const insertStudent = data => get('student/insert',data)

export const updateStudent = data => get('student/update',data)

export const getAllGroup = data => get('group/all',data)