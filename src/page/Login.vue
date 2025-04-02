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
              v-model="newTaiKhoan.username"
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
              v-model="newTaiKhoan.password"
              type="password"
              class="form-control"
              id="exampleInputPassword1"
            />
          </div>
          <div class="text-center">
            <button
              type="submit"
              @click.prevent="dangNhap"
              class="btn btn-primary"
            >
              Đăng Nhập
            </button>
            |
            <button
              type="submit"
              class="btn btn-primary"
              @click.prevent="themTaiKhoan"
            >
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

const taiKhoans = ref([
  {
    id: 0,
    username: "vuong",
    password: "123",
  },
]);
const newTaiKhoan = ref({
  id: "",
  username: "",
  password: "",
});

const reset = () => {
  newTaiKhoan.value = {
    id: "",
    username: "",
    password: "",
  };
};

const themTaiKhoan = () => {
  const existingAccount = taiKhoans.value.find(
    (t) => t.username === newTaiKhoan.value.username
  );

  if (existingAccount) {
    alert("Tài khoản đã tồn tại. Vui lòng đăng nhập!");
  } else {
    // Thêm tài khoản mới vào danh sách
    taiKhoans.value.push({
      id: taiKhoans.value.length + 1,
      ...newTaiKhoan.value,
    });
    alert("Tạo tài khoản thành công! Xin mời đăng nhập.");
    reset();
    router.push("/taiKhoan");
  }
};

// Đănnhập
const dangNhap = () => {
  const account = taiKhoans.value.find(
    (t) =>
      t.username === newTaiKhoan.value.username &&
      t.password === newTaiKhoan.value.password
  );

  if (account) {
    alert("Đăng nhập thành công!");
    router.push("/home");
  } else {
    alert("Username hoặc password không đúng");
  }
};
</script>
