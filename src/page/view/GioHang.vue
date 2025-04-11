<template>
  <div class="container-fluid">
    <div class="container mt-5">
      <h1 class="text-center mb-4">Giỏ hàng</h1>

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
              :src="item.idProductDetail.listUrl[0].urlImage"
              class="img-fluid rounded"
              style="width: 70%; height: auto"
              alt="Product Image"
            />
          </div>

          <!-- Thông tin sản phẩm -->
          <div class="col-md-4">
            <router-link
              :to="'/productDetail/' + item.idProductDetail.id"
              class="text-decoration-none text-dark hover-effect"
              style="
                padding: 0 !important; /* Loại bỏ padding nếu không cần thiết */
                margin: 0 !important; /* Loại bỏ margin nếu không cần thiết */
                display: inline-block; /* Đảm bảo phần tử chỉ chiếm diện tích cần thiết */
              "
              ><h6 class="card-title mb-1">
                {{ item.idProductDetail.idProduct.productName }}
              </h6></router-link
            >

            <p class="card-text mb-0">
              <strong>Giá: </strong>

              <b
                v-if="item.idProductDetail.idDiscount.discountValue > 0"
                class="text-muted text-decoration-line-through"
                >{{
                  Number(item.idProductDetail.price).toLocaleString("vi-VN")
                }}đ
              </b>
              <b
                v-if="item.idProductDetail.idDiscount.discountValue > 0"
                class="text-danger"
                >{{
                  Number(
                    item.idProductDetail.price -
                      item.idProductDetail.idDiscount.discountValue
                  ).toLocaleString("vi-VN")
                }}đ</b
              >

              <b
                v-if="item.idProductDetail.idDiscount.discountValue === 0"
                class="text-danger"
                >{{
                  Number(item.idProductDetail.price).toLocaleString("vi-VN")
                }}
                đ</b
              >
            </p>
            <br />
            <p class="card-text mb-0" style="font-size: 0.8rem">
              <b>Ngày thêm: </b
              >{{
                new Date(item.dateAdded).toLocaleString("vi-VN", {
                  hour12: false,
                })
              }}
            </p>
          </div>

          <!-- Hiển thị tổng tiền của từng sản phẩm -->
          <div class="col-md-2 text-center">
            <b> Tổng: </b>
            <b
              class="text-danger"
              v-if="item.idProductDetail.idDiscount.discountValue === 0"
              >{{
                Number(
                  item.idProductDetail.price * item.quantity
                ).toLocaleString("vi-VN")
              }}đ</b
            >
            <b
              class="text-danger"
              v-if="item.idProductDetail.idDiscount.discountValue > 0"
              >{{
                Number(
                  (item.idProductDetail.price -
                    item.idProductDetail.idDiscount.discountValue) *
                    item.quantity
                ).toLocaleString("vi-VN")
              }}đ</b
            >
          </div>

          <!-- Nút giảm/tăng số lượng -->
          <div class="col-md-2 text-center">
            <div class="input-group" style="width: 130px; padding: 0px">
              <button
                class="btn btn-outline-secondary"
                type="button"
                @click.stop="giamSoLuong(item)"
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
                @click.stop="tangSoLuong(item)"
                @mouseleave="updateSoLuong(item)"
              >
                +
              </button>
            </div>
          </div>

          <!-- Nút xóa -->
          <div class="col-md-1 text-center">
            <button
              class="btn btn-sm btn-danger rounded-0"
              @click="xoaGioHang(item)"
            >
              Xóa
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
            >{{ Number(tongCong).toLocaleString("vi-VN") }}đ</b
          >
        </h4>
        <button
          @click="thanhToan"
          data-bs-toggle="modal"
          data-bs-target="#modalThanhToan"
          class="btn btn-success mt-2 rounded-0"
        >
          Thanh toán
        </button>
      </div>
    </div>

    <!-- Modal Thanh Toán -->
    <div class="modal fade" id="modalThanhToan" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Xác nhận thanh toán</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div class="modal-body">
            <p>Bạn có chắc muốn thanh toán các sản phẩm đã chọn?</p>
          </div>

          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Hủy
            </button>
            <button type="button" class="btn btn-primary">Xác nhận</button>
          </div>
        </div>
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
//chon sanpham khi click vao div
const select = (gh) => {
  // Kiểm tra xem sản phẩm đã có trong chonSanPham chưa
  const index = chonSanPham.value.findIndex(
    (item) => item.idProductDetail.id === gh.idProductDetail.id
  );

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
  if (isLogin.value) {
    // const gioHang = {
    //   id: gh.id,
    //   user: gh.user,
    //   product: gh.product,
    //   price: gh.price,
    //   quantity: gh.quantity,
    //   createdAt: gh.createdAt,
    //   quantity: gh.quantity,
    // };
    // console.log(
    //   "Dữ liệu gior hangf trước khi gửi:",
    //   JSON.stringify(gioHang, null, 2)
    // );
    // try {
    //   const res = await axios.put("http://localhost:8080/cart/update", gioHang);
    //   console.log(res.data);
    //   getCart();
    // } catch (error) {
    //   console.error("Lỗi:", error.response ? error.response.data : error);
    // }
  } else {
    const sessionCart = JSON.parse(sessionStorage.getItem("cart")) || [];
    const index = sessionCart.findIndex(
      (item) => item.idProductDetail.id === gh.idProductDetail.id
    );
    if (index !== -1) {
      sessionCart[index].quantity = gh.quantity;
      sessionStorage.setItem("cart", JSON.stringify(sessionCart));
    }
  }
};
const cart = ref([]);

