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
            src="../img/logo.png"
            alt="Logo"
            width="40"
            height="40"
            class="d-inline-block align-text-top me-2"
          />
          maylocnuoc.com
        </a>

        <!-- Form tìm kiếm bên phải -->
        <div class="d-flex align-items-center ms-auto">
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
</script>

<style>
.bg-gray {
  background-color: #6c757d; /* Màu nền navbar phù hợp */
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
