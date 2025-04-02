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
          <h5 class="mb-3 text-start">Bộ lọc</h5>
          <input
            class="form-control mb-3"
            placeholder="Tìm kiếm.."
            v-model="duLieu"
            @keyup.enter="timKiem"
            @input="timKiem"
          />
          <label class="form-label">Trạng thái</label>
          <select
            class="form-control mb-3"
            v-model="trangThai"
            @change="timKiem"
          >
            <option value="ALL">Tất cả</option>
            <option value="EXPIRED">Hết hạn</option>
            <option value="INACTIVE">Đang chờ</option>
            <option value="ACTIVE">Đang hoạt động</option>
          </select>
        </div>
      </div>

      <!-- Bảng danh sách -->
      <div class="col-lg-7 col-md-8">
        <table class="table table-bordered table-hover text-center">
          <thead class="table-success">
            <tr>
              <th>STT</th>
              <th>Mã code</th>
              <th>Số lượng</th>
              <th>Phần trăm</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày hết hạn</th>
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
              <td>{{ dc.code }}</td>
              <td>{{ dc.quantity }}</td>
              <td>{{ dc.percentage }}%</td>
              <td>{{ dc.validFrom }}</td>
              <td>{{ dc.validTo }}</td>
              <td
                class="text-warning"
                v-if="
                  new Date(dc.validFrom).toISOString().split('T')[0] >
                  new Date().toISOString().split('T')[0]
                "
              >
                <b>Đang chờ</b>
              </td>
              <td
                class="text-success"
                v-else-if="
                  new Date(dc.validTo).toISOString().split('T')[0] >=
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
                <label class="form-label">Mã code</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="newDiscount.code"
                  required
                />
              </div>
              <div class="col-md-6 mb-3">
                <label class="form-label">Số lượng</label>
                <input
                  type="number"
                  class="form-control"
                  v-model="newDiscount.quantity"
                  min="5"
                  max="100"
                  step="5"
                  required
                />
              </div>
              <div class="col-md-6 mb-3">
                <label class="form-label">Phần trăm (%)</label>
                <input
                  type="number"
                  class="form-control"
                  v-model="newDiscount.percentage"
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
                  v-model="newDiscount.validFrom"
                  required
                />
              </div>
              <div class="col-md-6 mb-3">
                <label class="form-label">Ngày hết hạn</label>
                <input
                  type="date"
                  class="form-control"
                  v-model="newDiscount.validTo"
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
import { ref, onMounted } from "vue";
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
  quantity: 1,
  code: "",
  percentage: 5,
  validFrom: null,
  validTo: null,
  createAt: new Date().toISOString().split("T")[0],
});
const discountUpdate = ref({
  id: null,
  quantity: 1,
  code: "",
  percentage: 5,
  validFrom: null,
  validTo: null,
  createAt: null,
});
const resetDicountUpdate = () => {
  discountUpdate.value = {
    id: null,
    quantity: 1,
    code: "",
    percentage: 5,
    validFrom: null,
    validTo: null,
    createAt: null,
  };
};
const resetDiscount = () => {
  newDiscount.value = {
    id: null,
    quantity: 1,
    code: "",
    percentage: 5,
    validFrom: null,
    validTo: null,
    createAt: new Date().toISOString().split("T")[0],
  };
};

