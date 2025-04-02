<template>
  <div class="container-fluid" style="background-color: aliceblue">
    <br /><br />
    <div class="container-sm container-md container-lg container-xl">
      <!-- Carousel -->
      <div
        id="carouselExampleSlidesOnly"
        class="carousel slide"
        data-bs-ride="carousel"
      >
        <div class="carousel-inner">
          <div class="carousel-item active" data-bs-interval="2000">
            <img
              src="https://i.imgur.com/U2Mxo0s.jpeg"
              class="d-block w-100"
              alt="Xbox"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://i.imgur.com/qZVQFJZ.png"
              class="d-block w-100"
              alt="PlayStation"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://i.imgur.com/99nRJlt.png"
              class="d-block w-100"
              alt="Nintendo"
            />
          </div>
        </div>
      </div>
    </div>
    <br />
    <div class="container-fluid px-5">
      <div class="row">
        <div class="col-lg-2" style="margin-top: 300px">
          <h5 class="mb-3 text-start">Bộ lọc</h5>
          <div class="mb-3">
            <input
              class="form-control"
              placeholder="Tên sản phẩm.."
              v-model="duLieu"
              @keyup.enter="timKiem"
              @input="timKiem"
            />
          </div>

          <div class="mb-3">
            <label for="vaiTro" class="form-label text-start">Danh mục</label>
            <select class="form-control" @change="timKiem" v-model="danhMuc">
              <option value="">Tất cả</option>
              <option v-for="ct in categorys" :key="ct.id" :value="ct.id">
                {{ ct.name }}
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label for="vaiTro" class="form-label text-start">Giá</label>
            <select class="form-control" @change="setGia" v-model="gia">
              <option value="">Tất cả</option>
              <option value="0">Dưới 3 triệu</option>
              <option value="1">3 triệu - 7 triệu</option>
              <option value="2">Trên 7 triệu</option>
            </select>
          </div>
        </div>
        <div class="col-8">
          <div v-show="!isSearch">
            <div>
              <br /><br />
              <h1
                class="text-center fw-semibold text-uppercase mt-3 text-secondary"
              >
                Nổi Bật
              </h1>
              <hr />
              <div class="row g-2">
                <div
                  v-for="pd in products.filter((p) => p.category.id === 1)"
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
                        style="
                          width: 100%;
                          aspect-ratio: 16 / 9;
                          object-fit: cover;
                        "
                      />
                    </router-link>

                    <!-- Nội dung card -->
                    <div class="card-body d-flex flex-column">
                      <router-link
                        :to="'/productDetail/' + pd.id"
                        class="text-decoration-none text-dark"
                      >
                        <h6 class="card-title text-center fs-6">
                          {{ truncateText(pd.name, 34) }}
                        </h6>

                        <p class="card-text fs-6">
                          <small> {{ truncateText(pd.description, 93) }}</small>
                        </p>
                      </router-link>
                      <!-- Phần giá và button luôn nằm dưới cùng -->
                      <div class="mt-auto">
                        <div style="min-height: 30px">
                          <b style="color: red">
                            {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                          </b>
                        </div>

                        <div class="d-flex justify-content-between">
                          <button
                            @click.stop="themVaoGio(pd)"
                            class="btn btn-success btn-sm"
                          >
                            Thêm vào giỏ
                          </button>
                          <button
                            @click.stop="muaHang(pd)"
                            class="btn btn-danger btn-sm"
                          >
                            Mua
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="xbox">
              <br /><br />
              <h1
                class="text-center fw-semibold text-uppercase mt-3 text-secondary"
              >
                Xbox Series
              </h1>
              <hr />
              <div class="row g-2">
                <div
                  v-for="pd in products.filter((p) => p.category.id === 1)"
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
                        style="
                          width: 100%;
                          aspect-ratio: 16 / 9;
                          object-fit: cover;
                        "
                      />
                    </router-link>

                    <!-- Nội dung card -->
                    <div class="card-body d-flex flex-column">
                      <router-link
                        :to="'/productDetail/' + pd.id"
                        class="text-decoration-none text-dark"
                      >
                        <h6 class="card-title text-center fs-6">
                          {{ truncateText(pd.name, 34) }}
                        </h6>

                        <p class="card-text fs-6">
                          <small> {{ truncateText(pd.description, 93) }}</small>
                        </p>
                      </router-link>
                      <!-- Phần giá và button luôn nằm dưới cùng -->
                      <div class="mt-auto">
                        <div style="min-height: 30px">
                          <b style="color: red">
                            {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                          </b>
                        </div>

                        <div class="d-flex justify-content-between">
                          <button
                            @click.stop="themVaoGio(pd)"
                            class="btn btn-success btn-sm"
                          >
                            Thêm vào giỏ
                          </button>
                          <button
                            @click.stop="muaHang(pd)"
                            class="btn btn-danger btn-sm"
                          >
                            Mua
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="ps">
              <br /><br />
              <h1
                class="text-center fw-semibold text-uppercase mt-3 text-secondary"
              >
                Playtation Series
              </h1>
              <hr />
              <div class="row g-2">
                <div
                  v-for="pd in products.filter((p) => p.category.id === 2)"
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
                        style="
                          width: 100%;
                          aspect-ratio: 16 / 9;
                          object-fit: cover;
                        "
                      />
                    </router-link>

                    <!-- Nội dung card -->
                    <div class="card-body d-flex flex-column">
                      <router-link
                        :to="'/productDetail/' + pd.id"
                        class="text-decoration-none text-dark"
                      >
                        <h6 class="card-title text-center fs-6">
                          {{ truncateText(pd.name, 34) }}
                        </h6>

                        <p class="card-text fs-6">
                          <small> {{ truncateText(pd.description, 93) }}</small>
                        </p>
                      </router-link>
                      <!-- Phần giá và button luôn nằm dưới cùng -->
                      <div class="mt-auto">
                        <div style="min-height: 30px">
                          <b style="color: red">
                            {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                          </b>
                        </div>

                        <div class="d-flex justify-content-between">
                          <button
                            @click.stop="themVaoGio(pd)"
                            class="btn btn-success btn-sm"
                          >
                            Thêm vào giỏ
                          </button>
                          <button
                            @click.stop="muaHang(pd)"
                            class="btn btn-danger btn-sm"
                          >
                            Mua
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div id="nitendo">
              <br /><br />
              <h1
                class="text-center fw-semibold text-uppercase mt-3 text-secondary"
              >
                Nitendo Series
              </h1>
              <hr />
              <div class="row g-2">
                <div
                  v-for="pd in products.filter((p) => p.category.id === 3)"
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
                        style="
                          width: 100%;
                          aspect-ratio: 16 / 9;
                          object-fit: cover;
                        "
                      />
                    </router-link>

                    <!-- Nội dung card -->
                    <div class="card-body d-flex flex-column">
                      <router-link
                        :to="'/productDetail/' + pd.id"
                        class="text-decoration-none text-dark"
                      >
                        <h6 class="card-title text-center fs-6">
                          {{ truncateText(pd.name, 34) }}
                        </h6>

                        <p class="card-text fs-6">
                          <small> {{ truncateText(pd.description, 93) }}</small>
                        </p>
                      </router-link>
                      <!-- Phần giá và button luôn nằm dưới cùng -->
                      <div class="mt-auto">
                        <div style="min-height: 30px">
                          <b style="color: red">
                            {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                          </b>
                        </div>

                        <div class="d-flex justify-content-between">
                          <button
                            @click.stop="themVaoGio(pd)"
                            class="btn btn-success btn-sm"
                          >
                            Thêm vào giỏ
                          </button>
                          <button
                            @click.stop="muaHang(pd)"
                            class="btn btn-danger btn-sm"
                          >
                            Mua
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-show="isSearch">
            <br />
            <br />
            <div class="row g-2">
              <div
                v-for="pd in productsearch"
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
                      style="
                        width: 100%;
                        aspect-ratio: 16 / 9;
                        object-fit: cover;
                      "
                    />
                  </router-link>

                  <!-- Nội dung card -->
                  <div class="card-body d-flex flex-column">
                    <router-link
                      :to="'/productDetail/' + pd.id"
                      class="text-decoration-none text-dark"
                    >
                      <h6 class="card-title text-center fs-6">
                        {{ truncateText(pd.name, 34) }}
                      </h6>

                      <p class="card-text fs-6">
                        <small> {{ truncateText(pd.description, 93) }}</small>
                      </p>
                    </router-link>
                    <!-- Phần giá và button luôn nằm dưới cùng -->
                    <div class="mt-auto">
                      <div style="min-height: 30px">
                        <b style="color: red">
                          {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                        </b>
                      </div>

                      <div class="d-flex justify-content-between">
                        <button
                          @click.stop="themVaoGio(pd)"
                          class="btn btn-success btn-sm"
                        >
                          Thêm vào giỏ
                        </button>
                        <button
                          @click.stop="muaHang(pd)"
                          class="btn btn-danger btn-sm"
                        >
                          Mua
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div
              v-if="productsearch.length == 0"
              class="d-flex justify-content-center align-items-center"
            >
              <h4 class="text-center text-danger">Không tìm thấy sản phẩm</h4>
            </div>
          </div>
          <div id="tatCa">
            <br /><br />
            <h1
              class="text-center fw-semibold text-uppercase mt-3 text-secondary"
            >
              Tất cả sản phẩm
            </h1>
            <hr />
            <div class="row g-2">
              <div
                v-for="pd in products"
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
                      style="
                        width: 100%;
                        aspect-ratio: 16 / 9;
                        object-fit: cover;
                      "
                    />
                  </router-link>

                  <!-- Nội dung card -->
                  <div class="card-body d-flex flex-column">
                    <router-link
                      :to="'/productDetail/' + pd.id"
                      class="text-decoration-none text-dark"
                    >
                      <h6 class="card-title text-center fs-6">
                        {{ truncateText(pd.name, 34) }}
                      </h6>

                      <p class="card-text fs-6">
                        <small> {{ truncateText(pd.description, 93) }}</small>
                      </p>
                    </router-link>
                    <!-- Phần giá và button luôn nằm dưới cùng -->
                    <div class="mt-auto">
                      <div style="min-height: 30px">
                        <b style="color: red">
                          {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                        </b>
                      </div>

                      <div class="d-flex justify-content-between">
                        <button
                          @click.stop="themVaoGio(pd)"
                          class="btn btn-success btn-sm"
                        >
                          Thêm vào giỏ
                        </button>
                        <button
                          @click.stop="muaHang(pd)"
                          class="btn btn-danger btn-sm"
                        >
                          Mua
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-2"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";

const toast = useToast();

//tim kiếm
const duLieu = ref("");
const danhMuc = ref("");
const soLuong = ref("");
const gia = ref("");
const gia1 = ref("");
const gia2 = ref("");
const setGia = () => {
  if (gia.value === "0") {
    gia1.value = "0";
    gia2.value = "3000000";
  } else if (gia.value === "1") {
    gia1.value = "3000000";
    gia2.value = "7000000";
  } else if (gia.value === "2") {
    gia1.value = "7000000";
    gia2.value = "100000000";
  } else {
    gia1.value = "";
    gia2.value = "";
  }
  timKiem();
};
const timKiem = () => {
  if (
    !duLieu.value &&
    !danhMuc.value &&
    !soLuong.value &&
    !gia.value &&
    !gia1.value &&
    !gia2.value
  ) {
    isSearch.value = false;
  } else {
    isSearch.value = true;
    timKiem1();
  }
};
const productsearch = ref([]);
const isSearch = ref(false);
const timKiem1 = async () => {
  try {
    const res = await axios("http://localhost:8080/products/search", {
      params: {
        duLieu: duLieu.value,
        danhMuc: danhMuc.value,
        gia1: gia1.value,
        gia2: gia2.value,
        soLuong: soLuong.value,
      },
    });
    console.log(res.data);
    productsearch.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tim product", error);
  }
};

const truncateText = (text, maxLength) => {
  if (!text) return "";
  return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
};

const categorys = ref([]);

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

//lấy category
const getCategory = async () => {
  try {
    const res = await axios.get(
      "http://localhost:8080/products/hien-thi/category"
    );
    console.log(res.data);
    categorys.value = res.data;
  } catch (error) {
    console.error("Lỗi khi gọi category:", error);
  }
};
// mua hàng
const muaHang = async (pd) => {};

//fakse user
const userId = "894de7e6-12c8-4387-94ad-05396cca268d";
//thêm giỏ hàng
const themVaoGio = async (pd) => {
  const gioHang = {
    user: { id: userId }, // Lồng userId vào object
    product: { id: pd.id }, // Lồng pd.id vào object
    price: pd.price,
    quantity: 1,
    createdAt: new Date().toISOString().split("T")[0],
  };
  console.log("Giỏ hàng trước khi gửi:", gioHang);
  try {
    const res = await axios.post("http://localhost:8080/cart/add", gioHang);
    console.log(res.data);
    toast.success("Đã thêm vào giỏ hàng");
  } catch (error) {
    console.error("Lỗi:", error.response ? error.response.data : error);
    console.error("Lỗi chi tiết:", error.response.data);
    toast.error("Thêm thất bại");
  }
};

onMounted(() => {
  getCategory();
  getProduct();
});
</script>
<style scoped>
#carouselExample {
  width: 100%; /* Thay đổi chiều rộng của carousel */
  max-width: 1200px; /* Giới hạn chiều rộng tối đa */
  margin: 0 auto; /* Căn giữa */
}

.carousel-item {
  height: 500px; /* Giới hạn chiều cao của carousel */
}

.carousel-item img {
  object-fit: cover; /* Giữ tỷ lệ ảnh khi thay đổi kích thước */
  height: 100%; /* Căn chỉnh chiều cao ảnh phù hợp với carousel-item */
}
.card {
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-title {
  font-size: 1.25rem;
  font-weight: bold;
}

.card-text {
  font-size: 1rem;
}

.card-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 150px;
}

button {
  width: 48%;
}

.modal-content {
  max-width: 500px;
  margin: 0 auto;
}
.hover-effect:hover {
  transform: scale(1.07);
  transition: transform 0.2s ease-in-out;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}
</style>
