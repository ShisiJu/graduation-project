import {
	get,
	post,
	postJson,
	downloadFile
} from './http.js'

//文件的导出 并且携带参数

export const exportStudentCourse = data => downloadFile('poi/export-student-course', data)

export const exportStudent = data => downloadFile('poi/export-student', data)

export const exportTutor = data => downloadFile('poi/export-tutor', data)

export const exportCourse = data => downloadFile('poi/export-course', data)

export const exportGroup = data => downloadFile('poi/export-group', data)



// 用户登录

export const userLogin = data => post('login/login', data)

// 登录时查询用户信息
export const findStudenInfo = data => get('student/info', data)

export const findTutorInfo = data => get('tutor/info', data)

export const findAdminInfo = data => get('admin/info', data)

// 学生
export const findStudentCourse = data => post('course/student', data)
// StudentCourse
export const findStudentCourseWithPage = data => postJson('student-course/search', data)

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
export const searchStudents = data => postJson('student/page', data)

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

export const findOptionalCourse = data => postJson('course/findOptionalCourse', data)



// Student_Course CRUD
export const searchStudentCourses = data => postJson('student-course/search', data)

export const updateScore = data => post('student-course/update-score', data)

export const deleteStudentCourse = data => get('student-course/delete', data)

//student-course
export const saveStudentCourse = data => get('student-course/save', data)



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

export const satisticByTutorAndQuestion = data => get('statistic/tutor-question', data)

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


export const computeSorce = data => {
	let count = data.A + data.B + data.C + data.D;
	let score = (data.A * 100 + data.B * 85 + data.C * 60 + data.D * 40) / count;
	let formatScore = score.toFixed(0);
	return formatScore;
}
