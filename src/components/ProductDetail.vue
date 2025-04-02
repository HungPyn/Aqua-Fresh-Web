<template>
  <div class="containe-fluid" style="background-color: #fffafa">
    <br /><br />
    <div class="container">
      <div class="row">
        <!-- Hình ảnh sản phẩm -->
        <div class="col-md-5">
          <img
            :src="product.image"
            class="img-fluid rounded shadow-sm"
            alt="Product Image"
          />
        </div>

        <!-- Thông tin sản phẩm -->
        <div class="col-md-7">
          <h1 class="fw-bold">{{ product.name }}</h1>
          <h3 class="text-danger fw-bold">
            {{
              product.price
                ? product.price.toLocaleString() + " VNĐ"
                : "Đang cập nhật"
            }}
          </h3>
          <p class="text-muted">
            Số lượng: {{ product.quantity ?? "Không có sẵn" }}
          </p>
          <p>{{ product.description }}</p>

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
              <button class="btn btn-success flex-grow-1" @click="themVaoGio">
                🛒 Thêm vào giỏ
              </button>
            </div>

            <!-- Dòng thứ hai: Nút Mua Ngay và Yêu Thích, căn cách đều -->
            <div class="d-flex justify-content-between">
              <button class="btn btn-danger flex-grow-1 me-2" @click="mua">
                Mua Ngay
              </button>
              <button
                class="btn btn-outline-danger flex-grow-1"
                @click="themVaoYeuThich"
              >
                ❤️ Yêu thích
              </button>
            </div>
          </div>
        </div>
        <div class="container mt-4">
          <h5 class="fw-bold">Đánh giá {{ product.name }}</h5>

          <div class="d-flex align-items-center">
            <span class="text-warning fs-2">⭐</span>
            <span class="fs-2 fw-bold ms-2">{{ averageRating }}/5</span>
          </div>
          <p class="text-muted">{{ totalReviews }} khách hàng hài lòng</p>

          <!-- Biểu đồ đánh giá -->
          <div
            v-for="(percent, star) in ratingStats"
            :key="star"
            class="d-flex align-items-center"
          >
            <span class="me-2">{{ star }} ★</span>
            <div class="progress w-50">
              <div
                class="progress-bar bg-primary"
                role="progressbar"
                :style="{ width: percent + '%' }"
              ></div>
            </div>
            <span class="ms-2">{{ percent }}%</span>
          </div>

          <!-- Nút viết đánh giá -->
          <button class="btn btn-primary mt-3">Viết đánh giá</button>
        </div>
      </div>
      <br />
      <div class="row g-2">
        <h5 class="fw-semibold mt-3 text-secondary">Các sản phẩm tương tự</h5>
        <div
          v-for="pd in products
            .filter((p) => p.category.id === cate)
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
                :src="pd.image"
                class="img-fluid"
                style="width: 100%; aspect-ratio: 16 / 9; object-fit: cover"
              />
            </router-link>

            <!-- Nội dung card -->
            <div class="card-body d-flex flex-column">
              <router-link
                :to="'/productDetail/' + pd.id"
                class="text-decoration-none text-dark hover-effect"
              >
                <h6 class="card-title text-center fs-6">
                  {{ truncateText(pd.name, 34) }}
                </h6>

                <p class="card-text fs-6">
                  <small> {{ truncateText(pd.description, 245) }}</small>
                </p>
              </router-link>

              <!-- Giá sản phẩm -->
              <div class="mt-auto text-center">
                <b style="color: red">
                  {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                </b>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from "vue";
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
// category tương tự
const cate = ref(1);

//// mua
const mua = () => {};
// them vao gio
//fakse user
const userId = "894de7e6-12c8-4387-94ad-05396cca268d";
const themVaoGio = async () => {
  const gioHang = {
    user: { id: userId },
    product: { id: product.value.id },
    price: product.value.price,
    quantity: soLuong.value,
    createdAt: new Date().toISOString().split("T")[0],
  };
  console.log("Giỏ hàng trước khi gửi:", gioHang);
  try {
    const res = await axios.post("http://localhost:8080/cart/add", gioHang);
    console.log(res.data);
    toast.success("Đã thêm vào giỏ hàng");
  } catch (error) {
    console.error("Lỗi:", error.response ? error.response.data : error);

    toast.error("Thêm thất bại");
  }
};
//themVaoYeuThich
const themVaoYeuThich = () => {};

const truncateText = (text, maxLength) => {
  if (!text) return "";
  return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
};

const product = ref({});
const products = ref([]);
const getProduct = async () => {
  try {
    const res = await axios.get("http://localhost:8080/products/hien-thi");
    console.log(res.data);
    products.value = res.data;
  } catch (error) {
    console.error("Lỗi khi gọi product:", error);
  }
};

const props = defineProps(["id"]);
const getOneProduct = async () => {
  try {
    const res = await axios.get(
      "http://localhost:8080/products/get-product/" + props.id
    );
    console.log(res.data);
    product.value = res.data;
    cate.value = product.value.category?.id || 1;
  } catch (error) {
    console.error("Lỗi khi gọi 1 product:", error);
  }
};

onMounted(() => {
  getProduct();
  getOneProduct();
});
watch(
  () => props.id,
  () => {
    getOneProduct(); // Khi ID thay đổi, lấy sản phẩm mới
  }
);
</script>
<style scoped>
img {
  max-width: 100%;
  border-radius: 8px;
}
.hover-effect {
  color: black; /* Giữ màu đen */
}

.hover-effect:hover h6 {
  text-decoration: underline;
}
</style>
