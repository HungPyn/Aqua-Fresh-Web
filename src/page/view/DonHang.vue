<template>
  <div
    class="mt-4"
    style="max-width: 1000px; margin: 0 auto; font-size: 0.85rem"
  >
    <h1 class="mb-4 text-center">Đơn Hàng</h1>

    <div v-if="donHang.length === 0" class="alert alert-info text-center">
      Bạn chưa có đơn hàng nào.
    </div>

    <div v-if="donHang.length > 0">
      <div
        v-for="dh in donHang"
        :key="dh.id"
        class="card mb-4 border-primary rounded-lg shadow-lg"
        style="transition: transform 0.2s ease, box-shadow 0.2s ease"
        @mouseover="hover = true"
        @mouseleave="hover = false"
        :class="{ shadow: hover }"
      >
        <div class="card-body">
          <div class="d-flex justify-content-between flex-wrap">
            <div class="mb-2">
              <p class="mb-1">
                <strong>Trạng thái: </strong>
                <span style="font-size: 15px; font-weight: bold"
                  ><b v-if="dh.status == 'CHỜ_XỬ_LÝ'" class="text-danger">
                    Chờ Xử lý
                  </b>
                  <b v-if="dh.status == 'ĐANG_GIAO'" class="text-warning">
                    Đang giao
                  </b>
                  <b v-if="dh.status == 'HOÀN_THÀNH'" class="text-success">
                    Hoàn thành
                  </b>
                </span>
              </p>
              <p class="mb-1">
                <strong>Ngày đặt:</strong> {{ new Date().toLocaleString() }}
              </p>
            </div>
            <div class="text-end mb-2">
              <p class="mb-1">
                <strong>Tổng thanh toán: </strong>
                <b class="text-danger">{{ formatCurrency(dh.total) }} đ</b>
              </p>
              <p class="mb-1">
                <strong>Phí vận chuyển: </strong>
                <label class="text-danger"
                  >{{ formatCurrency(dh.shippingPrice) }} đ</label
                >
              </p>
            </div>
          </div>

          <hr />

          <!-- Chi tiết sản phẩm -->
          <div
            v-for="item in dh.detailGuessDTOList"
            :key="item.productDetailId"
            class="d-flex align-items-center mb-2 small"
            style="border-bottom: 1px solid #eee; padding-bottom: 6px"
          >
            <router-link :to="'/productDetail/' + item.productDetailId">
              <img
                :src="item.urlImage"
                alt="Product"
                style="
                  width: 110px;
                  height: 80px;
                  object-fit: cover;
                  border-radius: 4px;
                  margin-right: 10px;
                  margin-right: 35px; /* Tăng khoảng cách giữa ảnh và nội dung */
                "
              />
            </router-link>
            <div class="flex-grow-1">
              <router-link
                class="product-link"
                :to="'/productDetail/' + item.productDetailId"
              >
                <div>
                  <strong style="font-size: 16px">{{
                    item.productName
                  }}</strong>
                </div>
                <div>
                  Số lượng: <b>{{ item.quantity }}</b> | Giá:
                  <span class="text-danger"
                    >{{ formatCurrency(item.price) }} đ</span
                  >
                </div>
              </router-link>
            </div>
            <div class="text-end ms-5">
              <p class="mb-0">
                Tổng:
                <label class="text-danger">
                  {{ formatCurrency(item.price * item.quantity) }} đ
                </label>
              </p>
            </div>
          </div>

          <div class="d-flex justify-content-end mt-3">
            <button
              v-show="dh.status == 'CHỜ_XỬ_LÝ'"
              class="btn btn-outline-danger btn-sm"
              @click="huyDon(dh)"
            >
              Hủy đơn hàng
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
        const matchingImage = picture.value.find(
          (img) => img.productDetailIdl === detail.productDetailId
        );

        // Gán urlImage nếu tìm thấy
        detail.urlImage = matchingImage ? matchingImage.urlImage : null;
      });
    });
  } catch (error) {
    console.error("Lỗi khi gán ảnh:", error);
  }
  console.log(
    "Đơn hàng sau khi gán ảnh:",
    JSON.stringify(donHang.value, null, 2)
  );
};

//huy don hang
const huyDon = async (id) => {
  if (isLogin.value) {
    toast.info("xóa thành công");
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
.product-link {
  text-decoration: none; /* Loại bỏ gạch chân */
  color: inherit; /* Thừa hưởng màu chữ từ phần tử cha */
}

.product-link:hover {
  text-decoration: underline; /* Chỉ gạch chân khi hover */
  color: inherit; /* Giữ nguyên màu chữ khi hover */
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
