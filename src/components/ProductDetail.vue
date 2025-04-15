<template>
  <div class="containe-fluid">
    <br /><br />
    <div class="container">
      <div class="row">
        <div class="col-md-5 mb-3">
          <img
            :src="selectedImage"
            class="img-fluid rounded shadow-sm"
            alt="Product Image"
          />
          <!-- Ảnh nhỏ phía dưới -->
          <div class="col-12 d-flex flex-wrap gap-2">
            <img
              v-for="(img, index) in product.listUrl"
              :key="index"
              :src="img.urlImage"
              class="img-thumbnail"
              style="
                width: 80px;
                height: 80px;
                cursor: pointer;
                object-fit: cover;
              "
              @click="checkImg(img.urlImage)"
              :class="{
                'border border-primary border-2':
                  selectedImage === img.urlImage,
              }"
              alt="Thumbnail"
            />
          </div>
        </div>

        <!-- Thông tin sản phẩm -->
        <div class="col-md-7">
          <h3 class="fw-bold">{{ product.idProduct?.productName }}</h3>
          <br />

          <div v-if="product.idDiscount?.discountValue === 0">
            <h3 class="text-danger fw-bold">
              {{
                product.price
                  ? product.price.toLocaleString() + " VNĐ"
                  : "Đang cập nhật"
              }}
            </h3>
          </div>
          <div
            v-if="product.idDiscount?.discountValue > 0"
            class="d-flex align-items-center gap-2"
          >
            <h3
              style="color: gray"
              class="text-muted text-decoration-line-through"
            >
              {{
                product.price
                  ? product.price.toLocaleString() + " VNĐ"
                  : "Đang cập nhật"
              }}
            </h3>

            <h3 class="text-danger fw-bold">
              {{
                product.price
                  ? (
                      product.price - product.idDiscount.discountValue
                    ).toLocaleString() + " VNĐ"
                  : "Đang cập nhật"
              }}
            </h3>
          </div>

          <p class="text-muted">
            Số lượng: <b>{{ product.quantity ?? "Không có sẵn" }}</b>
          </p>
          <p>
            Công nghệ: <b>{{ product.idTechnology?.name }}</b>
          </p>
          <p>
            Màu sắc: <b>{{ product.idColor?.name }}</b>
          </p>
          <p>
            Thương hiệu: <b>{{ product.idProduct?.idCompany?.name }}</b>
          </p>

          <!-- Nút hành động -->
          <div class="container mt-4">
            <!-- Dòng đầu tiên: Ô số lượng và nút Thêm vào giỏ -->
            <div class="d-flex align-items-center mb-3">
              <!-- Ô số lượng -->
              <div class="input-group me-3" style="width: 130px">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  @click="giamSoLuong"
                >
                  -
                </button>
                <input
                  type="number"
                  class="form-control text-center fw-bold"
                  v-model="soLuong"
                  readonly
                />
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  @click="tangSoLuong"
                >
                  +
                </button>
              </div>

              <!-- Nút thêm vào giỏ -->
            </div>
            <br />
            <!-- Dòng thứ hai: Nút Mua Ngay và Yêu Thích, căn cách đều -->
            <div class="d-flex justify-content-between">
              <button
                class="btn btn-danger flex-grow-1 me-2 rounded-0"
                @click="getPriceShip"
                data-bs-toggle="modal"
                data-bs-target="#modalThanhToan"
              >
                Mua
              </button>
              <button
                class="btn btn-success flex-grow-1 rounded-0"
                @click="themVaoGio(product)"
              >
                🛒 Thêm vào giỏ
              </button>
            </div>
          </div>
        </div>
        <br />
        <hr />
        <div>
          <h5 class="fw-bold">Thông tin sản phẩm</h5>
          <p class="card-text fs-5">
            {{ product.description }}
          </p>
        </div>
      </div>
      <br />
      <div class="row g-2">
        <h5 class="fw-semibold mt-3 text-secondary">Các sản phẩm tương tự</h5>
        <div
          v-for="pd in productFull
            .filter(
              (pd) =>
                pd.idProduct.idCategory.id == product.idProduct.idCategory.id &&
                pd.id !== product.id
            )
            .sort(() => Math.random() - 0.5)
            .slice(0, 4)"
          :key="pd.id"
          class="col-12 col-md-3 mb-4"
        >
          <div
            class="card d-flex flex-column shadow-sm hover-effect"
            style="min-height: 370px"
          >
            <router-link
              :to="'/productDetail/' + pd.id"
              class="text-decoration-none text-dark"
            >
              <!-- Hình ảnh -->
              <img
                :src="pd.images[0]"
                class="img-fluid"
                style="height: 260px; margin: 10px"
              />
            </router-link>

            <!-- Nội dung card -->
            <div class="card-body d-flex flex-column">
              <router-link
                :to="'/productDetail/' + pd.id"
                class="text-decoration-none text-dark hover-effect"
              >
                <h6 class="card-title fs-6">
                  <b>{{ truncateText(pd.idProduct.productName, 99) }}</b>
                </h6>
              </router-link>

              <!-- Giá sản phẩm -->
              <div class="mt-auto">
                <b style="color: red" v-if="pd.idDiscount.discountValue === 0">
                  {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                </b>
                <div v-if="pd.idDiscount.discountValue > 0">
                  <b
                    style="color: gray"
                    class="text-muted text-decoration-line-through"
                  >
                    {{
                      pd.price
                        ? pd.price.toLocaleString() + " VNĐ"
                        : "Đang cập nhật"
                    }}
                  </b>

                  <b style="color: red" class="fw-bold">
                    {{
                      pd.price
                        ? (
                            pd.price - pd.idDiscount.discountValue
                          ).toLocaleString() + " VNĐ"
                        : "Đang cập nhật"
                    }}
                  </b>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- //medel thanhtoansthanhtoansgh////////////////////////////////////////////////////////// -->

    <div
      v-if="product && product.idProduct"
      class="modal fade"
      id="modalThanhToan"
      tabindex="-1"
    >
      <div class="modal-dialog modal-xl">
        <div class="modal-content rounded-0">
          <div class="modal-header">
            <h5 class="modal-title">Xác nhận thanh toán</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div class="modal-body">
            <div class="row">
              <div class="col-7">
                <div
                  style="background-color: aliceblue"
                  class="p-2 mb-2 shadow-sm"
                >
                  <div class="row align-items-center">
                    <!-- Hình ảnh và tên sản phẩm -->
                    <div class="col-md-3 text-center">
                      <img
                        :src="product.listUrl?.[0]?.urlImage"
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
                        {{ product.idProduct.productName }}
                      </h6>

                      <div
                        class="col-md-12 text-center mt-2 d-flex justify-content-between"
                        style="padding-left: 0px; padding-right: 0px"
                      >
                        <div
                          class="px-2"
                          style="
                            flex-grow: 1;
                            text-align: left;
                            margin-right: 10px;
                          "
                        >
                          <p class="mb-0" style="font-size: 0.8rem">
                            Số lượng: <b>{{ soLuong }}</b>
                          </p>
                        </div>

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
                              v-if="product.idDiscount?.discountValue === 0"
                            >
                              {{
                                Number(product.price).toLocaleString("vi-VN")
                              }}đ
                            </b>
                            <b class="text-danger" v-else>
                              {{
                                Number(
                                  product.price -
                                    product.idDiscount.discountValue
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
                      <label class="form-label">Họ và tên</label>
                      <input
                        disabled
                        v-model="user.username"
                        type="text"
                        class="form-control border"
                        placeholder=" Họ và tên"
                      />
                    </div>
                    <div class="col-6">
                      <label class="form-label">Số điện thoại</label>
                      <input
                        disabled
                        v-model="user.phone"
                        type="text"
                        class="form-control border"
                        placeholder=" Số điện thoại"
                      />
                    </div>
                  </div>

                  <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input
                      disabled
                      type="email"
                      class="form-control border"
                      v-model="user.email"
                      placeholder=" Email"
                    />
                  </div>
                  <b class="form-label">Địa chỉ</b>
                  <p>
                    {{ user.address.wardName }},
                    {{ user.address.district.districtName }}, Tỉnh
                    {{ user.address.district.province.provinceName }}
                  </p>

                  <div class="mb-3">
                    <b class="form-label">Địa chỉ chi tiết</b>
                    <input
                      v-model="user.specificAddress"
                      disabled
                      type="text"
                      class="form-control border"
                      placeholder=" Địa chỉ chi tiết"
                    />
                  </div>
                </form>
                <br />
                <hr />
                <h6>
                  Tiền ship:
                  <b class="text-danger">
                    {{
                      Number(
                        responeGiaShip?.data.total_fee || 0
                      ).toLocaleString("vi-VN")
                    }}
                    đ
                  </b>
                </h6>

                <h5>
                  Tổng:
                  <b class="text-danger">
                    {{
                      Number(
                        (product.price -
                          (product.idDiscount?.discountValue || 0)) *
                          soLuong +
                          (responeGiaShip?.data.total_fee || 0)
                      ).toLocaleString("vi-VN")
                    }}
                    đ
                  </b>
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
import { ref, onMounted, watch, onBeforeMount, computed } from "vue";
import axios from "axios";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";
import router from "@/router/router";
import { useRouter } from "vue-router";
const toast = useToast();
//soluong
const soLuong = ref(1);
const tangSoLuong = () => {
  if (soLuong.value < product.value.quantity) {
    soLuong.value++;
  }
};
const giamSoLuong = () => {
  if (soLuong.value > 1) {
    soLuong.value--;
  }
};
const fullProduct = ref({});
const product = ref({});
//ảnh

const selectedImage = ref(
  product.value?.listUrl?.[0]?.urlImage || "default.jpg"
);
const checkImg = (url) => {
  selectedImage.value = url;
  console.log("selectedImage là:", selectedImage.value);
};
watch(product, (newVal) => {
  if (newVal?.listUrl?.length) {
    selectedImage.value = newVal.listUrl[0].urlImage;
  }
});

//chek usser
const getUserFromSession = () => {
  const storedUser = sessionStorage.getItem("user");
  user.value = storedUser ? JSON.parse(storedUser) : null;
};
const user = ref(null);
const isLogin = computed(() => !!user.value);
// them vao gio
const themVaoGio = async (pd) => {
  if (isLogin.value) {
    const cart = {
      idProductDetail: pd.id,
      quantity: soLuong.value,
      idUSer: user.value.id,
      status: 1,
    };
    try {
      const response = await axios.post(
        "http://localhost:8080/user/cart",
        cart,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          withCredentials: true,
        }
      );
      toast.success("Đã thêm vào giỏ hàng!", {
        timeout: 1000,
      });
    } catch (error) {
      console.error("Lỗi khi thêm vào giỏ hàng:", error);

      if (error.response) {
        console.error("Status:", error.response.status);
        console.error("Data:", error.response.data);
        toast.error(`Lỗi: ${error.response.data.message || "Có lỗi xảy ra!"}`);
      } else if (error.request) {
        console.error("Không có phản hồi từ server:", error.request);
        toast.error("Không có phản hồi từ server.");
      } else {
        console.error("Lỗi khác:", error.message);
        toast.error(`Lỗi: ${error.message}`);
      }
    }
  } else {
    try {
      const cart = JSON.parse(sessionStorage.getItem("cart")) || [];
      const existingItem = cart.find(
        (item) => item.idProductDetail.id === pd.id
      );

      if (existingItem) {
        existingItem.quantity += soLuong.value;
      } else {
        // Nếu chưa có thì thêm mới
        cart.push({
          idProductDetail: pd,
          quantity: soLuong.value,
          dateAdded: new Date().toISOString(),
        });
      }
      // Lưu lại giỏ hàng
      sessionStorage.setItem("cart", JSON.stringify(cart));
      console.log("Cart hiện tại:", JSON.stringify(cart, null, 2));
      toast.success("Đã thêm vào giỏ hàng (tạm thời)!", {
        timeout: 1000,
      });
    } catch (error) {
      console.error("Lỗi khi thêm vào giỏ hàng:", error);
      toast.error("Đã xảy ra lỗi khi thêm vào giỏ hàng!");
    }
  }
  soLuong.value = 1;
};

//fakse user
const userId = "894de7e6-12c8-4387-94ad-05396cca268d";
const props = defineProps(["id"]);
const getOneProduct = async () => {
  try {
    const res = await axios.get("http://localhost:8080/product/" + props.id);
    console.log(res.data);
    fullProduct.value = res.data;
    // console.log("product là:", JSON.stringify(fullProduct.value, null, 2));
    product.value = fullProduct.value.productDetail;
  } catch (error) {
    console.error("Lỗi khi gọi 1 product:", error);
  }
};
//sản phảm tương tự
//get ảnh
const truncateText = (text, maxLength) => {
  if (!text) return "";
  return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
};

const getImage = async () => {
  try {
    const response = await axios.get("http://localhost:8080/product/picture");
    images.value = response.data;
  } catch (error) {
    console.error("Lỗi lấy ảnh:", error);
  }
};
//getproduct
const getProduct = async () => {
  try {
    const response = await axios.get("http://localhost:8080/product");
    productNoImage.value = response.data;
  } catch (error) {
    console.error("Lỗi lấy product", error);
  }
};
//ghép product full dữ liệu
const images = ref([]);
const productNoImage = ref([]);
const productFull = ref([]);
const getFullProduct = () => {
  productNoImage.value.forEach((product) => {
    // Tìm các ảnh có idproduct trùng với id của sản phẩm
    const productImages = images.value
      .filter((image) => image.productDetailIdl === product.id)
      .map((image) => image.urlImage);

    // Ghép thông tin sản phẩm và các ảnh vào productFull
    productFull.value.push({
      ...product, // Thêm thông tin của sản phẩm
      images: productImages, // Thêm các ảnh tương ứng
    });
  });
};

onMounted(async () => {
  try {
    // Chạy getOneProduct, getImage, getProduct song song
    const [productData, imageData, productDetails] = await Promise.all([
      getOneProduct(),
      getImage(),
      getProduct(),
    ]);

    // Sau khi cả 3 async functions đều xong, gọi ghép dữ liệu
    getFullProduct(productData, imageData, productDetails);
    getUserFromSession(); // Lấy thông tin người dùng từ sessionStorage
  } catch (error) {
    console.error("Lỗi khi tải dữ liệu:", error);
  }
});
//thanh toán
const XacNhanThanhToan = async () => {
  if (isLogin.value) {
    if (
      responeGiaShip.value?.data?.total_fee == null ||
      isNaN(responeGiaShip.value.data.total_fee) ||
      Number(responeGiaShip.value.data.total_fee) === 0
    ) {
      toast.error("Đơn hàng không hợp lệ!", {
        timeout: 1500,
      });
      return;
    }
    const detailGuessDTOList = [
      {
        price:
          product.value.price - (product.value.idDiscount?.discountValue || 0),
        quantity: soLuong.value,
        productDetailId: product.value.id,
      },
    ];
    const order = {
      total:
        (product.value.price - (product.value.idDiscount?.discountValue || 0)) *
          soLuong.value +
        responeGiaShip.value.data.total_fee,
      idUser: user.value.id,
      status: "Pending",
      shippingPrice: responeGiaShip.value.data.total_fee,
      detailGuessDTOList: detailGuessDTOList,
    };

    Swal.fire({
      title: "Xác nhận đặt hàng?",
      icon: "question",
      showCancelButton: true,
      confirmButtonText: "Có",
      cancelButtonText: "Không",
    }).then(async (result) => {
      // Cần async ở đây vì có await bên trong
      if (result.isConfirmed) {
        try {
          const response = await axios.post(
            "http://localhost:8080/order",
            order
          );
          console.log("Đơn hàng đã được tạo:", response.data);
          toast.success("Đặt hàng thành công", {});

          document.querySelector(".btn-close")?.click();
        } catch (error) {
          console.error("Lỗi khi thanh toán:", error);
        }
      }
    });
  } else {
    toast.error("Chưa đăng nhập", {
      timeout: 1000,
    });
  }
};

watch(
  () => props.id,
  () => {
    getOneProduct(); // Khi ID thay đổi, lấy sản phẩm mới
  }
);

///gia ship

const responeGiaShip = ref(null);
const getPriceShip = async () => {
  if (isLogin.value) {
    responeGiaShip.value = null;
    const donHang = ref({
      payment_type_id: 2,
      note: "Hàng dễ tổn thương! cẩn thận",
      required_note: "KHONGCHOXEMHANG",
      return_phone: "0378261550",
      return_address: "39 NTT",
      return_district_id: null,
      return_ward_code: "",
      client_order_code: "",
      from_name: "AquaFreshShop",
      from_phone: "0378261550",
      from_address: "72 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Vietnam",
      from_ward_name: "Phường 15",
      from_district_name: "Quận 10",
      from_province_name: "HCM",
      to_name: user.value?.username,
      to_phone: user.value?.phone,
      to_address: `${user.value?.specificAddress}, ${user.value?.address?.wardName}, ${user.value?.address?.district?.districtName}, ${user.value?.address?.district?.province?.provinceName}, Việt Nam`,
      to_ward_name: user.value?.address?.wardName,
      to_district_name: user.value?.address?.district?.districtName,
      to_province_name: user.value?.address?.district?.province?.provinceName,
      cod_amount:
        product.value.price * soLuong.value -
        (product.value.idDiscount?.discountValue || 0), ////
      content: "Đơn hàng : máy lọc nước",
      length: soLuong.value,
      width: 150,
      height: 150,
      weight: soLuong.value * 5000,
      cod_failed_amount: 0,
      pick_station_id: 1444,
      deliver_station_id: null,
      insurance_value: 5000000,
      service_type_id: 2,
      coupon: null,
      pickup_time: 1692840132,
      pick_shift: [2],
      items: [
        {
          name: "blabal",
          code: "locNuocVip",
          quantity: 1,
          price: 200000,
          length: 12,
          width: 12,
          height: 12,
          weight: 1200,
          category: {
            level1: "Áo",
          },
        },
      ],
    });
    if (soLuong.value * 5000 > 50000) {
      toast.error(
        "Khối lượng hàng hóa không được lớn hơn 50kg (Tối đa 10 chiếc)"
      );
      return;
    }
    if (product.value.price * soLuong.value > 50000000) {
      toast.error("Thanh toán 1 lần không quá 50 triệu");
      return;
    }
    //console.log("dia chi gui di:", JSON.stringify(donHang.value, null, 2));
    try {
      const response = await axios.post(
        "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create",
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
    toast.error("Chưa đăng nhập", {
      timeout: 1000,
    });
  }
};
</script>
<style scoped></style>
