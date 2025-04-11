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
                @click="mua(product)"
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

//// mua
const mua = async (pd) => {
  sessionStorage.removeItem("cart");
};

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
    toast.success("dáyudi");
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
      toast.success("Đã thêm vào giỏ hàng (tạm thời)!");
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
    // console.log("product ok là:", JSON.stringify(product.value, null, 2));
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
watch(
  () => props.id,
  () => {
    getOneProduct(); // Khi ID thay đổi, lấy sản phẩm mới
  }
);
</script>
<style scoped></style>
