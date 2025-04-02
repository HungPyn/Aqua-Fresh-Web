<template>
  <div
    class="container d-flex vh-100 align-items-center justify-content-center"
  >
    <div class="card p-4 shadow" style="width: 350px">
      <h2 class="text-center mb-4">{{ isLogin ? "Đăng nhập" : "Đăng ký" }}</h2>

      <form @submit.prevent="handleSubmit">
        <div v-if="!isLogin" class="mb-3">
          <label class="form-label">Tên đăng nhập</label>
          <input v-model="username" type="text" class="form-control" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Tên đăng nhập</label>
          <input v-model="email" type="email" class="form-control" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Mật khẩu</label>
          <input
            v-model="password"
            type="password"
            class="form-control"
            required
          />
        </div>
        <div v-if="!isLogin" class="mb-3">
          <label class="form-label">Xác nhận mật khẩu</label>
          <input
            v-model="confirmpass"
            type="password"
            class="form-control"
            required
          />
        </div>

        <button type="submit" class="btn btn-primary w-100">
          {{ isLogin ? "Đăng nhập" : "Đăng ký" }}
        </button>
      </form>

      <div v-if="isLogin" class="text-center my-3">Hoặc</div>

      <!-- Nút đăng nhập Google (chỉ hiện khi Đăng nhập) -->
      <button
        v-if="isLogin"
        class="btn btn-outline-danger w-100 d-flex align-items-center justify-content-center"
        @click="loginWithGoogle"
      >
        <img
          src="https://www.svgrepo.com/show/475656/google-color.svg"
          alt="Google"
          width="20"
          class="me-2"
        />
        Đăng nhập với Google
      </button>

      <p class="mt-3 text-center">
        {{ isLogin ? "Chưa có tài khoản?" : "Đã có tài khoản?" }}
        <a href="#" @click.prevent="toggleForm">
          {{ isLogin ? "Đăng ký" : "Đăng nhập" }}
        </a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const isLogin = ref(true);
const username = ref("");
const email = ref("");
const password = ref("");
const confirmpass = ref("");

const handleSubmit = () => {
  if (isLogin.value) {
    alert("Đăng nhập thành công!");
  } else {
    alert("Đăng ký thành công!");
  }
};

const loginWithGoogle = () => {
  alert("Redirect đến Google OAuth...");
};

const toggleForm = () => {
  isLogin.value = !isLogin.value;
};
</script>
