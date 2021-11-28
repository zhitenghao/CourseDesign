import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    category_id:''
  },
  mutations: {
    CHANGE_CATEGORY:(state, payload) => {
      state.category_id = payload
    }
  },
  actions: {
  },
  modules: {
  }
})
