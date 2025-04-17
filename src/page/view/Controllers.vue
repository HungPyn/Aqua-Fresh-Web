<template>
  <div class="products-container">
    <!-- Hero Banner/Carousel Section -->
    <section class="hero-section">
      <div class="container">
        <div
          id="carouselExampleSlidesOnly"
          class="carousel slide carousel-fade"
          data-bs-ride="carousel"
        >
          <div class="carousel-inner rounded-4 shadow">
            <div class="carousel-item active" data-bs-interval="2000">
              <img
                src="https:////i.imgur.com/Z6q48Zh.jpeg"
                class="d-block w-100"
                alt="Xbox"
              />
              <div class="carousel-caption d-none d-md-block">
                <h2 class="fw-bold">Máy Lọc Nước Chất Lượng Cao</h2>
                <p>Mang đến nguồn nước sạch cho gia đình bạn</p>
              </div>
            </div>
            <div class="carousel-item" data-bs-interval="2000">
              <img
                src="https://i.imgur.com/kkbakbV.jpeg"
                class="d-block w-100"
                alt="PlayStation"
              />
              <div class="carousel-caption d-none d-md-block">
                <h2 class="fw-bold">Công Nghệ Lọc Hiện Đại</h2>
                <p>Loại bỏ tạp chất, giữ lại khoáng chất cần thiết</p>
              </div>
            </div>
            <div class="carousel-item" data-bs-interval="2000">
              <img
                src="https://i.imgur.com/r9VyNeR.jpeg"
                class="d-block w-100"
                alt="Nintendo"
              />
              <div class="carousel-caption d-none d-md-block">
                <h2 class="fw-bold">Đa Dạng Mẫu Mã</h2>
                <p>Phù hợp với mọi không gian nội thất</p>
              </div>
            </div>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleSlidesOnly"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleSlidesOnly"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
    </section>

    <!-- Category Sections -->
    <div class="container py-4">
      <!-- Tủ Đứng Section -->
      <section class="product-section" id="tuDung">
        <div class="section-header">
          <h2 class="section-title">Máy Lọc Nước Tủ Đứng</h2>
          <p class="section-subtitle">
            Thiết kế đứng tiết kiệm diện tích, phù hợp cho nhiều không gian
          </p>
        </div>

        <div class="row g-4">
          <div
            v-for="pd in productFull.filter(
              (pd) => pd.idProduct.idCategory.id === 1
            )"
            :key="pd.id"
            class="col-12 col-sm-6 col-md-4 col-lg-3"
          >
            <div class="product-card">
              <div class="product-image">
                <router-link :to="'/productDetail/' + pd.id">
                  <img
                    :src="pd.images[0]"
                    class="img-fluid"
                    alt="Product Image"
                  />
                </router-link>
                <div
                  class="product-badge"
                  v-if="pd.idDiscount.discountValue > 0"
                >
                  <span
                    >-{{
                      Math.round(
                        (pd.idDiscount.discountValue / pd.price) * 100
                      )
                    }}%</span
                  >
                </div>
                <div class="product-actions">
                  <button @click.stop="themVaoGio(pd)" class="add-to-cart-btn">
                    <i class="fas fa-shopping-cart me-2"></i>Thêm vào giỏ
                  </button>
                </div>
              </div>
              <div class="product-info">
                <router-link
                  :to="'/productDetail/' + pd.id"
                  class="product-name"
                >
                  {{ truncateText(pd.idProduct.productName, 85) }}
                </router-link>
                <div class="product-price">
                  <span class="price-current"
                    >{{
                      Number(
                        pd.price - pd.idDiscount.discountValue
                      ).toLocaleString("vi-VN")
                    }}
                    ₫</span
                  >
                  <span class="price-old" v-if="pd.idDiscount.discountValue > 0"
                    >{{ Number(pd.price).toLocaleString("vi-VN") }} ₫</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Để Bàn Section -->
      <section class="product-section" id="deBan">
        <div class="section-header">
          <h2 class="section-title">Máy Lọc Nước Để Bàn</h2>
          <p class="section-subtitle">
            Thiết kế nhỏ gọn, dễ dàng di chuyển và bố trí
          </p>
        </div>

        <div class="row g-4">
          <div
            v-for="pd in productFull.filter(
              (pd) => pd.idProduct.idCategory.id === 2
            )"
            :key="pd.id"
            class="col-12 col-sm-6 col-md-4 col-lg-3"
          >
            <div class="product-card">
              <div class="product-image">
                <router-link :to="'/productDetail/' + pd.id">
                  <img
                    :src="pd.images[0]"
                    class="img-fluid"
                    alt="Product Image"
                  />
                </router-link>
                <div
                  class="product-badge"
                  v-if="pd.idDiscount.discountValue > 0"
                >
                  <span
                    >-{{
                      Math.round(
                        (pd.idDiscount.discountValue / pd.price) * 100
                      )
                    }}%</span
                  >
                </div>
                <div class="product-actions">
                  <button @click.stop="themVaoGio(pd)" class="add-to-cart-btn">
                    <i class="fas fa-shopping-cart me-2"></i>Thêm vào giỏ
                  </button>
                </div>
              </div>
              <div class="product-info">
                <router-link
                  :to="'/productDetail/' + pd.id"
                  class="product-name"
                >
                  {{ truncateText(pd.idProduct.productName, 85) }}
                </router-link>
                <div class="product-price">
                  <span class="price-current"
                    >{{
                      Number(
                        pd.price - pd.idDiscount.discountValue
                      ).toLocaleString("vi-VN")
                    }}
                    ₫</span
                  >
                  <span class="price-old" v-if="pd.idDiscount.discountValue > 0"
                    >{{ Number(pd.price).toLocaleString("vi-VN") }} ₫</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Âm Bồn Section -->
      <section class="product-section" id="amBon">
        <div class="section-header">
          <h2 class="section-title">Máy Lọc Nước Âm Bồn</h2>
          <p class="section-subtitle">
            Tích hợp vào hệ thống bồn rửa, tiết kiệm không gian
          </p>
        </div>

        <div class="row g-4">
          <div
            v-for="pd in productFull.filter(
              (pd) => pd.idProduct.idCategory.id === 3
            )"
            :key="pd.id"
            class="col-12 col-sm-6 col-md-4 col-lg-3"
          >
            <div class="product-card">
              <div class="product-image">
                <router-link :to="'/productDetail/' + pd.id">
                  <img
                    :src="pd.images[0]"
                    class="img-fluid"
                    alt="Product Image"
                  />
                </router-link>
                <div
                  class="product-badge"
                  v-if="pd.idDiscount.discountValue > 0"
                >
                  <span
                    >-{{
                      Math.round(
                        (pd.idDiscount.discountValue / pd.price) * 100
                      )
                    }}%</span
                  >
                </div>
                <div class="product-actions">
                  <button @click.stop="themVaoGio(pd)" class="add-to-cart-btn">
                    <i class="fas fa-shopping-cart me-2"></i>Thêm vào giỏ
                  </button>
                </div>
              </div>
              <div class="product-info">
                <router-link
                  :to="'/productDetail/' + pd.id"
                  class="product-name"
                >
                  {{ truncateText(pd.idProduct.productName, 85) }}
                </router-link>
                <div class="product-price">
                  <span class="price-current"
                    >{{
                      Number(
                        pd.price - pd.idDiscount.discountValue
                      ).toLocaleString("vi-VN")
                    }}
                    ₫</span
                  >
                  <span class="price-old" v-if="pd.idDiscount.discountValue > 0"
                    >{{ Number(pd.price).toLocaleString("vi-VN") }} ₫</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- All Products Section -->
      <section class="product-section" id="tatCa">
        <div class="section-header">
          <h2 class="section-title">Tất Cả Sản Phẩm</h2>
          <p class="section-subtitle">
            Khám phá đầy đủ các loại máy lọc nước của chúng tôi
          </p>
        </div>

        <div class="row g-4">
          <div
            v-for="pd in productFull"
            :key="pd.id"
            class="col-12 col-sm-6 col-md-4 col-lg-3"
          >
            <div class="product-card">
              <div class="product-image">
                <router-link :to="'/productDetail/' + pd.id">
                  <img
                    :src="pd.images[0]"
                    class="img-fluid"
                    alt="Product Image"
                  />
                </router-link>
                <div
                  class="product-badge"
                  v-if="pd.idDiscount.discountValue > 0"
                >
                  <span
                    >-{{
                      Math.round(
                        (pd.idDiscount.discountValue / pd.price) * 100
                      )
                    }}%</span
                  >
                </div>
                <div class="product-actions">
                  <button @click.stop="themVaoGio(pd)" class="add-to-cart-btn">
                    <i class="fas fa-shopping-cart me-2"></i>Thêm vào giỏ
                  </button>
                </div>
              </div>
              <div class="product-info">
                <router-link
                  :to="'/productDetail/' + pd.id"
                  class="product-name"
                >
                  {{ truncateText(pd.idProduct.productName, 85) }}
                </router-link>
                <div class="product-price">
                  <span class="price-current"
                    >{{
                      Number(
                        pd.price - pd.idDiscount.discountValue
                      ).toLocaleString("vi-VN")
                    }}
                    ₫</span
                  >
                  <span class="price-old" v-if="pd.idDiscount.discountValue > 0"
                    >{{ Number(pd.price).toLocaleString("vi-VN") }} ₫</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import axios, { AxiosHeaders } from "axios";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";
