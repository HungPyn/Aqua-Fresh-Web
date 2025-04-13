<template>
  <div class="container-fluid px-5">
    <!-- Tiêu đề -->
    <div class="text-center bg-light rounded-3 shadow-sm py-3 mb-4">
      <h4 class="fw-bold text-dark m-0">Danh Sách Mã Giảm Giá</h4>
    </div>

    <div class="row">
      <!-- Bộ lọc -->
      <div class="col-lg-2 col-md-4 mb-4">
        <div class="border p-3 bg-white shadow-sm rounded">
          <input
            class="form-control mb-3"
            placeholder="Tìm kiếm.."
            v-model="duLieu"
            @keyup.enter="timKiem"
            @input="timKiem"
          />
        </div>
      </div>

      <!-- Bảng danh sách -->
      <div class="col-lg-7 col-md-8">
        <table class="table table-bordered table-hover text-center">
          <thead class="table-success">
            <tr>
              <th>STT</th>
              <th>Tên</th>
              <th>Giá trị</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày hết hạn</th>
              <th>Mô tả</th>
              <th>Trạng thái</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(dc, index) in discounts"
              :key="dc.id"
              @click="detailAdd(dc)"
            >
              <td>{{ index + 1 }}</td>
              <td>{{ dc.name }}</td>
              <td>{{ dc.discountValue }}</td>
              <td>{{ dc.startDate }}</td>
              <td>{{ dc.endDate }}</td>
              <td>{{ dc.description }}</td>
              <td
                class="text-warning"
                v-if="
                  new Date(dc.startDate).toISOString().split('T')[0] >
                  new Date().toISOString().split('T')[0]
                "
              >
                <b>Đang chờ</b>
              </td>
              <td
                class="text-success"
                v-else-if="
                  new Date(dc.endDate).toISOString().split('T')[0] >=
                  new Date().toISOString().split('T')[0]
                "
              >
                <b>Đang hoạt động</b>
              </td>
              <td class="text-danger" v-else><b>Hết hạn</b></td>

              <td>
                <button
                  @click.stop="detailUpdate(dc)"
                  class="btn btn-sm btn-success me-2"
                >
                  <i class="fas fa-edit"></i> Sửa
                </button>
                <button
                  @click.stop="deleteDiscount(dc.id)"
                  class="btn btn-sm btn-warning"
                >
                  <i class="fas fa-trash-alt"></i> Xóa
                </button>
              </td>
            </tr>
            <tr v-if="discounts.length === 0">
              <td colspan="8" class="text-center text-danger">
                <b>Không tìm thấy kết quả</b>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Form nhập liệu -->
      <div class="col-lg-3">
        <div class="card p-3 shadow">
          <h5 class="text-center fw-bold">
            {{ isShow ? "Thêm mã giảm giá" : "Cập nhật mã giảm giá" }}
          </h5>
          <form>
            <div class="row">
              <div class="col-md-12 mb-3">
                <label class="form-label">Tên</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="newDiscount.name"
                  required
                />
              </div>
              <div class="col-md-12 mb-3">
                <label class="form-label">Mô tả</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="newDiscount.description"
                  required
                />
              </div>

              <div class="col-md-6 mb-3">
                <label class="form-label">Giá trị</label>
                <input
                  type="number"
                  class="form-control"
                  v-model="newDiscount.discountValue"
                  min="5"
                  max="100"
                  step="5"
                  required
                />
              </div>
              <div class="col-md-6 mb-3">
                <label class="form-label">Ngày bắt đầu</label>
                <input
                  type="date"
                  class="form-control"
                  v-model="newDiscount.startDate"
                  required
                />
              </div>
              <div class="col-md-6 mb-3">
                <label class="form-label">Ngày hết hạn</label>
                <input
                  type="date"
                  class="form-control"
                  v-model="newDiscount.endDate"
                  required
                />
              </div>
            </div>

            <!-- Căn giữa nút -->
            <div class="text-center mt-3">
              <button
                v-if="!isShow"
                type="button"
                class="btn btn-secondary d-inline-block mx-2"
                @click="previousAdd()"
              >
                <i class="fas fa-arrow-left"></i> Hủy
              </button>

              <button
                v-if="isShow"
                type="button"
                class="btn btn-primary d-inline-block mx-2"
                @click="addDiscount()"
              >
                <i class="fas fa-plus"></i> Thêm mã
              </button>

              <button
                v-else
                type="button"
                class="btn btn-success d-inline-block mx-2"
                @click="updateDiscount()"
              >
                <i class="fas fa-save"></i> Cập nhật
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";

//detail add
const detailAdd = (dc) => {
  newDiscount.value = { ...dc };
};

//
const toast = useToast();
//tro ve
const previousAdd = () => {
  isShow.value = true;
  resetDiscount();
};

//v show update
const isShow = ref(true);

//dicountdicount

const discounts = ref([]);
const newDiscount = ref({
  id: null,
  name: "",
  discountValue: 5,
  startDate: null,
  endDate: null,
  description: "",
  isActive: true,
});

const resetDiscount = () => {
  newDiscount.value = {
    id: null,
    name: "",
    discountValue: 5,
    startDate: null,
    endDate: null,
    description: "",
    isActive: true,
  };
};
//chek usser
const getUserFromSession = () => {
  const storedUser = sessionStorage.getItem("user");
  user.value = storedUser ? JSON.parse(storedUser) : null;
};
const user = ref(null);
const isLogin = computed(() => !!user.value);

