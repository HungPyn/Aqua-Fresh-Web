<template>
  <div class="container-fluid">
    <div class="container mt-5">
      <h1 class="text-center mb-4">🛒 Giỏ hàng của bạn</h1>

      <!-- Thông báo nếu giỏ hàng trống -->
      <div v-if="cart.length === 0" class="alert alert-info text-center">
        Giỏ hàng của bạn hiện tại trống.
        <router-link class="fw-bold fs-6" to="/home#tatCa"
          >Mua sắm ngay!</router-link
        >
      </div>

      <!-- Danh sách sản phẩm trong giỏ hàng -->
      <div
        v-for="(item, index) in cart"
        :key="item.id"
        class="card p-3 mb-3 shadow-sm"
        @click="select(item)"
      >
        <div class="row align-items-center">
          <!-- Checkbox chọn sản phẩm -->
          <div class="col-md-1 text-center">
            <input
              class="form-check-input"
              type="checkbox"
              v-model="chonSanPham"
              :value="item"
              :id="'checkbox-' + index"
              style="transform: scale(1.5); cursor: pointer"
            />
          </div>
          <!-- Hình ảnh sản phẩm -->
          <div class="col-md-2 text-center">
            <img
              :src="item.product.image"
              class="img-fluid rounded"
              alt="Product Image"
            />
          </div>

          <!-- Thông tin sản phẩm -->
          <div class="col-md-4">
            <router-link
              :to="'/productDetail/' + item.product.id"
              class="text-decoration-none text-dark hover-effect"
              style="
                padding: 0 !important; /* Loại bỏ padding nếu không cần thiết */
                margin: 0 !important; /* Loại bỏ margin nếu không cần thiết */
                display: inline-block; /* Đảm bảo phần tử chỉ chiếm diện tích cần thiết */
              "
              ><h5 class="card-title mb-1">
                {{ item.product.name }}
              </h5></router-link
            >

            <p class="card-text mb-0">
              <strong>Giá: </strong>
              <b class="text-danger"
                >{{ Number(item.price).toLocaleString("vi-VN") }} vnđ</b
              >
            </p>
          </div>

          <!-- Hiển thị tổng tiền của từng sản phẩm -->
          <div class="col-md-2 text-center">
            <b> Tổng: </b>
            <b class="text-danger"
              >{{
                Number(item.price * item.quantity).toLocaleString("vi-VN")
              }}
              vnđ</b
            >
          </div>

          <!-- Nút giảm/tăng số lượng -->
          <div class="col-md-2 text-center">
            <div class="input-group" style="width: 130px; padding: 0px">
              <button
                class="btn btn-outline-secondary"
                type="button"
                @click="giamSoLuong(item)"
                @mouseleave="updateSoLuong(item)"
              >
                -
              </button>
              <input
                type="number"
                class="form-control text-center fw-bold"
                v-model="item.quantity"
                readonly
              />
              <button
                class="btn btn-outline-secondary"
                type="button"
                @click="tangSoLuong(item)"
                @mouseleave="updateSoLuong(item)"
              >
                +
              </button>
            </div>
          </div>

          <!-- Nút xóa -->
          <div class="col-md-1 text-center">
            <button class="btn btn-sm btn-danger" @click="xoaGioHang(item.id)">
              🗑️ Xóa
            </button>
          </div>
        </div>
      </div>

      <!-- Tổng giá trị giỏ hàng -->
      <div class="text-end mt-3 border-top pt-3 ms-5">
        <!-- Checkbox "Chọn tất cả" -->
        <div class="mt-3 text-start">
          <input
            type="checkbox"
            v-model="chonTatCa"
            style="transform: scale(2); margin-right: 15px"
          />
          <label>Chọn tất cả</label>
        </div>
        <h4>
          Tổng cộng:
          <b class="text-danger"
            >{{ Number(tongCong).toLocaleString("vi-VN") }} vnđ</b
          >
        </h4>
        <button @click="thanhToan" class="btn btn-success mt-2">
          💳 Thanh toán
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { computed, onMounted, ref, watch } from "vue";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";

const toast = useToast();

const chonTatCa = ref(false);
watch(chonTatCa, (newVal) => {
  if (newVal) {
    chonSanPham.value = [...cart.value]; // Chọn tất cả sản phẩm
  } else {
    chonSanPham.value = []; // Bỏ chọn tất cả sản phẩm
  }
});
//chon sanpham khi click vaof div
const select = (gh) => {
  // Kiểm tra xem sản phẩm đã có trong chonSanPham chưa
  const index = chonSanPham.value.findIndex((item) => item.id === gh.id);

  if (index !== -1) {
    // Nếu sản phẩm đã có, xóa nó khỏi chonSanPham
    chonSanPham.value.splice(index, 1);
  } else {
    // Nếu sản phẩm chưa có, thêm nó vào chonSanPham
    chonSanPham.value.push(gh);
  }
};
///
const tangSoLuong = (gh) => {
  if (gh.quantity >= 1) {
    gh.quantity++;
  }
};
const giamSoLuong = (gh) => {
  if (gh.quantity > 1) {
    gh.quantity--;
  }
};
const updateSoLuong = async (gh) => {
  const gioHang = {
    id: gh.id,
    user: gh.user,
    product: gh.product,
    price: gh.price,
    quantity: gh.quantity,
    createdAt: gh.createdAt,
    quantity: gh.quantity,
  };
  console.log(
    "Dữ liệu gior hangf trước khi gửi:",
    JSON.stringify(gioHang, null, 2)
  );
  try {
    const res = await axios.put("http://localhost:8080/cart/update", gioHang);
    console.log(res.data);
    getCart();
  } catch (error) {
    console.error("Lỗi:", error.response ? error.response.data : error);
  }
};

const cart = ref([]);
//fakse id user
const userId = "894de7e6-12c8-4387-94ad-05396cca268d";

const getCart = async () => {
  try {
    const res = await axios.get(
      "http://localhost:8080/cart/hien-thi/" + userId
    );
    console.log(res.data);
    cart.value = res.data;
  } catch (error) {
    console.error("Lỗi:", error.response ? error.response.data : error);
  }
};
//xóa
const xoaGioHang = async (id) => {
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
        await axios.delete(`http://localhost:8080/cart/delete/${id}`);
        getCart();
      } catch (error) {
        console.error("Lỗi:", error.response ? error.response.data : error);
      }
    }
  });
};
// thanh toán
const chonSanPham = ref([]);
watch(chonSanPham, (newVal) => {
  console.log("Sản phẩm đã chọn:", JSON.parse(JSON.stringify(newVal)));
});
const tongCong = computed(() => {
  return chonSanPham.value.reduce((total, item) => {
    return total + (item.price || 0) * (item.quantity || 0);
  }, 0);
});
const thanhToan = async () => {};

onMounted(getCart);
</script>
<style scoped>
.card:hover {
  background-color: #c4cfd9; /* Màu nền xám nhạt */
  transition: background-color 0.3s ease;
}
.hover-effect {
  color: black; /* Giữ màu đen */
}
.hover-effect:hover h5 {
  text-decoration: underline;
}
</style>
