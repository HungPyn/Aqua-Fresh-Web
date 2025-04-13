<template>
  <div class="container-fluid">
    <br />
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
              src="https:////i.imgur.com/Z6q48Zh.jpeg"
              class="d-block w-100"
              alt="Xbox"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://i.imgur.com/kkbakbV.jpeg"
              class="d-block w-100"
              alt="PlayStation"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://i.imgur.com/r9VyNeR.jpeg"
              class="d-block w-100"
              alt="Nintendo"
            />
          </div>
        </div>
      </div>
    </div>
    <br />
    <div class="container">
      <div class="row">
        <div id="tuDung">
          <br /><br />
          <h1
            class="text-center fw-semibold text-uppercase mt-3 text-secondary"
          >
            Tủ đứng
          </h1>
          <hr />
          <div class="row g-5">
            <div
              v-for="pd in productFull.filter(
                (pd) => pd.idProduct.idCategory.id === 1
              )"
              :key="pd.id"
              class="col-12 col-md-3 mb-4"
            >
              <div
                class="card d-flex flex-column shadow-sm hover-effect product-card"
                style="min-height: 330px"
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
                    class="text-decoration-none text-dark"
                  >
                    <h6 class="card-title fs-6">
                      {{ truncateText(pd.idProduct.productName, 85) }}
                    </h6>
                  </router-link>
                  <!-- Phần giá và button luôn nằm dưới cùng -->
                  <div class="mt-auto">
                    <div style="min-height: 30px">
                      <div v-if="pd.idDiscount.discountValue > 0">
                        <b
                          style="color: gray"
                          class="text-decoration-line-through"
                        >
                          {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                        </b>
                        <b style="color: red">
                          {{
                            Number(
                              pd.price - pd.idDiscount.discountValue
                            ).toLocaleString("vi-VN")
                          }}
                          vnđ
                        </b>
                      </div>
                      <div v-if="pd.idDiscount.discountValue == 0">
                        <b style="color: red">
                          {{ Number(pd.price).toLocaleString("vi-VN") }}
                          vnđ
                        </b>
                      </div>
                    </div>

                    <div
                      class="d-flex justify-content-center mt-1 add-to-cart-overlay"
                    >
                      <button
                        @click.stop="themVaoGio(pd)"
                        class="btn btn-success btn-sm"
                      >
                        Thêm vào giỏ
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div id="deBan">
          <br /><br />
          <h1
            class="text-center fw-semibold text-uppercase mt-3 text-secondary"
          >
            Để bàn
          </h1>
          <hr />
          <div class="row g-5">
            <div
              v-for="pd in productFull.filter(
                (pd) => pd.idProduct.idCategory.id === 2
              )"
              :key="pd.id"
              class="col-12 col-md-3 mb-4"
            >
              <div
                class="card d-flex flex-column shadow-sm hover-effect product-card"
                style="min-height: 330px"
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
                    class="text-decoration-none text-dark"
                  >
                    <h6 class="card-title fs-6">
                      {{ truncateText(pd.idProduct.productName, 85) }}
                    </h6>
                  </router-link>
                  <!-- Phần giá và button luôn nằm dưới cùng -->
                  <div class="mt-auto">
                    <div style="min-height: 30px">
                      <div v-if="pd.idDiscount.discountValue > 0">
                        <b
                          style="color: gray"
                          class="text-decoration-line-through"
                        >
                          {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                        </b>
                        <b style="color: red">
                          {{
                            Number(
                              pd.price - pd.idDiscount.discountValue
                            ).toLocaleString("vi-VN")
                          }}
                          vnđ
                        </b>
                      </div>
                      <div v-if="pd.idDiscount.discountValue == 0">
                        <b style="color: red">
                          {{ Number(pd.price).toLocaleString("vi-VN") }}
                          vnđ
                        </b>
                      </div>
                    </div>

                    <div
                      class="d-flex justify-content-center mt-1 add-to-cart-overlay"
                    >
                      <button
                        @click.stop="themVaoGio(pd)"
                        class="btn btn-success btn-sm"
                      >
                        Thêm vào giỏ
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div id="amBon">
          <br /><br />
          <h1
            class="text-center fw-semibold text-uppercase mt-3 text-secondary"
          >
            Âm bồn
          </h1>
          <hr />
          <div class="row g-5">
            <div
              v-for="pd in productFull.filter(
                (pd) => pd.idProduct.idCategory.id === 3
              )"
              :key="pd.id"
              class="col-12 col-md-3 mb-4"
            >
              <div
                class="card d-flex flex-column shadow-sm hover-effect product-card"
                style="min-height: 330px"
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
                    class="text-decoration-none text-dark"
                  >
                    <h6 class="card-title fs-6">
                      {{ truncateText(pd.idProduct.productName, 85) }}
                    </h6>
                  </router-link>
                  <!-- Phần giá và button luôn nằm dưới cùng -->
                  <div class="mt-auto">
                    <div style="min-height: 30px">
                      <div v-if="pd.idDiscount.discountValue > 0">
                        <b
                          style="color: gray"
                          class="text-decoration-line-through"
                        >
                          {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                        </b>
                        <b style="color: red">
                          {{
                            Number(
                              pd.price - pd.idDiscount.discountValue
                            ).toLocaleString("vi-VN")
                          }}
                          vnđ
                        </b>
                      </div>
                      <div v-if="pd.idDiscount.discountValue == 0">
                        <b style="color: red">
                          {{ Number(pd.price).toLocaleString("vi-VN") }}
                          vnđ
                        </b>
                      </div>
                    </div>

                    <div
                      class="d-flex justify-content-center mt-1 add-to-cart-overlay"
                    >
                      <button
                        @click.stop="themVaoGio(pd)"
                        class="btn btn-success btn-sm"
                      >
                        Thêm vào giỏ
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
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
          <div class="row g-5">
            <div
              v-for="pd in productFull"
              :key="pd.id"
              class="col-12 col-md-3 mb-4"
            >
              <div
                class="card d-flex flex-column shadow-sm hover-effect product-card"
                style="min-height: 330px"
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
                    class="text-decoration-none text-dark"
                  >
                    <h6 class="card-title fs-6">
                      {{ truncateText(pd.idProduct.productName, 85) }}
                    </h6>
                  </router-link>
                  <!-- Phần giá và button luôn nằm dưới cùng -->
                  <div class="mt-auto">
                    <div style="min-height: 30px">
                      <div v-if="pd.idDiscount.discountValue > 0">
                        <b
                          style="color: gray"
                          class="text-decoration-line-through"
                        >
                          {{ Number(pd.price).toLocaleString("vi-VN") }} vnđ
                        </b>
                        <b style="color: red">
                          {{
                            Number(
                              pd.price - pd.idDiscount.discountValue
                            ).toLocaleString("vi-VN")
                          }}
                          vnđ
                        </b>
                      </div>
                      <div v-if="pd.idDiscount.discountValue == 0">
                        <b style="color: red">
                          {{ Number(pd.price).toLocaleString("vi-VN") }}
                          vnđ
                        </b>
                      </div>
                    </div>

                    <div
                      class="d-flex justify-content-center mt-1 add-to-cart-overlay"
                    >
                      <button
                        @click.stop="themVaoGio(pd)"
                        class="btn btn-success btn-sm"
                      >
                        Thêm vào giỏ
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
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import axios, { AxiosHeaders } from "axios";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";
const toast = useToast();

//get user

const truncateText = (text, maxLength) => {
  if (!text) return "";
  return text.length > maxLength ? text.substring(0, maxLength) + "..." : text;
};

//get ảnh
const getImage = async () => {
  try {
    const response = await axios.get("http://localhost:8080/product/picture");
    images.value = response.data;
  } catch (error) {
    console.error("Lỗi lấy ảnh:", error);
  }
};
//

//getproduct
const getProduct = async () => {
  try {
    const response = await axios.get("http://localhost:8080/product");
    productNoImage.value = response.data;
    console.log("Dữ liệu JSON product no image:");
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
onMounted(() => {
  // Lấy ảnh và sản phẩm đồng thời
  Promise.all([getImage(), getProduct()])
    .then(() => {
      // Sau khi cả hai đều xong, gọi hàm ghép dữ liệu
      getFullProduct();
      getUserFromSession();
    })
    .catch((error) => {
      console.error("Lỗi khi tải dữ liệu:", error);
    });
});

//chek usser
const getUserFromSession = () => {
  const storedUser = sessionStorage.getItem("user");
  user.value = storedUser ? JSON.parse(storedUser) : null;
};
const user = ref(null);
const isLogin = computed(() => !!user.value);
// thêm vào hàng
const themVaoGio = async (pd) => {
  if (isLogin.value) {
    const cart = {
      idProductDetail: pd.id,
      quantity: 1,
      idUSer: user.value.id,
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
        existingItem.quantity += 1;
      } else {
        // Nếu chưa có thì thêm mới
        cart.push({
          idProductDetail: pd,
          quantity: 1,
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
};

//fakse user
const userId = "894de7e6-12c8-4387-94ad-05396cca268d";
//thêm giỏ hàng
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
.product-card {
  position: relative;
  overflow: hidden;
}

.add-to-cart-overlay {
  position: absolute;
  bottom: 15px;
  left: 75%;
  transform: translateX(-50%);
  opacity: 0;
  transition: opacity 0.3s ease;
  width: 75%;
}

.product-card:hover .add-to-cart-overlay {
  opacity: 1;
}
</style>
