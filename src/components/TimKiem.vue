<template>
  <div class="container py-5">
    <!-- Tiêu đề tìm kiếm -->
    <h3 class="text-center mb-4">Tìm kiếm sản phẩm</h3>

    <!-- Form tìm kiếm với các bộ lọc -->
    <div class="row mb-4">
      <div class="col-12 col-md-6 col-lg-2 mb-3">
        <b for="technologyId" class="form-label">Công nghệ</b>
        <select class="form-control text-center">
          <option value="">Chọn công nghệ(chưa có api)</option>
          <option></option>
        </select>
      </div>
      <div class="col-12 col-md-6 col-lg-2 mb-3">
        <b for="technologyId" class="form-label">Giá từ</b>
        <select v-model="priceFrom" @change="timKiem" class="form-control">
          <option value="0">0</option>
          <option value="2000000">2.000.000</option>
          <option value="5000000">5.000.000</option>
        </select>
      </div>

      <!-- Giá đến -->
      <div class="col-12 col-md-6 col-lg-3 mb-3">
        <b for="technologyId" class="form-label">Đến</b>
        <select v-model="priceTo" @change="timKiem" class="form-control">
          <option value="2000000">2.000.000</option>
          <option value="5000000">5.000.000</option>
          <option value="7000000">7.000.000</option>
          <option value="10000000">10.000.000</option>
          <option value="100000000">trên 10.000.000</option>
        </select>
      </div>
    </div>

    <!-- Hiển thị kết quả tìm kiếm -->
    <div v-if="productFull.length === 0" class="text-center">
      <h5 class="text-center mt-5">
        <p class="text-danger">Không có sản phẩm phù hợp với</p>
        <b>"{{ noiDung }}" !</b>
      </h5>
    </div>

    <!-- Kết quả tìm kiếm -->
    <div v-if="productFull.length > 0">
      <div class="row">
        <div
          v-for="pd in productFull"
          :key="pd.productDetailId"
          class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4"
        >
          <div class="card h-100 shadow-sm">
            <router-link
              :to="'/productDetail/' + pd.productDetailId"
              class="text-decoration-none text-dark"
            >
              <img
                :src="pd.urlImage"
                class="img-fluid"
                style="height: 200px; object-fit: cover"
              />
            </router-link>
            <div class="card-body d-flex flex-column">
              <router-link
                :to="'/productDetail/' + pd.productDetailId"
                class="text-decoration-none text-dark"
              >
                <h6 class="card-title">
                  <b>{{ truncateText(pd.productName, 99) }}</b>
                </h6>
              </router-link>
              <div class="mt-auto">
                <b class="text-danger fw-bold">
                  {{ pd.price.toLocaleString() + " VNĐ" }}
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
import axios from "axios";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router"; // Import useRoute

const router = useRoute();

//du lieu tuim kiem
const noiDung = ref(router.query.q);
const technologyId = ref("");
const priceTo = ref(100000000);
const priceFrom = ref(0);

const productFull = ref([]);
const truncateText = (text, maxLength) => {
  if (!text) return "";
  return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
};

const timKiem = async () => {
  try {
    console.log("Tên sản phẩm tìm kiếm:", noiDung.value);
    const response = await axios.get("http://localhost:8080/product/search", {
      params: {
        productName: noiDung.value,
        technologyId: technologyId.value,
        priceTo: priceTo.value,
        priceFrom: priceFrom.value,
      },
    });
    productFull.value = response.data;
    console.log("Dữ liệu tìm kiếm:", productFull.value);
  } catch (error) {
    console.error("Lõi tim kiem:", error);
  }
};

watch(
  () => router.query.q,
  (newQuery) => {
    noiDung.value = newQuery;
    timKiem();
  }
);
onMounted(() => {
  timKiem();
});
</script>
