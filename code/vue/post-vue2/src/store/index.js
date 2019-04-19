import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
	userInfo: {}
}

const mutations = {
	setType(state, userInfo) {
		state.type = type;
	}
}

const actions = {

}

export default new Vuex.Store({
	state,
	actions,
	mutations,
})
