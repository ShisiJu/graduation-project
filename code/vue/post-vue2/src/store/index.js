import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'

Vue.use(Vuex)

const vuexLocal = new VuexPersistence({
	storage: window.localStorage
})

const state = {
	userInfo: {},
	info: {},
	quiz: {},
	course: {},
	quizAnswers: []
}

const mutations = {
	setUserInfo(state, userInfo) {
		state.userInfo = userInfo;
	},
	setInfo(state, info) {
		state.info = info;
	},
	setQuiz(state, quiz) {
		state.quiz = quiz;
	},
	setQuizAnswers(state, quizAnswers) {
		state.quizAnswers = quizAnswers;
	},
	setCourse(state, course) {
		state.course = course;
	}
}

const actions = {

}

export default new Vuex.Store({
	state,
	actions,
	mutations,
	plugins: [vuexLocal.plugin]
})
