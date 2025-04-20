<script setup>
import { ref, onMounted } from "vue";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";
import CustomerService from "@/services/CustomerService"; // Đổi đúng path nếu khác

const toast = useToast();
const users = ref([]);
const searchValue = ref("");

const newUser = ref({
  id: null,
  username: "",
  email: "",
  phone: "",
  role: "USER", // hoặc ADMIN nếu có
});

const isEdit = ref(false);

const getUsers = async () => {
  users.value = await CustomerService.getAllCustomers();
};

const search = async () => {
  if (!searchValue.value.trim()) return getUsers();
  try {
    users.value = await CustomerService.searchCustomers(searchValue.value);
  } catch (error) {
    toast.error("Lỗi tìm kiếm");
  }
};

const openModal = (user = null) => {
  if (user) {
    newUser.value = { ...user };
    isEdit.value = true;
  } else {
    newUser.value = {
      id: null,
      username: "",
      email: "",
      phone: "",
      role: "USER",
    };
    isEdit.value = false;
  }
  const modal = new bootstrap.Modal(document.getElementById("userModal"));
  modal.show();
};

const saveUser = async () => {
  try {
    if (isEdit.value) {
      await CustomerService.updateCustomer(newUser.value.id, newUser.value);
      toast.success("Cập nhật thành công");
    } else {
      await CustomerService.createCustomer(newUser.value);
      toast.success("Tạo mới thành công");
    }
    getUsers();
    bootstrap.Modal.getInstance(document.getElementById("userModal")).hide();
  } catch (error) {
    toast.error("Lỗi khi lưu người dùng");
  }
};

const deleteUser = async (id) => {
  const confirm = await Swal.fire({
    title: "Bạn có chắc chắn muốn xoá?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Xoá",
    cancelButtonText: "Huỷ",
  });
  if (confirm.isConfirmed) {
    try {
      await CustomerService.deleteCustomer(id);
      toast.success("Xoá thành công");
      getUsers();
    } catch (error) {
      toast.error("Không thể xoá");
    }
  }
};

onMounted(getUsers);
</script>

<template>
  <div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h4 class="text-primary fw-bold">Quản lý người dùng</h4>
      <button class="btn btn-success" @click="openModal()">+ Thêm mới</button>
    </div>

    <div class="mb-3">
      <input
        type="text"
        class="form-control"
        v-model="searchValue"
        placeholder="Tìm kiếm theo tên..."
        @input="search"
      />
    </div>

    <div class="card shadow-sm">
      <table class="table table-bordered table-hover mb-0">
        <thead class="table-light">
          <tr>
            <th>#</th>
            <th>Tên</th>
            <th>Email</th>
            <th>Điện thoại</th>
            <th>Vai trò</th>
            <th>Ngày tạo</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(user, index) in users" :key="user.id">
            <td>{{ index + 1 }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.phone }}</td>
            <td>
              <span class="badge bg-info text-dark">{{ user.role }}</span>
            </td>
            <td>{{ new Date(user.createdAt).toLocaleDateString() }}</td>
            <td>
              <button
                class="btn btn-sm btn-primary me-1"
                @click="openModal(user)"
              >
                Sửa
              </button>
              <button
                class="btn btn-sm btn-danger"
                @click="deleteUser(user.id)"
              >
                Xoá
              </button>
            </td>
          </tr>
          <tr v-if="users.length === 0">
            <td colspan="7" class="text-center text-danger">
              Không có kết quả.
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="userModal" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <form @submit.prevent="saveUser">
            <div class="modal-header bg-primary text-white">
              <h5 class="modal-title">
                {{ isEdit ? "Cập nhật" : "Thêm mới" }} người dùng
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
              ></button>
            </div>
            <div class="modal-body">
              <div class="mb-2">
                <label class="form-label">Tên người dùng</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="newUser.username"
                  required
                />
              </div>
              <div class="mb-2">
                <label class="form-label">Email</label>
                <input
                  type="email"
                  class="form-control"
                  v-model="newUser.email"
                  required
                />
              </div>
              <div class="mb-2">
                <label class="form-label">Số điện thoại</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="newUser.phone"
                />
              </div>
              <div class="mb-2">
                <label class="form-label">Vai trò</label>
                <select class="form-select" v-model="newUser.role">
                  <option value="USER">USER</option>
                  <option value="ADMIN">ADMIN</option>
                </select>
              </div>
            </div>
            <div class="modal-footer">
              <button class="btn btn-secondary" data-bs-dismiss="modal">
                Đóng
              </button>
              <button type="submit" class="btn btn-primary">
                {{ isEdit ? "Cập nhật" : "Tạo mới" }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
