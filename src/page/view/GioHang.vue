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
              @click.stop="xoaGioHang(item)"
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
          v-if="chonSanPham.length > 0"
          data-bs-toggle="modal"
          data-bs-target="#modalThanhToan"
          class="btn btn-success mt-2 rounded-0"
        >
          Thanh toán
        </button>
      </div>
    </div>

    <!-- Modal Thanh Toán/////////////////////////////////////////////////////////////////////////////////////////////////////// -->
    <div class="modal fade" id="modalThanhToan" tabindex="-1">
      <div class="modal-dialog modal-xl">
        <div class="modal-content rounded-0">
          <div class="modal-header">
            <h5 class="modal-title">Xác nhận thanh toán</h5>
            <button
              type="button"
              class="btn-close"
              @click="handleClose"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div class="modal-body">
            <div class="row">
              <div class="col-7">
                <div
                  style="background-color: aliceblue"
                  v-for="item in chonSanPham"
                  :key="item.id"
                  class="p-2 mb-2 shadow-sm"
                >
                  <div class="row align-items-center">
                    <!-- Hình ảnh và tên sản phẩm -->
                    <div class="col-md-3 text-center">
                      <img
                        :src="item.idProductDetail.listUrl[0].urlImage"
                        class="img-fluid rounded"
                        style="max-height: 100px; width: auto"
                        alt="Product Image"
                      />
                    </div>
                    <div class="col-md-9">
                      <h6
                        class="card-title mb-0 fs-6"
                        style="font-size: 0.9rem"
                      >
                        {{ item.idProductDetail.idProduct.productName }}
                      </h6>

                      <!-- Số lượng và tổng tiền ngang hàng -->
                      <div
                        class="col-md-12 text-center mt-2 d-flex justify-content-between"
                        style="padding-left: 0px; padding-right: 0px"
                      >
                        <!-- Số lượng -->
                        <div
                          class="px-2"
                          style="
                            flex-grow: 1;
                            text-align: left;
                            margin-right: 10px;
                          "
                        >
                          <p class="mb-0" style="font-size: 0.8rem">
                            Số lượng: <b>{{ item.quantity }}</b>
                          </p>
                        </div>

                        <!-- Tổng tiền -->
                        <div
                          class="px-2"
                          style="
                            flex-grow: 1;
                            text-align: right;
                            margin-left: 10px;
                          "
                        >
                          <p class="mb-0" style="font-size: 0.8rem">
                            <b>Giá: </b>
                            <b
                              class="text-danger"
                              v-if="
                                item.idProductDetail.idDiscount
                                  .discountValue === 0
                              "
                            >
                              {{
                                Number(
                                  item.idProductDetail.price
                                ).toLocaleString("vi-VN")
                              }}đ
                            </b>
                            <b
                              class="text-danger"
                              v-if="
                                item.idProductDetail.idDiscount.discountValue >
                                0
                              "
                            >
                              {{
                                Number(
                                  item.idProductDetail.price -
                                    item.idProductDetail.idDiscount
                                      .discountValue
                                ).toLocaleString("vi-VN")
                              }}đ
                            </b>
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="col-5">
                <h5 class="mb-3 text-center">Thông tin</h5>
                <form v-if="isLogin">
                  <div class="row mb-3">
                    <div class="col-6">
                      <label for="fullname" class="form-label">Họ và tên</label>
                      <input
                        disabled
                        v-model="user.username"
                        type="text"
                        class="form-control border"
                        id="fullname"
                        placeholder=" Họ và tên"
                      />
                    </div>
                    <div class="col-6">
                      <label for="fullname" class="form-label"
                        >Số điện thoại</label
                      >
                      <input
                        disabled
                        v-model="user.phone"
                        type="text"
                        class="form-control border"
                        id="phone"
                        placeholder=" Số điện thoại"
                      />
                    </div>
                  </div>

                  <div class="mb-3">
                    <label for="fullname" class="form-label">Email</label>
                    <input
                      disabled
                      type="email"
                      class="form-control border"
                      id="email"
                      v-model="user.email"
                      placeholder=" Email"
                    />
                  </div>
                  <b class="form-label">Địa chỉ</b>
                  <div class="mb-3 d-flex justify-content-between">
                    <div class="me-2 w-100">
                      <label class="form-label">Tỉnh/Thành phố</label>
                      <select
                        v-model="idTinh"
                        class="form-select"
                        @change="getHuyen()"
                      >
                        <option
                          :value="tinh.id"
                          v-for="tinh in tinh"
                          :key="tinh.id"
                        >
                          {{ tinh.provinceName }}
                        </option>
                      </select>
                    </div>
                    <div class="mx-2 w-100">
                      <label class="form-label">Quận/Huyện</label>
                      <select
                        v-model="idHuyen"
                        @change="getXa"
                        class="form-select"
                      >
                        <option
                          :value="huyen.id"
                          v-for="huyen in huyen"
                          :key="huyen.id"
                        >
                          {{ huyen.districtName }}
                        </option>
                      </select>
                    </div>
                    <div class="ms-2 w-100">
                      <label class="form-label">Phường/Xã</label>
                      <select v-model="idXa" class="form-select">
                        <option :value="xa.id" v-for="xa in xa" :key="xa.id">
                          {{ xa.wardName }}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div class="mb-3">
                    <b class="form-label">Địa chỉ chi tiết</b>
                    <input
                      v-model="diaChiChiTietLayGia"
                      type="text"
                      class="form-control border"
                      placeholder=" Địa chỉ chi tiết"
                    />
                  </div>

                  <button
                    type="button"
                    class="btn btn-primary rounded-0"
                    @click="getPriceShip"
                  >
                    Xác nhận địa chỉ
                  </button>
                </form>
                <form v-if="!isLogin">
                  <div class="row mb-3">
                    <div class="col-6">
                      <label class="form-label">Họ và tên</label>
                      <input
                        v-model="diaChiChuaDangNhap.name"
                        type="text"
                        class="form-control border"
                        placeholder=" Họ và tên"
                      />
                    </div>
                    <div class="col-6">
                      <label class="form-label">Số điện thoại</label>
                      <input
                        v-model="diaChiChuaDangNhap.phone"
                        type="text"
                        class="form-control border"
                        placeholder=" Số điện thoại"
                      />
                    </div>
                  </div>

                  <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input
                      type="email"
                      class="form-control border"
                      v-model="diaChiChuaDangNhap.email"
                      placeholder=" Email"
                    />
                  </div>
                  <b class="form-label">Địa chỉ</b>
                  <div class="mb-3 d-flex justify-content-between">
                    <div class="me-2 w-100">
                      <label class="form-label">Tỉnh/Thành phố</label>
                      <select
                        v-model="idTinh"
                        class="form-select"
                        @change="getHuyen()"
                      >
                        <option
                          :value="tinh.id"
                          v-for="tinh in tinh"
                          :key="tinh.id"
                        >
                          {{ tinh.provinceName }}
                        </option>
                      </select>
                    </div>
                    <div class="mx-2 w-100">
                      <label class="form-label">Quận/Huyện</label>
                      <select
                        v-model="idHuyen"
                        @change="getXa"
                        class="form-select"
                      >
                        <option
                          :value="huyen.id"
                          v-for="huyen in huyen"
                          :key="huyen.id"
                        >
                          {{ huyen.districtName }}
                        </option>
                      </select>
                    </div>
                    <div class="ms-2 w-100">
                      <label class="form-label">Phường/Xã</label>
                      <select v-model="idXa" class="form-select">
                        <option :value="xa.id" v-for="xa in xa" :key="xa.id">
                          {{ xa.wardName }}
                        </option>
                      </select>
                    </div>
                  </div>

                  <div class="mb-3">
                    <b class="form-label">Địa chỉ chi tiết</b>
                    <input
                      v-model="diaChiChuaDangNhap.diaChiChiTiet"
                      type="text"
                      class="form-control border"
                      placeholder=" Địa chỉ chi tiết"
                    />
                  </div>

                  <button
                    type="button"
                    class="btn btn-primary rounded-0"
                    @click="checkGiaChuaDangNhap"
                  >
                    Xác nhận địa chỉ
                  </button>
                </form>
                <br />
                <hr />
                <h6>
                  Tiền ship:
                  <b class="text-danger">
                    {{
                      Number(responeGiaShip?.data.total || 0).toLocaleString(
                        "vi-VN"
                      )
                    }}
                    đ</b
                  >
                </h6>

                <h5>
                  Tổng:
                  <b class="text-danger"
                    >{{
                      Number(
                        tongCong +
                          (responeGiaShip?.data.total &&
                          responeGiaShip.data.total > 0
                            ? responeGiaShip.data.total
                            : 0)
                      ).toLocaleString("vi-VN")
                    }}
                    đ</b
                  >
                </h5>
              </div>
            </div>
          </div>

          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-primary rounded-0"
              @click="XacNhanThanhToan"
            >
              Xác nhận
            </button>
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
import js from "@eslint/js";

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
    const gioHang = {
      idProductDetail: gh.idProductDetail.id,
      quantity: gh.quantity,
      idUSer: gh.idUser.id,
      status: 0,
    };
    try {
      const response = await axios.post(
        "http://localhost:8080/user/cart",
        gioHang,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          withCredentials: true,
        }
      );
    } catch (error) {
      console.error("Lỗi khi sửa số lượng giỏ hàng:", error);

      if (error.response) {
        console.error("Status:", error.response.status);
        console.error("Data:", error.response.data);
        toast.error(`Lỗi: ${error.response.data.message || "Có lỗi xảy ra!"}`);
      }
    }
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
//chek user
const getUserFromSession = () => {
  const storedUser = sessionStorage.getItem("user");
  user.value = storedUser ? JSON.parse(storedUser) : null;
};

