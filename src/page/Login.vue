<template>
  <div class="container">
    <div class="row" v-show="!dangKy">
      <h2 class="text-center">Đăng nhập</h2>
      <br />
      <hr />
      <div class="col-4"></div>
      <div class="col-4">
        <form>
          <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Username</label>
            <input
              v-model="username"
              required
              type="username"
              class="form-control"
              id="exampleInputUsername1"
              aria-describedby="emailHelp"
            />
            <div id="emailHelp" class="form-text"></div>
          </div>
          <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label"
              >Password</label
            >
            <input
              required
              v-model="password"
              type="password"
              class="form-control"
              id="exampleInputPassword1"
            />
          </div>
          <div v-show="baoLoi" class="text-center text-danger">
            <label>Sai tài khoản hoặc mật khẩu</label>
          </div>
          <br />

          <div class="text-center">
            <button @click.prevent="dangNhap" class="btn btn-primary">
              Đăng Nhập
            </button>
            <br />
            <br />
            <a @click="setDangKy" href="#">Đăng ký</a>
          </div>
        </form>
      </div>
      <div class="col-4"></div>
    </div>
    <div class="row" v-show="dangKy">
      <h2 class="text-center">Đăng Ký</h2>
      <br />
      <hr />
      <div class="col-4"></div>
      <div class="col-4">
        <form>
          <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Username</label>
            <input
              v-model="taiKhoan.username"
              required
              type="username"
              class="form-control"
              id="exampleInputUsername1"
              aria-describedby="emailHelp"
            />
            <div id="emailHelp" class="form-text"></div>
          </div>
          <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label"
              >Password</label
            >
            <input
              required
              v-model="taiKhoan.password"
              type="password"
              class="form-control"
              id="exampleInputPassword1"
            />
          </div>
          <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label"
              >Confirm password</label
            >
            <input
              required
              v-model="taiKhoan.confirmPassword"
              type="password"
              class="form-control"
              id="exampleInputPassword1"
            />
          </div>
          <div v-show="tbConfirmPassword" class="text-center text-danger">
            <label>Mật khẩu không trùng khớp</label>
          </div>
          <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Full name</label>
            <input
              v-model="taiKhoan.fullName"
              required
              type="username"
              class="form-control"
              id="exampleInputUsername1"
              aria-describedby="emailHelp"
            />
            <div id="emailHelp" class="form-text"></div>
          </div>
          <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Email</label>
            <input
              v-model="taiKhoan.email"
              required
              type="email"
              class="form-control"
              id="exampleInputUsername1"
              aria-describedby="emailHelp"
            />
            <div id="emailHelp" class="form-text"></div>
          </div>

          <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label"
              >Số điện thoại</label
            >
            <input
              v-model="taiKhoan.phoneNumber"
              required
              type="text"
              class="form-control"
              id="exampleInputUsername1"
              aria-describedby="emailHelp"
            />
            <div id="emailHelp" class="form-text"></div>
          </div>
          <div v-show="tbPhoneNumber" class="text-center text-danger">
            <label>Số điện thoại sai!</label>
          </div>

          <br />

          <div class="text-center">
            <button @click.prevent="dangKyTaiKhoan" class="btn btn-primary">
              Đăng Ký
            </button>
            <button
              v-if="!isShow"
              type="button"
              class="btn btn-secondary d-inline-block mx-2"
              @click="previousDangNhap()"
            >
              <i class="fas fa-arrow-left"></i> Hủy
            </button>
            <br />
            <br />
          </div>
        </form>
      </div>
      <div class="col-4"></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

import { useToast } from "vue-toastification";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";

const toast = useToast();
const baoLoi = ref(false);
const dangKy = ref(false);
const setDangKy = () => {
  dangKy.value = true;
};
const previousDangNhap = () => {
  dangKy.value = false;
  taiKhoan.value = {
    username: "",
    password: "",
    confirmPassword: "",
    fullName: "",
    email: "",
    phoneNumber: "",
  };
  tbConfirmPassword.value = false;
  tbPhoneNumber.value = false;
};
// đnag ký tài khoản
const taiKhoan = ref({
  username: "",
  password: "",
  confirmPassword: "",
  fullName: "",
  email: "",
  phoneNumber: "",
});
const tbConfirmPassword = ref(false);
const tbPhoneNumber = ref(false);
const router = useRouter();

const dangKyTaiKhoan = async () => {
  if (taiKhoan.value.password !== taiKhoan.value.confirmPassword) {
    tbConfirmPassword.value = true;
    return;
  } else {
    tbConfirmPassword.value = false;
  }
  if (
    taiKhoan.value.phoneNumber.length > 11 ||
    taiKhoan.value.phoneNumber.length < 1
  ) {
    tbPhoneNumber.value = true;
    return;
  } else {
    tbPhoneNumber.value = false;
  }
  if (
    taiKhoan.value.username.length < 1 ||
    taiKhoan.value.password.length < 1 ||
    taiKhoan.value.fullName.length < 1 ||
    taiKhoan.value.email.length < 1 ||
    taiKhoan.value.phoneNumber.length < 1
  ) {
    toast.error("Vui lòng nhập đầy đủ thông tin!");
    return;
  }
  try {
    const res = await axios.post(
      "http://localhost:8080/auth/register",
      taiKhoan.value
    );
    console.log(res.data);
    toast.success("Đăng ký thành công!");
    dangKy.value = false;
    taiKhoan.value = {
      username: "",
      password: "",
      confirmPassword: "",
      fullName: "",
      email: "",
      phoneNumber: "",
    };
  } catch (error) {
    console.error(error);
    toast.error("Đăng ký thất bại!");
  }
};

const username = ref("");
const password = ref("");
const userLogin = ref({
  username: "",
  password: "",
});
const user = ref({});

const dangNhap = async () => {
  userLogin.value.username = username.value;
  userLogin.value.password = password.value;
  try {
    const res = await axios.post(
      "http://localhost:8080/auth/login",
      userLogin.value
    );
    const token = res.data;
    localStorage.setItem("token", token);
    console.log("Đã lưu token");
    getUser();
    baoLoi.value = false;
    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        console.log("Payload đã giải mã:", decodedToken);
        const userRole = decodedToken.authorities[0];
        sessionStorage.setItem("userRole", userRole);
        console.log("Vai trò người dùng:", userRole);
        if (userRole === "ROLE_ADMIN") {
          localStorage.setItem("role", "admin");
          // Làm gì đó cho admin
          location.href = "/discountAdmin";
        } else if (userRole === "ROLE_USER") {
          localStorage.setItem("role", "user");
          // Làm gì đó cho người dùng thông thường
          location.href = "/home";
        } else {
          toast.error("Lỗi k rõ role");
        }
      } catch (error) {
        console.error("Lỗi khi giải mã token:", error);
        toast.error("Lỗi");
      }
    } else {
      console.log("Không tìm thấy token.");
      toast.error("Lỗi");
    }
  } catch (error) {
    baoLoi.value = true;
    console.log("Status:", error.response.status);
    console.log("Data:", error.response.data);
  }
};
const getUser = async () => {
  const token = localStorage.getItem("token");
  if (token) {
    try {
      const res = await axios.get("http://localhost:8080/auth/me", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      user.value = res.data;
      sessionStorage.setItem("user", JSON.stringify(res.data));
      console.log("đã lưu user", JSON.stringify(user.value, null, 2));
    } catch (error) {
      console.error(error);
    }
  } else {
    console.log("Không tìm thấy token.");
  }
};
</script>
