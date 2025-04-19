<template>
  <div class="container mt-4">
    <h3>Quản lý đơn hàng</h3>

    <!-- Tìm kiếm -->
    <div class="input-group mb-3 mt-3">
      <input
        v-model="searchPhone"
        @keyup.enter="searchOrder"
        type="text"
        class="form-control"
        placeholder="Tìm theo số điện thoại..."
      />
      <button class="btn btn-primary" @click="searchOrder">Tìm kiếm</button>
      <button class="btn btn-secondary ms-2" @click="fetchOrders">
        Tất cả
      </button>
    </div>

    <!-- Bảng đơn hàng -->
    <table class="table table-bordered table-hover mt-3">
      <thead class="table-light">
        <tr>
          <th>ID</th>
          <th>Khách hàng</th>
          <th>SĐT</th>
          <th>Ngày đặt</th>
          <th>Tổng tiền</th>
          <th>Phí ship</th>
          <th>Trạng thái</th>
          <th>Thao tác</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.idUser?.name }}</td>
          <td>{{ order.idUser?.phone }}</td>
          <td>{{ formatDate(order.oderDate) }}</td>
          <td>{{ formatCurrency(order.total) }}</td>
          <td>{{ formatCurrency(order.shippingPrice) }}</td>
          <td>
            <div v-if="order.status === 'CHỜ_XỬ_LÝ'">
              <select v-model="order.status" class="form-select form-select-sm">
                <option value="CHỜ_XỬ_LÝ">Chờ xử lý</option>
                <option value="ĐANG_GIAO">Đang giao</option>
                <option value="HOÀN_THÀNH">Hoàn thành</option>
                <option value="HUỶ">Đã huỷ</option>
              </select>
            </div>
            <div v-else>{{ order.status.replaceAll("_", " ") }}</div>
          </td>
          <td>
            <template v-if="order.status === 'CHỜ_XỬ_LÝ'">
              <button
                class="btn btn-success btn-sm me-2"
                @click="updateStatus(order)"
              >
                Cập nhật
              </button>
              <button
                class="btn btn-danger btn-sm"
                @click="deleteOrder(order.id)"
              >
                Xóa
              </button>
            </template>
            <template v-else>
              <button
                class="btn btn-outline-primary btn-sm"
                @click="viewOrder(order)"
                title="Xem chi tiết"
              >
                👁
              </button>
            </template>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useToast } from "vue-toastification";
import OrderService from "@/services/OrderTableService.js";

const toast = useToast();
const orders = ref([]);
const searchPhone = ref("");

// Lấy tất cả đơn hàng
const fetchOrders = async () => {
  try {
    const data = await OrderService.getAll();
    orders.value = data;
  } catch (err) {
    toast.error("Lỗi khi tải danh sách đơn hàng");
    console.error(err);
  }
};

// Tìm kiếm đơn hàng theo SĐT
const searchOrder = async () => {
  if (!searchPhone.value.trim()) {
    fetchOrders();
    return;
  }
  try {
    const data = await OrderService.searchByPhone(searchPhone.value);
    orders.value = data;
  } catch (err) {
    toast.error("Không tìm thấy đơn hàng");
    console.error(err);
  }
};

// Xóa đơn hàng
const deleteOrder = async (id) => {
  if (!confirm("Bạn có chắc muốn xóa đơn hàng này?")) return;
  try {
    await OrderService.delete(id);
    toast.success("Xóa đơn hàng thành công");
    fetchOrders();
  } catch (err) {
    toast.error("Xóa thất bại");
    console.error(err);
  }
};

// Cập nhật trạng thái đơn hàng
const updateStatus = async (order) => {
  try {
    await OrderService.update(order.id, order);
    toast.success("Cập nhật trạng thái thành công");
    fetchOrders();
  } catch (err) {
    toast.error("Cập nhật thất bại");
    console.error(err);
  }
};

// Xem chi tiết đơn hàng
const viewOrder = (order) => {
  toast.info(`Xem chi tiết đơn hàng #${order.id}`);
  // Nếu có router detail page thì có thể dùng:
  // router.push(`/admin/orders/${order.id}`)
};

// Định dạng ngày
const formatDate = (isoString) => {
  const date = new Date(isoString);
  return date.toLocaleDateString("vi-VN");
};

// Định dạng tiền tệ
const formatCurrency = (value) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(value || 0);
};

onMounted(() => {
  fetchOrders();
});
</script>

<style scoped>
.container {
  max-width: 1200px;
}

h3 {
  font-weight: bold;
  margin-bottom: 1rem;
  color: #0d6efd;
}

.table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  font-size: 0.95rem;
}

thead th {
  background-color: #f8f9fa;
  text-align: center;
}

td,
th {
  vertical-align: middle;
  text-align: center;
}

select.form-select-sm {
  min-width: 140px;
}

input.form-control {
  border-radius: 8px;
  box-shadow: none;
  transition: border-color 0.3s;
}

input.form-control:focus {
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.15rem rgba(13, 110, 253, 0.25);
}

button {
  transition: background-color 0.3s, transform 0.2s;
}

button:hover {
  transform: scale(1.03);
}

.btn-success {
  background-color: #28a745;
  border-color: #28a745;
}

.btn-success:hover {
  background-color: #218838;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-danger:hover {
  background-color: #c82333;
}
</style>
