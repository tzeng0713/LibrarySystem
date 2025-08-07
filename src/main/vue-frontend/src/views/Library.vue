<template>
  <div class="library-container">
    <div class="tab-buttons">
      <button :class="{ active: currentTab === 'books' }" @click="currentTab = 'books'">
        書籍一覽
      </button>
      <button :class="{ active: currentTab === 'records' }" @click="currentTab = 'records'">
        借閱紀錄
      </button>
    </div>

    <!-- 書籍一覽 -->
    <div v-if="currentTab === 'books'" class="book-list">
      <div class="book-card" v-for="book in books" :key="book.inventoryId">
        <h3>{{ book.name }}</h3>
        <p class="author">作者：{{ book.author }}</p>
        <p class="intro">{{ book.intro }}</p>
        <p class="stock">庫存：{{ book.stock }}</p>
        <button
          :disabled="book.stock <= 0"
          @click="borrow(book)"
          >
          {{ book.stock <= 0 ? '無庫存' : '借閱' }}
        </button>
      </div>
    </div>

    <!-- 借閱紀錄 -->
    <div v-else class="record-list">
      <div class="record-header">
        <span>書名</span>
        <span>借閱時間</span>
        <span>歸還時間</span>
        <span></span>
      </div>
      <div class="record-item" v-for="record in borrowRecords" :key="record.id">
        <span>{{ record.name }}</span>
        <span>{{ record.borrowingTime }}</span>
        <span>
            <template v-if="record.returnTime">
            {{ record.returnTime }} 
            </template>
            <template v-else>—</template>
        </span>
        <span>
            <button v-if="!record.returnTime" @click="returnBook(record)">歸還</button>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const currentTab = ref('books')
const books = ref([])
const borrowRecords = ref([])

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/api/library/books')
    const data = await response.json()
    books.value = data.map(book => ({
      inventoryId: 0, // 替代欄位，因為目前沒有用到
      isbn: book.isbn,
      name: book.name,
      author: book.author,
      intro: book.introduction,
      stock: book.availableCount,
      status: book.statusText
    }))
  } catch (error) {
    console.error('取得書籍資料失敗', error)
  }

  // 撈借閱紀錄
  try {
    const res = await fetch('http://localhost:8080/api/library/records', {
      credentials: 'include' // 有用 session 就必加
    })
    const data = await res.json()

    borrowRecords.value = data.map(record => ({
      name: record.bookName,
      borrowingTime: record.borrowingTime,
      returnTime: record.returnTime,
      inventoryId: record.inventoryId
    }))
  } catch (error) {
    console.error('取得借閱紀錄失敗', error)
  }

})


const borrow = async (book) => {
  try {
    const response = await fetch('http://localhost:8080/api/library/borrow', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: JSON.stringify({ isbn: book.isbn })
    })

    if (response.ok) {
      alert(`成功借閱《${book.name}》`)
      // 更新前端畫面：庫存數 -1
      book.stock--

      // 若庫存變 0，自動把狀態改成「無庫存」
      if (book.stock === 0) {
        book.status = '無庫存'
      }

    } else {
      const text = await response.text()
      alert(`借書失敗：${text}`)
    }
  } catch (error) {
    console.error('借書請求失敗', error)
    alert('系統錯誤，請稍後再試')
  }
}

const returnBook = async (record) => {
  try {
    const res = await fetch('http://localhost:8080/api/library/return', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: JSON.stringify({
        inventoryId: record.inventoryId,
      })
    })

    if (!res.ok) throw new Error('還書失敗')

    alert('歸還成功！')

    // 直接更新前端畫面（不用重新抓全部資料）
    const now = new Date()
    record.returnTime = now.toLocaleString('zh-TW', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    }).replace(/\//g, '-').replace(',', '')


  } catch (err) {
    console.error('還書失敗', err)
    alert('歸還失敗，請稍後再試')
  }
}
</script>

<style scoped>
.library-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 24px;
}

.tab-buttons {
  position: fixed;
  top: 50px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 999;
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 24px;
}

.tab-buttons button {
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  background-color: #ddd;
  cursor: pointer;
}

.tab-buttons .active {
  background-color: #42b983;
  color: white;
}

.book-list {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 24px;
}

.book-card {
  width: 200px;
  padding: 16px;
  border: 1px solid #ccc;
  border-radius: 12px;
  text-align: center;
  background-color: #fdfdfd;
  box-shadow: 2px 2px 6px rgba(0,0,0,0.1);
}

.author {
  font-size: 12px;
  margin-bottom: 6px;
}

.intro {
  font-size: 12px;
  color: #555;
  height: 40px;
  overflow: hidden;
  text-overflow: ellipsis;
}

.stock {
  font-size: 12px;
  margin: 8px 0;
}

button {
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  background-color: #42b983;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #369c71;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.record-list {
  max-width: 800px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
}

.record-header,
.record-item {
  display: grid;
  grid-template-columns: 5fr 4fr 4fr 1fr;
  gap: 12px;
  align-items: center;
  padding: 14px 16px;
  border-bottom: 1px solid #ddd;
}

.record-header {
  font-weight: bold;
  background-color: #f0f0f0;
  border-top: 1px solid #ccc;
  border-bottom: 2px solid #ccc;
}
</style>