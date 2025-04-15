<template>
  <div class="container mt-4">
    <!-- Tiêu đề -->

    <h1 class="mb-4">Đơn Hàng</h1>

    <!-- Kiểm tra nếu không có đơn hàng -->
    <div v-if="orders.length === 0" class="alert alert-info">
      Bạn chưa có đơn hàng nào.
    </div>
    <!-- Danh sách các đơn hàng -->
    <div
      v-for="(order, index) in orders"
      :key="index"
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
                ><b>{{ order.status }}</b></span
              >
            </p>
            <p><strong>Ngày đặt:</strong> {{ new Date().toLocaleString() }}</p>
          </div>
          <div>
            <!-- Tổng tiền với màu đỏ -->
            <p>
              <strong class="font-weight-bold">Tổng tiền: </strong>
              <span class="total-price">
                <b class="text-danger"
                  >{{ formatCurrency(order.total) }} đ</b
                ></span
              >
            </p>
            <!-- Phí vận chuyển với màu đỏ -->
            <p>
              <strong class="font-weight-bold">Phí vận chuyển: </strong>
              <b class="text-danger"
                >{{ formatCurrency(order.shippingPrice) }} đ</b
              >
            </p>
          </div>
        </div>

        <!-- Chi tiết sản phẩm -->
        <div
          v-for="(item, idx) in order.detailGuessDTOList"
          :key="idx"
          class="row mb-3 product-detail"
        >
          <hr />
          <div class="col-md-2 text-center">
            <img
              :src="item.imageUrl"
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
                >Máy lọc nước gầm tủ AQUA 7 cấp lọc có khả năng hút, bơm sâu đến
                3 mét. Đây là một trong những sản phẩm có thể khắc phục tốt việc
                hút từ những nơi có áp lực nước khá yếu, hay hút dưới bể
                ngầm.</strong
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
          <button
            class="btn btn-outline-primary btn-sm update-status-btn"
            :disabled="order.status !== 'Đang chuẩn bị'"
          >
            Cập nhật trạng thái
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

// Dữ liệu đơn hàng
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
