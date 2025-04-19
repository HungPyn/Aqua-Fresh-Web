<template>
  <div class="container-fluid p-0">
    <nav
      class="navbar navbar-expand-lg py-3"
      style="background-color: #f8f8ff; position: sticky; top: 0; z-index: 1000"
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
          <!-- Danh sách menu bên trái -->
          <ul
            v-if="userRole === 'ROLE_ADMIN' && isLogin"
            class="navbar-nav me-auto"
          >
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/donHangAdmin">
                Đơn hàng
              </router-link>
            </li>

            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/discountAdmin">
                Khuyến mãi
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/productAdmin">
                Sản phẩm
              </router-link>
            </li>
          </ul>
          <ul
            v-if="userRole === 'ROLE_USER' || !isLogin"
            class="navbar-nav me-auto"
          >
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/home">
                Trang chủ
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/home#tatCa">
                Tất cả
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/home#tuDung">
                Tủ đứng
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/home#deBan">
                Để bàn
              </router-link>
            </li>

            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/home#amBon">
                Âm bồn
              </router-link>
            </li>
          </ul>

          <!-- Ô tìm kiếm căn giữa -->
          <form class="d-flex mx-auto w-50">
            <input
              @keydown.enter.prevent="timKiem()"
              v-model="noiDung"
              class="form-control me-2"
              type="search"
              placeholder="Tìm kiếm..."
              aria-label="Search"
            />
          </form>

          <!-- Biểu tượng giỏ hàng và yêu thích bên phải -->
          <ul class="navbar-nav ms-auto d-flex align-items-center">
            <li class="nav-item ms-3 position-relative">
              <router-link to="/gioHang">
                <img
                  src="../../img/gioHang.png"
                  alt="Giỏ hàng"
                  width="30"
                  height="35"
                  class="d-inline-block align-text-top"
                />
                <!-- Hiển thị số lượng nếu lớn hơn 0 -->
                <span v-if="soLuongGioHang > 0" class="cart-badge">{{
                  soLuongGioHang
                }}</span>
              </router-link>
            </li>

            <li class="nav-item ms-3">
              <!-- Nếu chưa đăng nhập -->
              <router-link
                v-if="!isLogin"
                class="nav-link fw-bold fs-6"
                to="/login"
                style="text-decoration: none; color: black"
              >
                <h6>Đăng nhập</h6>
              </router-link>

              <!-- Nếu đã đăng nhập -->
              <div v-if="isLogin" class="dropdown">
                <button
                  class="btn btn-outline-dark dropdown-toggle"
                  type="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  Xin chào, {{ user.username }}
                </button>

                <ul class="dropdown-menu">
                  <router-link
                    v-if="userRole == 'ROLE_USER'"
                    to="/donHang"
                    class="dropdown-item btn btn-link text-start"
                  >
                    Quản lý đơn hàng
                  </router-link>
                  <li>
                    <button class="dropdown-item" @click="dangXuat">
                      Đăng xuất
                    </button>
                  </li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <router-view></router-view>

    <div>
      <!-- Nút liên hệ Zalo và Facebook -->
      <div class="contact-button">
        <!-- Nút Zalo -->
        <a
          href="https://zaloapp.com/qr/p/1utj308vmcoxs"
          target="_blank"
          class="d-block mb-2"
        >
          <img
            src="../../img/Icon_of_Zalo.svg.png"
            alt="Zalo"
            class="img-fluid rounded-circle"
          />
        </a>

        <!-- Nút Facebook -->
        <a href="" target="_blank">
          <img
            src="../../img/2021_Facebook_icon.svg.png"
            alt="Facebook"
            class="img-fluid rounded-circle"
          />
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import Controllers from "./Controllers.vue";
import { useRouter } from "vue-router"; // Import useRouter
import Swal from "sweetalert2";

const router = useRouter(); // Sử dụng useRouter để lấy đối tượng router

const user = ref(JSON.parse(sessionStorage.getItem("user") || "null"));

const isLogin = computed(() => !!user.value);
const userRole = ref(sessionStorage.getItem("userRole") || null);
//dang xuatxuat
const dangXuat = async () => {
  console.log("user la", user.value);
  console.log("userRole la", JSON.stringify(userRole.value, null, 2));

  // Hiển thị hộp thoại xác nhận
  const result = await Swal.fire({
    title: "Bạn có chắc muốn đăng xuất?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Đăng xuất",
    cancelButtonText: "Hủy",
  });

  if (result.isConfirmed) {
    sessionStorage.removeItem("user");
    localStorage.removeItem("token");
    sessionStorage.removeItem("cart");
    user.value = null;

    await Swal.fire({
      title: "Đăng xuất thành công!",
      icon: "success",
      timer: 1500,
      showConfirmButton: false,
    });

    location.href = "/login";
  }
};

const noiDung = ref("");
const timKiem = () => {
  if (noiDung.value.trim()) {
    if (router.currentRoute.name !== "timKiem") {
      router.push({ name: "timKiem", query: { q: noiDung.value } });
    }
  }
};

const soLuongGioHang = ref(0);
</script>

<style scoped>
/* Thêm lớp CSS để giảm kích thước chữ cho các menu */
.nav-link.fs-6 {
  font-size: 0.875rem; /* Giảm kích thước chữ */
  white-space: nowrap; /* Đảm bảo không xuống dòng */
}

/* Tạo lớp cho các menu khi đang chọn và đổi màu nền thành xám */
.nav-link.bg-secondary {
  background-color: #6c757d !important;
  color: white !important;
}

/* Thay đổi kích thước ô tìm kiếm */
form .form-control {
  width: 300px; /* Rút ngắn chiều rộng ô tìm kiếm */
}

/* Căn chỉnh giỏ hàng và yêu thích cách đều đẹp */
.navbar-nav.ms-auto {
  display: flex;
  align-items: center;
  justify-content: space-between; /* Căn đều */
  width: 150px; /* Giới hạn chiều rộng của thanh biểu tượng */
}

.nav-item {
  margin-right: 20px; /* Tạo khoảng cách đều giữa các biểu tượng */
}
.contact-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.contact-button img {
  width: 50px;
  height: 50px;
  transition: transform 0.3s ease;
}

/* Hiệu ứng khi hover */
.contact-button:hover img {
  transform: scale(1.1);
}
.cart-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: red;
  color: white;
  font-size: 12px;
  font-weight: bold;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
}
</style>
