<template>
  <div class="outer">
    <div class="login-container">
      <h2>借還書系統</h2>
      <form @submit.prevent="handleLogin">
        <input
          type="text"
          v-model="username"
          placeholder="手機號碼"
          pattern="^09\d{8}$"
          title="請輸入正確的手機格式，例如 0912345678"
          required
        />
        <input
          type="password"
          v-model="password"
          placeholder="密碼"
          pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,12}$"
          title="密碼須為6~12碼，且包含英文與數字"
          required
        />
        <button type="submit">登入</button>
      </form>
      <!-- 新增註冊連結 -->
      <p class="register-link">
        還沒有帳號？<router-link to="/register">點我註冊</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')
const router = useRouter()

const handleLogin = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/users/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        phoneNumber: username.value,
        password: password.value,
      }),
      credentials: 'include', // 🔥 這行讓 cookie(session) 帶上
    })

    if (!response.ok) {
      throw new Error('登入失敗')
    }

    const user = await response.json()
    console.log('✅ 登入成功，回傳使用者資訊：', user)

    // 登入成功後跳轉到 /library
    router.push('/library')

  } catch (error) {
    console.error('❌ 登入錯誤：', error)
    alert('登入失敗，請確認帳號密碼')
  }
}
</script>


<style scoped>
.outer {
  height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  max-width: 350px;
  margin: 100px auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  text-align: center;
  font-family: sans-serif;
}

input {
  display: block;
  width: 100%;
  margin: 1rem 0;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  width: 100%;
  padding: 0.75rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
}

button:hover {
  background-color: #369c71;
}

.register-link {
  margin-top: 1rem;
  font-size: 14px;
}

.register-link a {
  color: #42b983;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}

</style>
