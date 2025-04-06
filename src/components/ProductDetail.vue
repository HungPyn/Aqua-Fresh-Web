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
              v-for="(img, index) in product.images"
              :key="index"
              :src="img"
              class="img-thumbnail"
              style="
                width: 80px;
                height: 80px;
                cursor: pointer;
                object-fit: cover;
              "
              @click="checkImg(index)"
              :class="{
                'border border-primary border-2': selectedImage === img,
              }"
              alt="Thumbnail"
            />
          </div>
        </div>

        <!-- Thông tin sản phẩm -->
        <div class="col-md-7">
          <h3 class="fw-bold">{{ product.idProduct.productName }}</h3>
          <br />

          <div v-if="product.idDiscount.discountValue === 0">
            <h3 class="text-danger fw-bold">
              {{
                product.price
                  ? product.price.toLocaleString() + " VNĐ"
                  : "Đang cập nhật"
              }}
            </h3>
          </div>
          <div
            v-if="product.idDiscount.discountValue > 0"
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
            Công nghệ: <b>{{ product.idTechnology.name }}</b>
          </p>
          <p>
            Màu sắc: <b>{{ product.idColor.name }}</b>
          </p>
          <p>
            Thương hiệu: <b>{{ product.idProduct.idCompany.name }}</b>
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
                @click="mua"
              >
                Mua
              </button>
              <button
                class="btn btn-success flex-grow-1 rounded-0"
                @click="themVaoGio"
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
          v-for="pd in productFull
            .filter(
              (pd) =>
                pd.idProduct.idCategory.id == product.idProduct.idCategory.id
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

                  <b3 style="color: red" class="fw-bold">
                    {{
                      pd.price
                        ? (
                            pd.price - pd.idDiscount.discountValue
                          ).toLocaleString() + " VNĐ"
                        : "Đang cập nhật"
                    }}
                  </b3>
                </div>
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

const product = ref({
  id: 3,
  idProduct: {
    id: 1,
    productName:
      "Compact Pulsator Washer for Clothes, .9 Cubic ft. Tub, White, BPAB10WH 9 Cubic ft. Tub, White, BPAB10WH 9 Cubic ft. Tub, White, BPAB10WH 9 Cubic ft. Tub, âsdsBPAB10WH",
    idCategory: {
      id: 1,
      name: "Để bàn",
      status: true,
    },
    idCompany: {
      id: 1,
      name: "kanggaru",
      status: false,
    },
    status: false,
  },
  price: 200000,
  quantity: 234,
  idColor: {
    id: 1,
    name: "Xanh",
    status: false,
  },
  idTechnology: {
    id: 1,
    name: "Dinner",
    status: true,
  },
  description:
    "Máy lọc nước [Tên sản phẩm] là giải pháp lý tưởng để đảm bảo nguồn nước sạch cho gia đình bạn. Với công nghệ lọc tiên tiến, máy có khả năng loại bỏ tạp chất, vi khuẩn và các chất độc hại trong nước, mang đến nguồn nước trong lành, an toàn cho sức khỏe. Sản phẩm được trang bị hệ thống lọc nhiều cấp, giúp loại bỏ cặn bẩn, clo, kim loại nặng và các chất gây ô nhiễm khác. Máy sử dụng công nghệ RO (Reverse Osmosis) giúp loại bỏ các chất độc hại, mang đến nguồn nước tinh khiết. Thiết kế tiết kiệm điện, giúp giảm chi phí vận hành, với dung tích lớn thích hợp cho gia ",
  idDiscount: {
    id: 1,
    discountName: "HESOI25",
    discountValue: 6000,
    startDate: "2025-06-01",
    endDate: "2025-06-30",
    description: "Giảm giá đặc biệt mùa hè",
    active: true,
  },
  status: true,
  images: [
    "https://i.imgur.com/p1lHdLo.jpeg",
    "https://i.imgur.com/ho3P7PU.jpeg",
    "https://i.imgur.com/p1lHdLo.jpeg",
  ],
});

//ảnh
const selectedImage = ref(product.value.images[0]);
const checkImg = (index) => {
  selectedImage.value = product.value.images[index];
};

//// mua
const mua = () => {};
// them vao gio
//fakse user
const userId = "894de7e6-12c8-4387-94ad-05396cca268d";

const props = defineProps(["id"]);
const getOneProduct = async () => {
  try {
    const res = await axios.get(
      "http://localhost:8080/admin/products/" + props.id
    );
    console.log(res.data);
    product.value = res.data;
    console.log(product.value);
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
      .filter((image) => image.idProductDetail.id === product.id)
      .map((image) => image.urlImage);

    // Ghép thông tin sản phẩm và các ảnh vào productFull
    productFull.value.push({
      ...product, // Thêm thông tin của sản phẩm
      images: productImages, // Thêm các ảnh tương ứng
    });
  });
};

onMounted(() => {
  Promise.all([getImage(), getProduct()])
    .then(() => {
      // Sau khi cả hai đều xong, gọi hàm ghép dữ liệu
      getFullProduct();
    })
    .catch((error) => {
      console.error("Lỗi khi tải dữ liệu:", error);
    });
});
watch();
</script>
<style scoped></style>
