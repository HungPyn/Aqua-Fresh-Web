<template>
  <div class="container">
    <h2 class="text-center">Đăng nhập</h2>
    <hr />
    <div class="row">
      <div class="col-4"></div>
      <div class="col-4">
        <form>
          <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Username</label>
            <input
              v-model="user.username"
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
              v-model="user.password"
              type="password"
              class="form-control"
              id="exampleInputPassword1"
            />
          </div>
          <div class="text-center">
            <button @click.prevent="dangNhap" class="btn btn-primary">
              Đăng Nhập
            </button>
            |
            <button type="submit" class="btn btn-primary" @click="themTaiKhoan">
              Đăng ký
            </button>
          </div>
        </form>
      </div>
      <div class="col-4"></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import router from "@/router/router";
import { useToast } from "vue-toastification";

const toast = useToast();
const userLogin = ref({
  username: "",
  password: "",
});
const user = ref({});

const dangNhap = async () => {
  try {
    const res = await post("http://localhost:8080/auth/login", userLogin.value);
    const token = res.data;
    localStorage.setItem("token", token);
    console.log("token là:", token);
    console.log(res.data);
    toast.success("Đăng nhập thành công!");
  } catch {
    toast.error("Đăng nhập thâtts bại!");
    console.log("Lỗi đăng nhập");
  }
};
</script>