// Hàm lấy danh sách mã giảm giá
const getDiscounts = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/discounts/hien-thi"
    );
    console.log(response.data);
    discounts.value = response.data;
  } catch (error) {
    console.error("Lỗi khi gọi API lấy discount:", error);
  }
};
const detailUpdate = (discount) => {
  newDiscount.value = { ...discount };
  discountUpdate.value.createAt = discount.createAt;
  isShow.value = false;
};
// check mã
const iscode = ref(false);
const checkCode = async () => {
  const code = newDiscount.value.code?.trim();
  const id = newDiscount.value.id || null;
  console.log("Giá trị trước khi gửi:", { code, id });
  if (!code) return;

  console.log("Gửi kiểm tra mã với:", { code, id }); // Debug

  try {
    const response = await axios.get(
      "http://localhost:8080/discounts/checkCode",
      {
        params: { code, id },
      }
    );

    // Kiểm tra giá trị API trả về
    iscode.value = response.data === true;
    console.log("Mã có tồn tại không:", iscode.value);
  } catch (error) {
    console.error("Lỗi khi gọi API kiểm tra mã giảm giá:", error);
    iscode.value = false; // Mặc định không có lỗi
  }
};
const checkCodeAdd = async () => {
  const code = newDiscount.value.code?.trim();
  const id = -1;
  console.log("Giá trị trước khi gửi:", { code, id });
  if (!code) return;

  console.log("Gửi kiểm tra mã với:", { code, id }); // Debug

  try {
    const response = await axios.get(
      "http://localhost:8080/discounts/checkCode",
      {
        params: { code, id },
      }
    );

    // Kiểm tra giá trị API trả về
    iscode.value = response.data === true;
    console.log("Mã có tồn tại không:", iscode.value);
  } catch (error) {
    console.error("Lỗi khi gọi API kiểm tra mã giảm giá:", error);
    iscode.value = false; // Mặc định không có lỗi
  }
};
//thêm
const addDiscount = async () => {
  console.log(
    "Dữ liệu trước khi gửi:",
    JSON.stringify(newDiscount.value, null, 2)
  );

  if (!newDiscount.value) {
    toast.error("Dữ liệu giảm giá không hợp lệ!");
    return;
  }
  await checkCodeAdd(); // Gọi API kiểm tra mã trước
  if (iscode.value) {
    // Nếu mã đã tồn tại, dừng lại
    toast.error("Mã giảm giá đã tồn tại, vui lòng nhập mã khác!");
    return;
  }

  const { id, ...discountData } = newDiscount.value;

  if (
    !discountData.code.trim() ||
    discountData.percentage == null ||
    discountData.quantity == null ||
    !discountData.validFrom ||
    !discountData.validTo ||
    !discountData.createAt
  ) {
    toast.error("Vui lòng nhập đầy đủ thông tin giảm giá!");
    return;
  }

  if (!Number.isInteger(discountData.quantity) || discountData.quantity < 1) {
    toast.error("Số lượng phải là số nguyên và lớn hơn hoặc bằng 1!");
    return;
  }

  if (
    typeof discountData.percentage !== "number" ||
    discountData.percentage < 5
  ) {
    toast.error("Phần trăm giảm giá phải là số và lớn hơn hoặc bằng 5!");
    return;
  }

  const createDate = new Date(discountData.createAt);
  const validFromDate = new Date(discountData.validFrom);
  const validToDate = new Date(discountData.validTo);

  if (validFromDate < createDate) {
    toast.error(
      `Ngày áp dụng (${discountData.validFrom}) phải sau hoặc bằng ngày tạo (${discountData.createAt})!`
    );
    return;
  }

  if (validToDate <= validFromDate) {
    toast.error(
      `Ngày hết hạn (${discountData.validTo}) phải sau ngày áp dụng (${discountData.validFrom})!`
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
      "http://localhost:8080/discounts/add",
      discountData
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
  discountUpdate.value = {
    ...JSON.parse(JSON.stringify(newDiscount.value)),
    createAt: discountUpdate.value.createAt,
  };
  console.log(
    "Dữ liệu trước khi gửi update:",
    JSON.stringify(discountUpdate.value, null, 2)
  );

  if (!discountUpdate.value) {
    toast.error("Dữ liệu giảm giá không hợp lệ!");
    return;
  }
  await checkCode(); // Gọi API kiểm tra mã trước

  if (iscode.value) {
    // Nếu mã đã tồn tại, dừng lại
    toast.error("Mã giảm giá đã tồn tại, vui lòng nhập mã khác!");
    return;
  }

  const { id, ...discountData } = discountUpdate.value;

  // Kiểm tra các trường không được null, rỗng hoặc không hợp lệ
  if (
    !discountData.code.trim() ||
    discountData.percentage == null ||
    discountData.quantity == null ||
    !discountData.validFrom ||
    !discountData.validTo ||
    !discountData.createAt
  ) {
    toast.error("Vui lòng nhập đầy đủ thông tin giảm giá!");
    return;
  }

  // Kiểm tra quantity phải là số nguyên >= 1
  if (!Number.isInteger(discountData.quantity) || discountData.quantity < 1) {
    toast.error("Số lượng phải là số nguyên và lớn hơn hoặc bằng 1!");
    return;
  }

  // Kiểm tra percentage phải là số và >= 5
  if (
    typeof discountData.percentage !== "number" ||
    discountData.percentage < 5
  ) {
    toast.error("Phần trăm giảm giá phải là số và lớn hơn hoặc bằng 5!");
    return;
  }

  const createDate = new Date(discountData.createAt);
  const validFromDate = new Date(discountData.validFrom);
  const validToDate = new Date(discountData.validTo);

  // Kiểm tra ngày áp dụng phải sau hoặc bằng ngày tạo
  if (validFromDate < createDate) {
    toast.error(
      `Ngày áp dụng (${discountData.validFrom}) phải sau hoặc bằng ngày tạo (${discountData.createAt})!`
    );
    return;
  }

  // Kiểm tra ngày hết hạn phải sau ngày áp dụng
  if (validToDate <= validFromDate) {
    toast.error(
      `Ngày hết hạn (${discountData.validTo}) phải sau ngày áp dụng (${discountData.validFrom})!`
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
      try {
        const response = await axios.put(
          "http://localhost:8080/discounts/update",
          discountUpdate.value
        );

        toast.success("Sửa mã giảm giá thành công!");
        console.log("Sửa mã giảm giá thành công:", response.data);

        resetDicountUpdate();
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
        await axios.delete(`http://localhost:8080/discounts/delete/${id}`);
        toast.success("Xóa thành công!");
        timKiem(); // Load lại danh sách sau khi xóa
      } catch (error) {
        console.error("Lỗi khi xóa giảm giá:", error);
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
    const res = await axios("http://localhost:8080/discounts/search", {
      params: {
        duLieu: duLieu.value,
        trangThai: trangThai.value,
      },
    });
    console.log(res.data);
    discounts.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tim discount", error);
  }
};

// Gọi API khi component được render
onMounted(getDiscounts);
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
