<script setup></script>
<template>
  <div class="container mt-4">
    <div class="text-center bg-light rounded-3 shadow-sm py-3 mb-4">
      <h4 class="fw-bold text-dark m-0">Danh Sách người dùng</h4>
    </div>
    <div class="row">
      <!-- Bộ lọc (col-4) -->
      <div class="col-md-4">
        <h5 class="mb-3">Bộ lọc</h5>
        <input
          class="form-control mb-2"
          placeholder="Tìm kiếm.."
          v-model="duLieu"
          @keyup.enter="timKiem"
          @input="timKiem"
        />
        <select class="form-control" @change="timKiem" v-model="vaiTro">
          <option value="">-- Chọn vai trò --</option>
          <option value="admin">Admin</option>
          <option value="user">User</option>
        </select>

        <br />
        <div class="card p-3 shadow-sm">
          <h5 class="mb-3">Thông tin chi tiết</h5>
          <form>
            <div class="mb-3 d-flex align-items-center">
              <label for="username" class="form-label w-25"
                >Tên người dùng</label
              >
              <input
                type="text"
                class="form-control"
                id="username"
                v-model="newUser.username"
                disabled
              />
            </div>

            <div class="mb-3 d-flex align-items-center">
              <label for="email" class="form-label w-25">Email</label>
              <input
                type="email"
                class="form-control"
                id="email"
                v-model="newUser.email"
                disabled
              />
            </div>

            <div class="mb-3 d-flex align-items-center">
              <label for="phone" class="form-label w-25">Số điện thoại</label>
              <input
                type="text"
                class="form-control"
                id="phone"
                v-model="newUser.phone"
                disabled
              />
            </div>

            <div class="mb-3 d-flex align-items-center">
              <label for="role" class="form-label w-25">Vai trò</label>
              <input
                type="text"
                class="form-control"
                id="role"
                v-model="newUser.role"
                disabled
              />
            </div>

            <div class="mb-3 d-flex align-items-center">
              <label for="createAt" class="form-label w-25">Ngày tạo</label>
              <input
                type="text"
                class="form-control"
                id="createAt"
                v-model="newUser.createAt"
                disabled
              />
            </div>
          </form>
        </div>
      </div>

      <!-- Danh sách user (col-8) -->
      <div class="col-md-8">
        <div class="card p-3 shadow-sm">
          <h5 class="mb-3">Danh Sách Người Dùng</h5>
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th>#</th>
                <th>Tên</th>
                <th>Email</th>
                <th>Điện thoại</th>
                <th>Vai trò</th>
                <th>Ngày tạo</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(user, index) in users"
                :key="user.id"
                @click="detail(user)"
              >
                <td>{{ index + 1 }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.email }}</td>
                <td>{{ user.phone }}</td>
                <td>
                  <b>{{ user.role }}</b>
                </td>
                <td>{{ new Date(user.createdAt).toLocaleDateString() }}</td>
                <td>
                  <button
                    type="button"
                    class="btn btn-warning"
                    @click="deleteUser(user.id)"
                  >
                    xóa
                  </button>
                </td>
              </tr>
              <tr v-if="users.length === 0">
                <td colspan="6" class="text-center text-danger">
                  <b>Không tìm thấy kết quả</b>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";

const toast = useToast();

const users = ref([]);

const newUser = ref({
  id: "",
  username: "",
  email: "",
  phone: "",
  role: "",
  createAt: "",
});
const detail = (user) => {
  newUser.value = {
    createAt: new Date(user.createdAt).toLocaleDateString(),
    ...user,
  };
};

const getUser = async () => {
  try {
    const res = await axios("http://localhost:8080/users/hien-thi");
    console.log(res.data);
    users.value = res.data;
  } catch (error) {
    console.error("Lỗi khi gọi API lấy ussers:", error);
  }
};

const deleteUser = (id) => {
  if (!id) {
    toast.error("ID không hợp lệ!");
    return;
  }

  Swal.fire({
    title: "Bạn có muốn xóa không?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Có",
    cancelButtonText: "Không",
  }).then(async (result) => {
    // Cần async ở đây vì có await bên trong
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/users/delete/${id}`);
        toast.success("Xóa thành công!");
        timKiem(); // Load lại danh sách sau khi xóa
      } catch (error) {
        console.error("Lỗi khi xóa:", error);
        toast.error("Xóa thất bại!");
      }
    }
  });
};

onMounted(getUser);

// tim kiem
const duLieu = ref("");
const vaiTro = ref("");
const timKiem = async () => {
  try {
    const res = await axios("http://localhost:8080/users/search", {
      params: {
        duLieu: duLieu.value,
        vaiTro: vaiTro.value,
      },
    });
    console.log(res.data);
    users.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tim user", error);
  }
};
</script>
