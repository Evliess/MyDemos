import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  render: r => r(App),
}).$mount('#app')