const user = ref(null);

const isLogin = computed(() => !!user.value);

//lấy ra giỏ hàng

const getCart = async () => {
  if (isLogin.value) {
    const idUser = user.value.id;
    try {
      const response = await axios.get(
        `http://localhost:8080/user/cart/${idUser}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          withCredentials: true,
        }
      );
      cart.value = response.data;
    } catch (error) {
      console.error("Lỗi khi lấy giỏ hàng:", error);
    }
  } else {
    const sessionCart = JSON.parse(sessionStorage.getItem("cart")) || [];
    cart.value = sessionCart;
  }
};
//xóa
const xoaGioHang = async (gh) => {
  if (isLogin.value) {
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
          const response = await axios.delete(
            `http://localhost:8080/user/cart/delete/${gh.id}`,
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
              },
              withCredentials: true,
            }
          );
          getCart();
        } catch (error) {
          console.error("Lỗi khi lấy giỏ hàng:", error);
        }
      }
    });
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
//xóa gioHang them oder
const xoaGioSauOder = async (id) => {
  try {
    const response = await axios.delete(
      `http://localhost:8080/user/cart/delete/${id}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        withCredentials: true,
      }
    );
  } catch (error) {
    console.error("Lỗi khi xóa giỏ hàng sau odder:", error);
  }
};
// list để đã chọn để thanh
const chonSanPham = ref([]);
///////////////////////////////////////////////////////////////
watch(chonSanPham, (newVal) => {
  console.log("Sản phẩm đã chọn:", JSON.stringify(newVal, null, 2));
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

const responeGiaShip = ref(null);
const getPriceShip = async () => {
  if (isLogin.value) {
    responeGiaShip.value = null;
    const canNang = ref(
      chonSanPham.value.reduce((total, item) => {
        const quantity = item.quantity || 0;
        return total + 5000 * quantity;
      }, 0)
    );
    const soLuong = ref(canNang.value / 5000);
    const donHang = ref({
      service_type_id: 2,
      from_district_id: 1442,
      from_ward_code: "21211",
      to_district_id: toDistrictId.value,
      to_ward_code: String(toWardCode.value),
      length: soLuong.value,
      width: 200,
      height: 150,
      weight: canNang.value,
      insurance_value: 0,
      coupon: null,
      items: [
        {
          name: "maylocnuoc",
          quantity: 1,
          length: 200,
          width: 200,
          height: 200,
          weight: 1000,
        },
      ],
    });
    // const donHang = ref({
    //   payment_type_id: 2,
    //   note: "Hàng dễ tổn thương! cẩn thận",
    //   required_note: "KHONGCHOXEMHANG",
    //   return_phone: "0378261550",
    //   return_address: "39 NTT",
    //   return_district_id: null,
    //   return_ward_code: "",
    //   client_order_code: "",
    //   from_name: "AquaFreshShop",
    //   from_phone: "0378261550",
    //   from_address: "72 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Vietnam",
    //   from_ward_name: "Phường 15",
    //   from_district_name: "Quận 10",
    //   from_province_name: "HCM",
    //   to_name: user.value?.username,
    //   to_phone: user.value?.phone,
    //   to_address: `${user.value?.specificAddress}, ${user.value?.address?.wardName}, ${user.value?.address?.district?.districtName}, ${user.value?.address?.district?.province?.provinceName}, Việt Nam`,
    //   to_ward_name: user.value?.address?.wardName,
    //   to_district_name: user.value?.address?.district?.districtName,
    //   to_province_name: user.value?.address?.district?.province?.provinceName,
    //   cod_amount: tongCong.value,
    //   content: "Đơn hàng : máy lọc nước",
    //   length: soLuong.value,
    //   width: 150,
    //   height: 150,
    //   weight: canNang.value,
    //   cod_failed_amount: 0,
    //   pick_station_id: 1444,
    //   deliver_station_id: null,
    //   insurance_value: 5000000,
    //   service_type_id: 2,
    //   coupon: null,
    //   pickup_time: 1692840132,
    //   pick_shift: [2],
    //   items: [
    //     {
    //       name: "blabal",
    //       code: "locNuocVip",
    //       quantity: 1,
    //       price: 200000,
    //       length: 12,
    //       width: 12,
    //       height: 12,
    //       weight: 1200,
    //       category: {
    //         level1: "Áo",
    //       },
    //     },
    //   ],
    // });
    if (canNang.value > 50000) {
      toast.error(
        "Khối lượng hàng hóa không được lớn hơn 50kg (Tối đa 10 chiếc)"
      );
      return;
    }
    if (diaChiChiTietLayGia.value.length < 5) {
      toast.error("Vui lòng nhập địa chỉ chi tiết");
      return;
    }
    if (
      toDistrictId.value &&
      !isNaN(toDistrictId.value) &&
      toDistrictId.value !== ""
    ) {
      // Kiểm tra mã quận (district_id) là một số hợp lệ và không trống
      const toDistrictIdValue = Number(toDistrictId.value); // Chuyển đổi sang số
      if (toDistrictIdValue <= 0) {
        toast.error("Vui lòng chọn quận/huyện");
        return;
      }
    }

    // Kiểm tra mã phường (ward_code) không trống và là chuỗi hợp lệ
    const toWardCodeValue = String(toWardCode.value); // Chuyển mã phường thành chuỗi và loại bỏ khoảng trắng
    if (toWardCodeValue === "") {
      toast.error("Vui lòng chọn xã/phường.");
      return;
    }
    if (tongCong.value > 50000000) {
      toast.error("Thanh toán 1 lần không quá 50 triệu");
      return;
    }

    try {
      // console.log("ĐỊa chỉ hui di là:", JSON.stringify(donHang.value, null, 2));
      const response = await axios.post(
        "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee",
        donHang.value,
        {
          headers: {
            "Content-Type": "application/json",
            ShopId: "5728798",
            Token: "4d93b447-16b0-11f0-8078-2a002cd46251",
          },
        }
      );
      responeGiaShip.value = response.data;
    } catch (error) {
      console.error("Lỗi khi lấy giá ship:", error);
    }
  } else {
    toast.warning("Điền thông tin địa chỉ", {
      timeout: 1000,
    });
  }
};
//
const diaChiChiTietLayGia = ref("");
const handleClose = () => {
  responeGiaShip.value = null;
};
const XacNhanThanhToan = async () => {
  if (isLogin.value) {
    if (
      responeGiaShip.value?.data?.total == null ||
      isNaN(responeGiaShip.value.data.total) ||
      Number(responeGiaShip.value.data.total) === 0
    ) {
      toast.error("Vui lòng xác nhận địa chỉ", {
        timeout: 1500,
      });
      return;
    }
    const detailGuessDTOList = chonSanPham.value.map((item) => ({
      price:
        item.idProductDetail.price -
        item.idProductDetail.idDiscount.discountValue,
      quantity: item.quantity,
      productDetailId: item.idProductDetail.id,
    }));
    const donHang = {
      total: tongCong.value + responeGiaShip.value.data.total,
      idUser: user.value.id,
      status: "PENDING",
      shippingPrice: responeGiaShip.value.data.total,
      detailGuessDTOList: detailGuessDTOList,
    };
    // console.log("odder là:", JSON.stringify(donHang, null, 2));
    // console.log(
    //   "Gio hang la:",
    //   JSON.stringify(cart.value, null, 2),
    //   cart.value
    // );

    Swal.fire({
      title: "Xác nhận đặt hàng?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Có",
      cancelButtonText: "Không",
    }).then(async (result) => {
      // Cần async ở đây vì có await bên trong
      if (result.isConfirmed) {
        await addOrder(donHang);
        for (const detail of donHang.detailGuessDTOList) {
          const itemTrongGio = cart.value.find(
            (item) => item.idProductDetail.id === detail.productDetailId
          );

          if (itemTrongGio) {
            await xoaGioSauOder(itemTrongGio.id); // Xóa theo id của giỏ hàng
          }
        }
        getCart();
      }
    });
  } else {
    toast.warning("Vui lòng đăng nhập", {
      timeout: 1000,
    });
    // if (
    //   responeGiaShip.value?.data?.total == null ||
    //   isNaN(responeGiaShip.value.data.total) ||
    //   Number(responeGiaShip.value.data.total) === 0
    // ) {
    //   toast.error("Vui lòng xác nhận địa chỉ!", {
    //     timeout: 1500,
    //   });
    //   return;
    // }
    // const detailGuessDTOList = chonSanPham.value.map((item) => ({
    //   price:
    //     item.idProductDetail.price -
    //     item.idProductDetail.idDiscount.discountValue,
    //   quantity: item.quantity,
    //   productDetailId: item.idProductDetail.id,
    // }));
    // const order = {
    //   total: tongCong.value + responeGiaShip.value.data.total,
    //   idUser: null,
    //   status: "Pending",
    //   shippingPrice: responeGiaShip.value.data.total,
    //   detailGuessDTOList: detailGuessDTOList,
    // };
    // Swal.fire({
    //   title: "Xác nhận đặt hàng?",
    //   icon: "question",
    //   showCancelButton: true,
    //   confirmButtonText: "Có",
    //   cancelButtonText: "Không",
    // }).then(async (result) => {
    //   // Cần async ở đây vì có await bên trong
    //   if (result.isConfirmed) {
    //     console.log("order là:", JSON.stringify(order, null, 2));
    //     addOrder(order);
    //     responeGiaShip.value = null;
    //   }
    // });
  }
};
const addOrder = async (order) => {
  try {
    const response = await axios.post("http://localhost:8080/order", order);
    console.log("Đơn hàng đã được tạo:", response.data);
    toast.success("Đặt hàng thành công", {});
    document.querySelector(".btn-close")?.click();
  } catch (error) {
    console.error("Lỗi khi thanh toán:", error);
  }
};

//dia chi chua dang nhap

const diaChiChuaDangNhap = ref({
  name: "",
  phone: "",
  email: "",
  xa: null,
  huyen: null,
  tinh: null,
  diaChiChiTiet: "",
});
//lay gia chua dan nhap
const checkGiaChuaDangNhap = async () => {
  // if (!isLogin.value) {
  //   responeGiaShip.value = null;
  //   const canNang = ref(
  //     chonSanPham.value.reduce((total, item) => {
  //       const quantity = item.quantity || 0;
  //       return total + 5000 * quantity;
  //     }, 0)
  //   );
  //   const soLuong = ref(canNang.value / 5000);
  //   const donHang = ref({
  //     payment_type_id: 2,
  //     note: "Hàng dễ tổn thương! cẩn thận",
  //     required_note: "KHONGCHOXEMHANG",
  //     return_phone: "0378261550",
  //     return_address: "39 NTT",
  //     return_district_id: null,
  //     return_ward_code: "",
  //     client_order_code: "",
  //     from_name: "AquaFreshShop",
  //     from_phone: "0378261550",
  //     from_address: "72 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Vietnam",
  //     from_ward_name: "Phường 15",
  //     from_district_name: "Quận 10",
  //     from_province_name: "HCM",
  //     to_name: diaChiChuaDangNhap.value.name,
  //     to_phone: diaChiChuaDangNhap.value.phone,
  //     to_address: `${diaChiChuaDangNhap.value.diaChiChiTiet}, ${diaChiChuaDangNhap.value.xa}, ${diaChiChuaDangNhap.value.huyen}, ${diaChiChuaDangNhap.value.tinh}, Việt Nam`,
  //     to_ward_name: diaChiChuaDangNhap.value.xa,
  //     to_district_name: diaChiChuaDangNhap.value.huyen,
  //     to_province_name: diaChiChuaDangNhap.value.tinh,
  //     cod_amount: tongCong.value,
  //     content: "Đơn hàng : máy lọc nước",
  //     length: soLuong.value,
  //     width: 150,
  //     height: 150,
  //     weight: canNang.value,
  //     cod_failed_amount: 0,
  //     pick_station_id: 1444,
  //     deliver_station_id: null,
  //     insurance_value: 5000000,
  //     service_type_id: 2,
  //     coupon: null,
  //     pickup_time: 1692840132,
  //     pick_shift: [2],
  //     items: [
  //       {
  //         name: "blabal",
  //         code: "locNuocVip",
  //         quantity: 1,
  //         price: 200000,
  //         length: 12,
  //         width: 12,
  //         height: 12,
  //         weight: 1200,
  //         category: {
  //           level1: "Áo",
  //         },
  //       },
  //     ],
  //   });
  //   if (
  //     diaChiChuaDangNhap.value.xa == null ||
  //     diaChiChuaDangNhap.value.huyen == null ||
  //     diaChiChuaDangNhap.value.tinh == null
  //   ) {
  //     toast.error("Địa chỉ không hợp lệ");
  //     return;
  //   }
  //   if (
  //     !diaChiChuaDangNhap.value.phone ||
  //     diaChiChuaDangNhap.value.phone.length < 10 ||
  //     diaChiChuaDangNhap.value.phone.length > 11 ||
  //     !diaChiChuaDangNhap.value.phone.startsWith("03")
  //   ) {
  //     toast.error("Số điện thoại không hợp lệ");
  //     return;
  //   }
  //   if (
  //     diaChiChuaDangNhap.value.diaChiChiTiet == null ||
  //     diaChiChuaDangNhap.value.diaChiChiTiet.length < 5
  //   ) {
  //     toast.error("Địa chỉ chi tiết không hợp lệ");
  //     return;
  //   }
  //   if (
  //     diaChiChuaDangNhap.value.name == null ||
  //     diaChiChuaDangNhap.value.name.length < 5
  //   ) {
  //     toast.error("Tên không hợp lệ");
  //     return;
  //   }
  //   if (canNang.value > 50000) {
  //     toast.error(
  //       "Khối lượng hàng hóa không được lớn hơn 50kg (Tối đa 10 chiếc)"
  //     );
  //     return;
  //   }
  //   if (tongCong.value > 50000000) {
  //     toast.error("Thanh toán 1 lần không quá 50 triệu");
  //     return;
  //   }
  //   // console.log("dia chi gui di:", JSON.stringify(donHang.value, null, 2));
  //   try {
  //     const response = await axios.post(
  //       "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create",
  //       donHang.value,
  //       {
  //         headers: {
  //           "Content-Type": "application/json",
  //           ShopId: "5728798",
  //           Token: "4d93b447-16b0-11f0-8078-2a002cd46251",
  //         },
  //       }
  //     );
  //     responeGiaShip.value = response.data;
  //   } catch (error) {
  //     console.error("Lỗi khi lấy giá ship:", error);
  //   }
  // }
  toast.warning("Vui lòng đăng nhập", {
    timeout: 1000,
  });
};
//lấy địa chỉ ra hazzz
const idTinh = ref();
const idHuyen = ref();
const idXa = ref();

const tinh = ref([]);
const huyen = ref([]);
const xa = ref([]);
// Hàm tìm tên dựa theo ID
const tenTinh = computed(() => {
  const t = tinh.value.find((item) => item.id === idTinh.value);
  return t ? t.provinceName : "";
});
const tenHuyen = computed(() => {
  const h = huyen.value.find((item) => item.id === idHuyen.value);
  return h ? h.districtName : "";
});
const tenXa = computed(() => {
  const x = xa.value.find((item) => item.id === idXa.value);
  return x ? x.wardName : "";
});

// Tự động gán tên vào object địa chỉ khi chọn
watch(tenTinh, (newVal) => {
  diaChiChuaDangNhap.value.tinh = newVal;
});
watch(tenHuyen, (newVal) => {
  diaChiChuaDangNhap.value.huyen = newVal;
});
watch(tenXa, (newVal) => {
  diaChiChuaDangNhap.value.xa = newVal;
});
const toDistrictId = computed(() => {
  const h = huyen.value.find((item) => item.id === idHuyen.value);
  return h ? h.districtIdApi : null;
});

const toWardCode = computed(() => {
  const x = xa.value.find((item) => item.id === idXa.value);
  return x?.wardIdApi?.toString() || "";
});

const getTinh = async () => {
  try {
    const res = await axios.get("http://localhost:8080/address/province");

    tinh.value = res.data;
    // console.log("tinh la", JSON.stringify(tinh.value, null, 2));
  } catch (error) {
    console.error(error);
  }
};
const getHuyen = async () => {
  try {
    console.log("id tinh la", idTinh.value);
    const res = await axios.post("http://localhost:8080/address/district", {
      id: idTinh.value,
    });
    console.log(res.data);
    huyen.value = res.data;
  } catch (error) {
    console.error(error);
  }
};
const getXa = async () => {
  try {
    const res = await axios.post("http://localhost:8080/address/ward", {
      id: idHuyen.value,
    });
    console.log(res.data);
    xa.value = res.data;
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  getUserFromSession();
  getCart();
  getTinh();
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