const toast = useToast();

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
  console.log("User từ session:", JSON.stringify(user.value, null, 2));
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
      // console.log("Cart hiện tại:", JSON.stringify(cart, null, 2));
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
/* General Styling */
.products-container {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
  background-color: #f9fafb;
}

/* Hero Section */
.hero-section {
  padding: 1rem 0 2rem;
}

.carousel-item {
  height: 400px;
  overflow: hidden;
  position: relative;
}

.carousel-item img {
  object-fit: cover;
  height: 100%;
  width: 100%;
}

.carousel-caption {
  background-color: rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  padding: 15px;
  max-width: 80%;
  margin: 0 auto;
}

/* Section Styling */
.product-section {
  margin-bottom: 3rem;
  scroll-margin-top: 100px;
}

.section-header {
  text-align: center;
  margin-bottom: 2rem;
  position: relative;
  padding-bottom: 1rem;
}

.section-header::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background: linear-gradient(90deg, #3498db, #2980b9);
  border-radius: 3px;
}

.section-title {
  font-size: 1.8rem;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.section-subtitle {
  color: #7f8c8d;
  max-width: 600px;
  margin: 0 auto;
}

/* Product Card Styling */
.product-card {
  background-color: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  border: 1px solid rgba(0, 0, 0, 0.03);
}

.product-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 15px 35px rgba(52, 152, 219, 0.15);
  border-color: rgba(52, 152, 219, 0.2);
}

