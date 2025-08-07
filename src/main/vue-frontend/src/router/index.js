import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Library from '../views/Library.vue'

const routes = [
  // 當網址是 / 時，自動導到 /login
  { path: '/', redirect: '/login' },

  // 登入畫面
  { path: '/login', component: Login },

  // 註冊畫面
  { path: '/register', component: Register },

  // 圖書館
  { path: '/library', component: Library }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