// Hàm lấy danh sách mã giảm giá
const getDiscounts = async () => {
  try {
    const response = await axios.get("http://localhost:8080/admin/discount", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      withCredentials: true,
    });
    discounts.value = response.data;
  } catch (error) {
    console.error("Lỗi khi laays disscount:", error);
  }
};
const detailUpdate = (discount) => {
  newDiscount.value = { ...discount };
  isShow.value = false;
};

const isActive = ref(true); // Trạng thái hoạt động
//thêm
const addDiscount = async () => {
  if (!newDiscount.value) {
    toast.error("Dữ liệu giảm giá không hợp lệ!");
    return;
  }
  const { id, ...discountData } = newDiscount.value;
  discountData.isActive = isActive.value;
  if (
    !discountData.name.trim() ||
    !discountData.description.trim() ||
    !discountData.discountValue == null ||
    !discountData.startDate ||
    !discountData.endDate
  ) {
    toast.error("Vui lòng nhập đầy đủ thông tin giảm giá!");
    return;
  }

  if (
    typeof discountData.discountValue !== "number" ||
    discountData.discountValue < 5
  ) {
    toast.error("Giá trị phải là số và lớn hơn hoặc bằng 5!");
    return;
  }
  const startDate = new Date(discountData.startDate);
  const endDate = new Date(discountData.endDate);

  if (endDate <= startDate) {
    toast.error(
      `Ngày hết hạn (${discountData.endDate}) phải sau ngày áp dụng (${discountData.startDate})!`
    );
    return;
  }

  // Thêm hộp thoại xác nhận trước khi thêm
  const result = await Swal.fire({
    title: "Xác nhận thêm mã giảm giá?",
    icon: "info", // Icon bình thường (không phải cảnh báo)
    showCancelButton: true,
    confirmButtonText: "Thêm",
    cancelButtonText: "Hủy",
  });

  if (!result.isConfirmed) return;

  try {
    const response = await axios.post(
      "http://localhost:8080/admin/discount/create",
      discountData,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        withCredentials: true,
      }
    );

    toast.success("Thêm giảm giá thành công!");
    console.log("Thêm giảm giá thành công:", response.data);

    resetDiscount();
    timKiem();
  } catch (error) {
    console.error("Lỗi khi thêm giảm giá:", error);
    toast.error("Đã xảy ra lỗi khi thêm giảm giá!");
  }
};

const updateDiscount = async () => {
  if (!newDiscount.value) {
    toast.error("Dữ liệu giảm giá không hợp lệ!");
    return;
  }
  const { ...discountData } = newDiscount.value;
  discountData.isActive = isActive.value;
  if (
    !discountData.name.trim() ||
    !discountData.description.trim() ||
    !discountData.discountValue == null ||
    !discountData.startDate ||
    !discountData.endDate
  ) {
    toast.error("Vui lòng nhập đầy đủ thông tin giảm giá!");
    return;
  }

  if (
    typeof discountData.discountValue !== "number" ||
    discountData.discountValue < 5
  ) {
    toast.error("Giá trị phải là số và lớn hơn hoặc bằng 5!");
    return;
  }
  const startDate = new Date(discountData.startDate);
  const endDate = new Date(discountData.endDate);

  if (endDate <= startDate) {
    toast.error(
      `Ngày hết hạn (${discountData.endDate}) phải sau ngày áp dụng (${discountData.startDate})!`
    );
    return;
  }

  Swal.fire({
    title: "Bạn có chắc muốn sửa không?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Có",
    cancelButtonText: "Không",
  }).then(async (result) => {
    // Cần async ở đây vì có await bên trong
    if (result.isConfirmed) {
      console.log("Update là: sauwr:", JSON.stringify(discountData, null, 2));
      const token = localStorage.getItem("token");
      console.log("JWT Token:", token);
      try {
        const response = await axios.put(
          `http://localhost:8080/admin/discount/update?id=${discountData.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            withCredentials: true,
          }
        );

        toast.success("Sửa mã giảm giá thành công!");
        console.log("Sửa mã giảm giá thành công:", response.data);

        resetDiscount();
        timKiem();
        isShow.value = true;
      } catch (error) {
        console.error("Lỗi khi sửa mã giá:", error);
        toast.error("Đã xảy ra lỗi khi sửa mã giảm giá!");
      }
    }
  });
};

const deleteDiscount = async (id) => {
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
        await axios.delete(
          `http://localhost:8080/admin/discount/delete/${id}`,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
            withCredentials: true,
          }
        );
        toast.success("Xóa thành công!");
        timKiem(); // Load lại danh sách sau khi xóa
      } catch (error) {
        console.error("Lỗi khi xóa giảm giá:", error);
        console.error(
          "Lỗi khi xóa giảm giá:",
          error.response ? error.response.data : error
        );
        toast.error("Xóa thất bại!");
      }
    }
  });
};

// tim kiem
const duLieu = ref("");
const trangThai = ref("ALL");
const timKiem = async () => {
  try {
    const res = await axios("http://localhost:8080/admin/discount/search", {
      params: {
        keyword: duLieu.value,
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      withCredentials: true,
    });
    console.log(res.data);
    discounts.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tim discount", error);
  }
};

// Gọi API khi component được render
onMounted(() => {
  getDiscounts(), getUserFromSession();
});
</script>

<style scoped>
.table td,
.table th {
  max-width: 150px; /* Giới hạn chiều rộng của ô */
  overflow: hidden; /* Ẩn phần dư thừa */
  text-overflow: ellipsis; /* Hiển thị dấu "..." nếu nội dung quá dài */
  white-space: nowrap; /* Ngăn không cho chữ xuống dòng */
}
</style>
