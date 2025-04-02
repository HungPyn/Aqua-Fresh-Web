<template>
  <div class="container-fluid p-0">
    <nav
      class="navbar navbar-expand-lg py-3 shadow-sm"
      style="background-color: #f8f8ff"
    >
      <div class="container">
        <!-- Nút toggle khi thu nhỏ -->
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Nội dung Navbar -->
        <div class="collapse navbar-collapse" id="navbarNav">
          <!-- Danh sách menu -->
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link
                class="nav-link fs-6 px-3"
                to="/donHangAdmin"
                :class="{ active: isActive('/donHangAdmin') }"
              >
                Đơn hàng
              </router-link>
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link fs-6 px-3"
                to="/hoaDonAdmin"
                :class="{ active: isActive('/hoaDonAdmin') }"
              >
                Hóa đơn
              </router-link>
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link fs-6 px-3"
                to="/discountAdmin"
                :class="{ active: isActive('/discountAdmin') }"
              >
                Khuyến mãi
              </router-link>
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link fs-6 px-3"
                to="/productAdmin"
                :class="{ active: isActive('/productAdmin') }"
              >
                Sản phẩm
              </router-link>
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link fs-6 px-3"
                to="/userAdmin"
                :class="{ active: isActive('/userAdmin') }"
              >
                Khách hàng
              </router-link>
            </li>
          </ul>

          <!-- Ô tìm kiếm lớn lùi sang phải -->
          <form class="d-flex ms-auto w-50">
            <input
              class="form-control form-control-lg me-2"
              type="search"
              placeholder="Tìm kiếm..."
            />
            <button class="btn btn-dark btn-lg" type="submit">🔍</button>
          </form>
        </div>
      </div>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";

const route = useRoute();
const isActive = (path) => route.path === path;
</script>

<style>
/* Hover nhẹ nhàng */
.nav-link {
  transition: color 0.3s ease, background-color 0.3s ease;
}
.nav-link:hover {
  background-color: rgba(0, 123, 255, 0.1);
  border-radius: 5px;
}

/* Trang đang chọn - nền xám đậm */
.nav-link.active {
  font-weight: bold;
  color: white !important;
  background-color: #6c757d !important;
  border-radius: 5px;
  padding: 8px 12px;
}

/* Ô tìm kiếm to và căn phải */
.form-control-lg {
  height: 40px;
  font-size: 16px;
}
.btn-lg {
  padding: 8px 12px;
  font-size: 16px;
}
</style>
