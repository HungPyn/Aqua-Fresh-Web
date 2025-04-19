<template>
  <div class="container mt-4">
    <!-- Tiêu đề -->

    <h1 class="mb-4">Đơn Hàng</h1>

    <!-- Kiểm tra nếu không có đơn hàng -->
    <div v-if="donHang.length === 0" class="alert alert-info">
      Bạn chưa có đơn hàng nào.
    </div>
    <!-- Danh sách các đơn hàng -->
    <div v-if="donHang.length > 0">
      <div
        v-for="dh in orders"
        :key="dh.id"
        class="card mb-4 shadow-lg border-primary rounded-lg"
        style="transition: transform 0.3s ease, box-shadow 0.3s ease"
        @mouseover="hover = true"
        @mouseleave="hover = false"
        :class="{ 'shadow-xl': hover }"
      >
        <div class="card-body">
          <div class="d-flex justify-content-between align-items-center">
            <div>
              <!-- Trạng thái đơn hàng với màu đỏ -->
              <p>
                <strong class="font-weight-bold">Trạng thái: </strong>
                <span class="text-warning"
                  ><b>{{ dh.status }}</b></span
                >
              </p>
              <p>
                <strong>Ngày đặt:</strong> {{ new Date().toLocaleString() }}
              </p>
            </div>
            <div>
              <!-- Tổng tiền với màu đỏ -->
              <p>
                <strong class="font-weight-bold">Tổng tiền: </strong>
                <span class="total-price">
                  <b class="text-danger"
                    >{{ formatCurrency(dh.total) }} đ</b
                  ></span
                >
              </p>
              <!-- Phí vận chuyển với màu đỏ -->
              <p>
                <strong class="font-weight-bold">Phí vận chuyển: </strong>
                <b class="text-danger"
                  >{{ formatCurrency(dh.shippingPrice) }} đ</b
                >
              </p>
            </div>
          </div>

          <!-- Chi tiết sản phẩm -->
          <div
            v-for="item in donHang.detailGuessDTOList"
            :key="item.productDetailId"
            class="row mb-3 product-detail"
          >
            <hr />
            <div class="col-md-2 text-center">
              <img
                :src="item.ListImage[0]"
                class="img-fluid"
                alt="Product Image"
                style="
                  width: 100px;
                  height: 100px;
                  border-radius: 5px;
                  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                  transition: transform 0.2s ease;
                "
                @mouseover="item.hover = true"
                @mouseleave="item.hover = false"
                :style="{ transform: item.hover ? 'scale(1.1)' : 'scale(1)' }"
              />
            </div>
            <div class="col-md-6">
              <p>
                Sản phẩm:
                <strong
                  >Máy lọc nước gầm tủ AQUA 7 cấp lọc có khả năng hút, bơm sâu
                  đến 3 mét. Đây là một trong những sản phẩm có thể khắc phục
                  tốt việc hút từ những nơi có áp lực nước khá yếu, hay hút dưới
                  bể ngầm.</strong
                >
              </p>
              <p>
                <strong>Giá: </strong>
                <b class="text-danger">{{ formatCurrency(item.price) }} đ</b>
              </p>
              <p><strong>Số lượng:</strong> {{ item.quantity }}</p>
            </div>
            <div class="col-md-4 text-center">
              <p>
                <strong>Tổng: </strong>
                <b class="text-danger">
                  {{ formatCurrency(item.price * item.quantity) }} đ</b
                >
              </p>
            </div>
          </div>

          <!-- Tình trạng đơn hàng -->
          <div class="d-flex justify-content-end">
            <button class="btn btn-outline-primary btn-sm update-status-btn">
              Cập nhật trạng thái
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { Toast } from "bootstrap/dist/js/bootstrap.bundle";
import { computed, onMounted, ref } from "vue";
import { useToast } from "vue-toastification";

const toast = useToast();

//chek usser
const getUserFromSession = () => {
  const storedUser = sessionStorage.getItem("user");
  user.value = storedUser ? JSON.parse(storedUser) : null;
};
const user = ref(null);
const isLogin = computed(() => !!user.value);
// Dữ liệu đơn hàng
const donHang = ref([]);
const getDonHang = async () => {
  if (isLogin.value) {
    try {
      const response = await axios.get(
        `http://localhost:8080/order/${user.value.id}`
      );
      donHang.value = response.data;
      // console.log(
      //   "DOnhangf laay ra la:",
      //   JSON.stringify(donHang.value, null, 2)
      // );
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  } else {
    toast.warning("Vui lòng đăng nhập để xem đơn hàng của bạn.");
  }
};
const picture = ref([]);
const getPicture = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/product/picture`);
    picture.value = response.data;
    // console.log("Ảnh laay ra la:", JSON.stringify(picture.value, null, 2));
  } catch (error) {
    console.error("lõi lấy ảnh:", error);
  }
};
const orders = ref([
  {
    total: 7236000,
    idUser: 6,
    status: "Đang giao",
    shippingPrice: 286000,
    detailGuessDTOList: [
      {
        price: 2950000,
        quantity: 1,
        productDetailId: 103,
        imageUrl: "https://i.imgur.com/ho3P7PU.jpeg",
      },
      {
        price: 4000000,
        quantity: 2,
        productDetailId: 104,
        imageUrl: "https://i.imgur.com/p1lHdLo.jpeg",
      },
    ],
  },
]);

// Hàm định dạng giá trị tiền
const formatCurrency = (value) => {
  return value.toLocaleString("vi-VN");
};
//ghép ảnh voa fodon hang
const setDonHang = () => {
  // Gắn ảnh vào đơn hàng
  try {
    donHang.value.forEach((dh) => {
      dh.detailGuessDTOList.forEach((detail) => {
        const matchingImages = picture.value
          .filter((img) => img.productDetailIdl === detail.productDetailId)
          .map((img) => img.urlImage);

        detail.ListImage = matchingImages; // Gắn mảng ảnh
      });
    });
    console.log(
      "Đơn hàng sau khi gán ảnh:",
      JSON.stringify(donHang.value, null, 2)
    );
  } catch (error) {
    console.error("Lỗi khi gán ảnh vào đơn hàng:", error);
  }
};
onMounted(async () => {
  // Đảm bảo gọi tuần tự
  getUserFromSession();
  await getDonHang();
  await getPicture();
  await setDonHang();
});
</script>

<style scoped>
/* Card Hover effect */
.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

/* Sản phẩm Image Hover */
.product-detail img {
  transition: transform 0.3s ease;
}
.product-detail img:hover {
  transform: scale(1.1);
}

/* Button Hover effect */
.update-status-btn:hover {
  background-color: #007bff;
  color: white;
}

/* Border style for status */
.status-text {
  font-weight: bold;
  color: #e74c3c;
}

/* Nút Cập nhật trạng thái */
.update-status-btn {
  border-radius: 20px;
  background-color: #007bff;
  color: white;
  border: none;
  transition: background-color 0.3s ease;
}

.update-status-btn:hover {
  background-color: #3b6a9b;
}
</style>
