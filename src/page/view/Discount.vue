<template>
  <div>
    <h2>Danh sách Discounts</h2>
    <table class="table table-primary">
      <thead>
        <tr>
          <th>stt</th>
          <th>id</th>
          <th>code</th>
          <th>pecentage</th>
          <th>valid_from</th>
          <th>valid_to</th>
          <th>creat_at</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(dc, index) in discounts" :key="dc.id">
          <th>{{ index + 1 }}</th>
          <th>{{ dc.id }}</th>
          <th>{{ dc.code }}</th>
          <th>{{ dc.percentage }}</th>
          <th>{{ dc.validFrom }}</th>
          <th>{{ dc.validTo }}</th>
          <th>{{ dc.createAt }}</th>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      discounts: [], // Chứa danh sách discounts
      newDiscount: { code: "", percentage: 0 }, // Thông tin discount mới
    };
  },
  created() {
    this.getDiscounts(); // Lấy danh sách discount khi component được tạo
  },
  methods: {
    // Hàm lấy tất cả discount từ backend
    async getDiscounts() {
      try {
        const response = await axios.get("http://localhost:8080/api/discounts");
        console.log(response.data); // In dữ liệu để kiểm tra cấu trúc
        this.discounts = response.data;
      } catch (error) {
        console.error("Lỗi khi gọi API lấy discount:", error);
      }
    },
    // Hàm thêm mới discount
    async addDiscount() {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/discounts",
          this.newDiscount
        );
        this.getDiscounts(); // Cập nhật lại danh sách discounts sau khi thêm
        this.newDiscount.code = ""; // Reset form
        this.newDiscount.percentage = 0;
      } catch (error) {
        console.error("Lỗi khi thêm discount:", error);
      }
    },
  },
};
</script>
