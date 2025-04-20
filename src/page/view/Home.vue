<template>
  <div class="container-fluid p-0">
    <nav
      class="navbar navbar-expand-lg py-3"
      style="background-color: #f8f8ff; position: sticky; top: 0; z-index: 1000"
    >
      <div class="container">
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

        <div class="collapse navbar-collapse" id="navbarNav">
          <!-- Menu NAV ADMIN -->
          <ul
            v-if="userRole === 'ROLE_ADMIN' && isLogin"
            class="navbar-nav me-auto"
          >
            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/product-admin">
                Sản phẩm
              </router-link>
            </li>

            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle fw-bold fs-6"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
              >
                Quản lý chi tiết
              </a>
              <ul class="dropdown-menu">
                <li>
                  <router-link to="/product-detail-admin" class="dropdown-item">
                    Sản phẩm chi tiết
                  </router-link>
                </li>
                <li>
                  <router-link to="/company-admin" class="dropdown-item">
                    Hãng sản xuất (Công ty)
                  </router-link>
                </li>
                <li>
                  <router-link to="/category-admin" class="dropdown-item">
                    Phân loại (Category)
                  </router-link>
                </li>
                <li>
                  <router-link to="/color-admin" class="dropdown-item">
                    Màu sắc
                  </router-link>
                </li>
              </ul>
            </li>

            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/donHangAdmin">
                Đơn hàng
              </router-link>
            </li>

            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/hoaDonAdmin">
                Hóa đơn
              </router-link>
            </li>

            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/discountAdmin">
                Khuyến mãi
              </router-link>
            </li>

            <li class="nav-item">
              <router-link class="nav-link fw-bold fs-6" to="/customer-admin">
                Khách hàng
              </router-link>
            </li>
          </ul>

          <!-- Menu GUEST hoặc ROLE_USER -->
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

          <!-- TÌM KIẾM: chỉ hiển thị khi không phải admin -->
          <form v-if="userRole !== 'ROLE_ADMIN'" class="d-flex mx-auto w-50">
            <input
              @keydown.enter.prevent="timKiem()"
              v-model="noiDung"
              class="form-control me-2"
              type="search"
              placeholder="Tìm kiếm..."
              aria-label="Search"
            />
          </form>

          <!-- ICON GIỎ HÀNG + TÀI KHOẢN -->
          <ul class="navbar-nav ms-auto d-flex align-items-center">
            <!-- Giỏ hàng: ẩn khi là admin -->
            <li
              class="nav-item ms-3 position-relative"
              v-if="userRole !== 'ROLE_ADMIN'"
            >
              <router-link to="/gioHang">
                <img
                  src="../../img/gioHang.png"
                  alt="Giỏ hàng"
                  width="30"
                  height="35"
                  class="d-inline-block align-text-top"
                />
                <span v-if="soLuongGioHang > 0" class="cart-badge">{{
                  soLuongGioHang
                }}</span>
              </router-link>
            </li>

            <!-- Tài khoản -->
            <li class="nav-item ms-3">
              <router-link
                v-if="!isLogin"
                class="nav-link fw-bold fs-6"
                to="/login"
                style="text-decoration: none; color: black"
              >
                <h6>Đăng nhập</h6>
              </router-link>

              <div v-if="isLogin" class="dropdown">
                <button
                  class="btn btn-outline-dark dropdown-toggle"
                  type="button"
                  data-bs-toggle="dropdown"
                >
                  Xin chào, {{ user.username }}
                </button>
                <ul class="dropdown-menu">
                  <router-link
                    v-if="userRole === 'ROLE_USER'"
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
    <!-- Liên hệ -->
    <div class="contact-button">
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
      <a href="https://www.facebook.com/vuong04321" target="_blank">
        <img
          src="../../img/2021_Facebook_icon.svg.png"
          alt="Facebook"
          class="img-fluid rounded-circle"
        />
      </a>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();
const user = ref(JSON.parse(sessionStorage.getItem("user") || "null"));
const isLogin = computed(() => !!user.value);
const userRole = ref(sessionStorage.getItem("userRole") || null);
const noiDung = ref("");
const soLuongGioHang = ref(0);

const timKiem = () => {
  if (noiDung.value.trim()) {
    if (router.currentRoute.name !== "timKiem") {
      router.push({ name: "timKiem", query: { q: noiDung.value } });
    }
  }
};

const dangXuat = async () => {
  const result = await Swal.fire({
    title: "Bạn có chắc muốn đăng xuất?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Đăng xuất",
    cancelButtonText: "Hủy",
  });

  if (result.isConfirmed) {
    sessionStorage.clear();
    localStorage.removeItem("token");
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
</script>

<style scoped>
.nav-link.fs-6 {
  font-size: 0.875rem;
  white-space: nowrap;
}
form .form-control {
  width: 300px;
}
.navbar-nav.ms-auto {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 150px;
}
.nav-item {
  margin-right: 20px;
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
