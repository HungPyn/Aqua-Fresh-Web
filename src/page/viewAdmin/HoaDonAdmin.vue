<template>
  <div class="container mt-4">
    <h3 class="mb-4">Tra cứu Hóa đơn theo số điện thoại</h3>

    <!-- Form tìm kiếm -->
    <div class="input-group mb-3">
      <input
        type="text"
        class="form-control"
        placeholder="Nhập số điện thoại khách hàng..."
        v-model="searchPhone"
        @keyup.enter="fetchInvoices"
      />
      <button class="btn btn-primary" @click="fetchInvoices">Tìm kiếm</button>
    </div>

    <!-- Bảng kết quả -->
    <div v-if="invoices.length">
      <table class="table table-bordered table-hover">
        <thead>
          <tr class="table-light">
            <th>Mã HĐ</th>
            <th>Mã ĐH</th>
            <th>Họ tên</th>
            <th>SĐT</th>
            <th>Tổng tiền</th>
            <th>Thanh toán</th>
            <th>Chi tiết</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="invoice in paginatedInvoices" :key="invoice.invoiceId">
            <td>{{ invoice.invoiceId }}</td>
            <td>{{ invoice.orderId }}</td>
            <td>{{ invoice.customerName }}</td>
            <td>{{ invoice.phoneNumber }}</td>
            <td>{{ invoice.invoiceTotal.toLocaleString() }} đ</td>
            <td>
              <span
                :class="{
                  'badge bg-success': invoice.paymentStatus === 'Paid',
                  'badge bg-warning': invoice.paymentStatus === 'Unpaid',
                }"
              >
                {{
                  invoice.paymentStatus === "Paid"
                    ? "Đã thanh toán"
                    : "Chờ thanh toán"
                }}
              </span>
            </td>
            <td>
              <button
                class="btn btn-sm btn-info"
                @click="viewDetails(invoice.invoiceId)"
              >
                Xem
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Pagination -->
      <nav>
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <button class="page-link" @click="currentPage--">«</button>
          </li>
          <li
            class="page-item"
            v-for="page in totalPages"
            :key="page"
            :class="{ active: page === currentPage }"
          >
            <button class="page-link" @click="currentPage = page">
              {{ page }}
            </button>
          </li>
          <li
            class="page-item"
            :class="{ disabled: currentPage === totalPages }"
          >
            <button class="page-link" @click="currentPage++">»</button>
          </li>
        </ul>
      </nav>
    </div>
    <div v-else-if="searched">
      <p class="text-danger">
        Không tìm thấy hóa đơn nào cho số điện thoại này.
      </p>
    </div>

    <!-- Modal chi tiết -->
    <div class="modal fade" id="invoiceDetailModal" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              Chi tiết hóa đơn #{{ invoiceDetail?.invoiceId }}
            </h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body" v-if="invoiceDetail">
            <p>
              <strong>Ngày lập:</strong>
              {{ formatDate(invoiceDetail.invoiceDate) }}
            </p>
            <p>
              <strong>Tổng tiền:</strong>
              {{ invoiceDetail.invoiceTotal.toLocaleString() }} đ
            </p>
            <p>
              <strong>Phương thức thanh toán:</strong>
              {{ invoiceDetail.paymentMethod }}
            </p>
            <p>
              <strong>Trạng thái:</strong>
              {{
                invoiceDetail.paymentStatus === "Paid"
                  ? "Đã thanh toán"
                  : "Chờ thanh toán"
              }}
            </p>

            <hr />
            <h5>Chi tiết đơn hàng</h5>
            <ul>
              <li
                v-for="item in invoiceDetail.orderDetails"
                :key="item.productId"
              >
                {{ item.productName }} - SL: {{ item.quantity }} - Đơn giá:
                {{ item.unitPrice.toLocaleString() }} đ
              </li>
            </ul>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" data-bs-dismiss="modal">
              Đóng
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import invoiceService from "@/services/InvoiceService.js";
import { useToast } from "vue-toastification";

const searchPhone = ref("");
const invoices = ref([]);
const searched = ref(false);
const invoiceDetail = ref(null);
const currentPage = ref(1);
const itemsPerPage = 20;
const toast = useToast();

const fetchInvoices = async () => {
  try {
    const res = await invoiceService.getAllInvoices();
    invoices.value = res.filter((i) =>
      i.phoneNumber?.includes(searchPhone.value.trim())
    );
    searched.value = true;
    currentPage.value = 1;
  } catch (e) {
    toast.error("Lỗi khi tìm kiếm hóa đơn!");
  }
};

const paginatedInvoices = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return invoices.value.slice(start, start + itemsPerPage);
});

const totalPages = computed(() => {
  return Math.ceil(invoices.value.length / itemsPerPage);
});

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString("vi-VN");
};

const viewDetails = async (invoiceId) => {
  try {
    const res = await invoiceService.getInvoiceById(invoiceId);
    invoiceDetail.value = {
      invoiceId: res[0][0],
      invoiceDate: res[0][1],
      invoiceTotal: res[0][2],
      paymentMethod: res[0][3],
      paymentStatus: res[0][4],
      orderDetails: res.map((row) => ({
        productName: row[5],
        quantity: row[6],
        unitPrice: row[7],
        productId: row[8],
      })),
    };

    const modal = new bootstrap.Modal(
      document.getElementById("invoiceDetailModal")
    );
    modal.show();
  } catch (err) {
    toast.error("Không thể tải chi tiết hóa đơn.");
  }
};
</script>

<style scoped>
.badge {
  font-size: 0.9em;
}
</style>
