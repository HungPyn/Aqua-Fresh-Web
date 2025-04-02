<template>
  <div class="container-fluid">
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div
        class="container-fluid d-flex align-items-center justify-content-between px-4"
      >
        <!-- Logo bên trái -->
        <a
          class="navbar-brand fw-bold fs-5 d-flex align-items-center"
          href="/home"
        >
          <img
            src="../img/tayCamLogo.jpg"
            alt="Logo"
            width="40"
            height="40"
            class="d-inline-block align-text-top me-2"
          />
          ProController
        </a>

        <!-- Chữ chạy ở giữa -->
        <div class="flex-grow-1 text-center">
          <h5 v-if="!showSearchBox" class="marquee text-primary">
            Chào mừng đến với cửa hàng tay cầm chơi game! 🎮
          </h5>
        </div>

        <!-- Form tìm kiếm bên phải -->
        <div class="d-flex align-items-center ms-auto" v-if="showSearchBox">
          <div class="d-flex align-items-center me-4">
            <img
              src="../img/shipperwebp.webp"
              alt="Shipper"
              width="25"
              height="25"
              class="me-2"
            />
            <p class="mb-0">| Giao hàng tận nơi.</p>
          </div>

          <div class="d-flex align-items-center me-4">
            <img
              src="../img/thanhtoan.webp"
              alt="Thanh toán"
              width="25"
              height="25"
              class="me-2"
            />
            <p class="mb-0">| Thanh toán tại nhà.</p>
          </div>

          <a
            href="https://www.facebook.com/vuong04321"
            class="link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover"
          >
            <img
              src="../img/2021_Facebook_icon.svg.png"
              alt="Facebook"
              width="24"
              height="24"
              class="me-2"
            />
            Facebook
          </a>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const showSearchBox = ref(false);

onMounted(() => {
  setTimeout(() => {
    showSearchBox.value = true;
  }, 10000);
});
</script>

<style>
.bg-gray {
  background-color: #6c757d; /* Màu nền navbar phù hợp */
}

.marquee {
  white-space: nowrap;
  overflow: hidden;
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  background: linear-gradient(90deg, #ff00ff, #00ffff, #ff9900, #ff00ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: 2px 2px 5px rgba(255, 255, 255, 0.3);
  animation: marquee 10s linear forwards;
  animation-fill-mode: forwards;
}

@keyframes marquee {
  from {
    transform: translateX(200%);
    opacity: 1;
  }
  to {
    transform: translateX(-110%);
    opacity: 0;
  }
}
</style>