.product-image {
  position: relative;
  overflow: hidden;
  padding-top: 100%; /* 1:1 Aspect Ratio */
  background-color: #f8f9fa;
}

.product-image img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s cubic-bezier(0.165, 0.84, 0.44, 1);
}

.product-card:hover .product-image img {
  transform: scale(1.08);
}

.product-badge {
  position: absolute;
  top: 15px;
  left: 15px;
  background: linear-gradient(to right, #e74c3c, #c0392b);
  color: white;
  padding: 6px 12px;
  border-radius: 30px;
  font-weight: 700;
  font-size: 0.8rem;
  z-index: 2;
  box-shadow: 0 3px 10px rgba(231, 76, 60, 0.3);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

.product-actions {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 15px;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0));
  transform: translateY(100%);
  transition: transform 0.4s cubic-bezier(0.19, 1, 0.22, 1);
  display: flex;
  justify-content: center;
}

.product-card:hover .product-actions {
  transform: translateY(0);
}

.add-to-cart-btn {
  width: 100%;
  padding: 10px 15px;
  background: linear-gradient(to right, #3498db, #2980b9);
  color: white;
  border: none;
  border-radius: 30px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  letter-spacing: 0.5px;
  font-size: 0.95rem;
}

.add-to-cart-btn:hover {
  background: linear-gradient(to right, #2980b9, #1a5276);
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(52, 152, 219, 0.4);
}

.product-info {
  padding: 18px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
}

.product-name {
  color: #2c3e50;
  font-weight: 600;
  text-decoration: none;
  margin-bottom: 12px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.5;
  transition: color 0.3s ease;
  font-size: 1.05rem;
}

.product-name:hover {
  color: #3498db;
}

.product-price {
  margin-top: auto;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
}

.price-current {
  font-weight: 700;
  color: #e74c3c;
  font-size: 1.2rem;
  position: relative;
}

.price-old {
  color: #95a5a6;
  text-decoration: line-through;
  font-size: 0.95rem;
  position: relative;
}

/* Responsive adjustments */
@media (max-width: 991.98px) {
  .section-title {
    font-size: 1.5rem;
  }

  .carousel-item {
    height: 300px;
  }
}

@media (max-width: 767.98px) {
  .product-section {
    margin-bottom: 2rem;
  }

  .carousel-item {
    height: 250px;
  }

  .section-title {
    font-size: 1.3rem;
  }
}

@media (max-width: 575.98px) {
  .hero-section {
    padding: 0.5rem 0 1.5rem;
  }

  .carousel-item {
    height: 200px;
  }
}
</style>
