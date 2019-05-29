import {
	get,
	post,
	postJson
} from './http.js'

export const userLogin = data => post('login/login', data)

// 登录时查询用户信息
export const findStudenInfo = data => get('student/info', data)

export const findTutorInfo = data => get('tutor/info', data)

export const findAdminInfo = data => get('admin/info', data)

// 学生
export const findStudentCourse = data => post('course/student', data)

export const findStudentCourseWithPage = data => postJson('course/studentWithPage', data)

export const findTutorCourse = data => postJson('course/tutor', data)

export const insertQuizWithAnswers = data => postJson('quiz/quizMap', data)

// 导师
export const findCoursesByTutor = data => get('course/tutor', data)

export const findCourseDetail = data => postJson('quiz/detail', data)

// 管理员

// 通用信息
export const getAllGroup = data => get('group/all', data)

export const getGroupByInstituteId = data => post('group/institute', data)

export const getAllInstitute = data => get('institute/all', data)

export const getGroupByInstituteIdIn = data => postJson('group/institute_ids', data)

export const getAllTutor = data => get('tutor/all', data)

// Student CRUD
export const searchStudents = data => get('student/searchStudents', data)

export const updateStudent = data => postJson('student/update', data)

export const deleteStudent = data => get('student/delete', data)

// Tutor CRUD
export const searchTutors = data => postJson('tutor/searchTutors', data)

export const saveTutor = data => postJson('tutor/saveTutor', data)

export const deleteTutor = data => get('tutor/deleteTutor', data)


// Institute CRUD
export const pageInstitues = data => post('/institute/page', data)

export const saveInstitue = data => postJson('/institute/save', data)

export const deleteInstitue = data => get('/institute/delete', data)


// Group CRUD
export const searchGroups = data => postJson('group/searchGroups', data)

export const saveGroup = data => postJson('group/saveGroup', data)

export const deleteGroup = data => get('group/deleteGroup', data)

// Course CRUD
export const searchCourses = data => postJson('course/search', data)

export const saveCourse = data => postJson('course/save', data)

export const deleteCourse = data => get('course/delete', data)

export const getGroupsByCourseId = data => get('course/findGroupsByCourseId', data)

// User CRUD
export const searchUser = data => postJson('user/search', data)

export const deleteUser = data => get('user/delete', data)

export const updatePwd = data => post('user/updatePwd', data)

export const resetPwd = data => post('user/resetPwd', data)

// Quiz 查询

export const searchQuizByCustom = data => postJson('quiz/custom', data)

// charts 统计分析
export const statisticByTutorId = data => get('statistic/recent', data)

export const satisticByTutor = data => get('statistic/tutor', data)

export const satisticByInstitute = data => get('statistic/institute', data)


// 常用的工具函数

export const cloneObject = obj => {
	let objStr = JSON.stringify(obj);
	return JSON.parse(objStr);
}

export const turnToEleArr = (arr, other) => {
	let eleObj = [];
	arr.forEach(e => {
		let obj = {};
		obj['label'] = e.name;
		obj['value'] = e.id;
		if (other != null) {
			obj[other] = e[other];
		}
		eleObj.push(obj);
	});
	return eleObj;
}