const userId = "894de7e6-12c8-4387-94ad-05396cca268d";
//chek usser
const getUserFromSession = () => {
  const storedUser = sessionStorage.getItem("user");
  user.value = storedUser ? JSON.parse(storedUser) : null;
};
const user = ref(null);
const isLogin = computed(() => !!user.value);
//lấy ra giỏ hàng

const getCart = async () => {
  if (isLogin.value) {
  } else {
    const sessionCart = JSON.parse(sessionStorage.getItem("cart")) || [];
    cart.value = sessionCart;
  }
};
//xóa
const xoaGioHang = async (gh) => {
  if (isLogin.value) {
    // Swal.fire({
    //   title: "Bạn có muốn xóa không?",
    //   icon: "warning",
    //   showCancelButton: true,
    //   confirmButtonText: "Có",
    //   cancelButtonText: "Không",
    // }).then(async (result) => {
    //   // Cần async ở đây vì có await bên trong
    //   if (result.isConfirmed) {
    //     try {
    //       await axios.delete(`http://localhost:8080/cart/delete/${id}`);
    //       getCart();
    //     } catch (error) {
    //       console.error("Lỗi:", error.response ? error.response.data : error);
    //     }
    //   }
    // });
  } else {
    Swal.fire({
      title: "Bạn có muốn xóa không?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Có",
      cancelButtonText: "Không",
    }).then(async (result) => {
      // Cần async ở đây vì có await bên trong
      if (result.isConfirmed) {
        const sessionCart = JSON.parse(sessionStorage.getItem("cart")) || [];
        const index = sessionCart.findIndex(
          (item) => item.idProductDetail.id === gh.idProductDetail.id
        );
        if (index !== -1) {
          sessionCart.splice(index, 1); // Xóa sản phẩm khỏi giỏ hàng
          sessionStorage.setItem("cart", JSON.stringify(sessionCart)); // Cập nhật giỏ hàng trong sessionStorage
          getCart(); // Cập nhật lại giỏ hàng hiển thị trên giao diện
        }
      }
    });
  }
};
// list để đã chọn để thanh
const chonSanPham = ref([]);
///////////////////////////////////////////////////////////////
watch(chonSanPham, (newVal) => {
  console.log("Sản phẩm đã chọn:", JSON.parse(JSON.stringify(newVal)));
});
const tongCong = computed(() => {
  return chonSanPham.value.reduce((total, item) => {
    const detail = item.idProductDetail || {};
    const discount = detail.idDiscount?.discountValue || 0;
    const price = detail.price || 0;
    const quantity = item.quantity || 0;

    return total + (price - discount) * quantity;
  }, 0);
});
// thanh toán
const thanhToan = async () => {};

onMounted(() => {
  getUserFromSession();
  getCart();
});
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
