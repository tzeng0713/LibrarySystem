<template>
  <div class="register-container">
    <h2>註冊帳號</h2>
    <form @submit.prevent="handleRegister">
      <input 
        type="text"
        v-model="phoneNumber"
        placeholder="手機號碼"
        pattern="^09\d{8}$"
        title="請輸入正確手機格式，例如 0912345678"
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
      <input
        type="text"
        v-model="userName"
        placeholder="使用者名稱"
        maxlength="20"
        required
      />
      <button type="submit">註冊</button>
    </form>
    <p class="back-login">
      已有帳號？<router-link to="/">點我登入</router-link>
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

// 建立響應式變數綁定 input
const phoneNumber = ref('')
const password = ref('')
const userName = ref('')

// 用來導頁
const router = useRouter()

// 處理註冊功能
const handleRegister = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/users/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        phoneNumber: phoneNumber.value,
        password: password.value,
        userName: userName.value
      })
    })


    if (!response.ok) {
      const errorData = await response.json()
      alert('❌ 註冊失敗：' + (errorData.message || '未知錯誤'))
      return
    }

    const result = await response.json()
    console.log('✅ 註冊成功：', result)

    alert('✅ 註冊成功！即將導向登入頁')
    router.push('/')  // 導回登入頁

  } catch (error) {
    console.error('❌ 發生錯誤：', error)
    alert('⚠️ 系統錯誤，請稍後再試')
  }
}
</script>

<style scoped>
.register-container {
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

.back-login {
  margin-top: 1rem;
}
.back-login a {
  color: #42b983;
  text-decoration: none;
}
</style>
