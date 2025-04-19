<template>
  <div class="container-fluid px-4">
    <h2 class="text-center mb-4">Quản lý sản phẩm</h2>

    <div class="row">
      <!-- Tìm kiếm -->
      <div class="col-md-3">
        <div class="card shadow-sm rounded mb-4">
          <div class="card-body">
            <label class="form-label">Tìm kiếm theo tên</label>
            <div class="input-group">
              <input
                v-model="keyword"
                type="text"
                class="form-control"
                placeholder="Nhập tên sản phẩm"
              />
              <button
                class="btn border-0 bg-transparent"
                @click="resetSearch"
                title="Làm mới"
              >
                <i class="bi bi-arrow-clockwise"></i>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Danh sách sản phẩm -->
      <div class="col-md-9 min-vh-100">
        <div class="d-flex justify-content-start mb-3">
          <button class="btn btn-primary" @click="openCreateModal">
            <i class="bi bi-plus-circle"></i> Thêm
          </button>
        </div>

        <div class="card shadow-sm rounded animate__animated animate__fadeIn">
          <div class="card-body">
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th>#</th>
                  <th>Tên sản phẩm</th>
                  <th>Danh mục</th>
                  <th>Hãng</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(product, index) in paginatedProducts"
                  :key="product.id"
                >
                  <td>{{ index + 1 + (currentPage - 1) * perPage }}</td>
                  <td>{{ product.name }}</td>
                  <td>{{ product.idCategory.name }}</td>
                  <td>{{ product.idCompany.name }}</td>
                  <td>
                    <span
                      :class="[
                        'badge',
                        product.status ? 'bg-success' : 'bg-secondary',
                      ]"
                    >
                      {{ product.status ? "Kinh doanh" : "Ngừng kinh doanh" }}
                    </span>
                  </td>
                  <td>
                    <button
                      class="btn btn-sm btn-warning"
                      @click="editProduct(product)"
                    >
                      <i class="bi bi-pencil-square"></i>
                    </button>
                  </td>
                </tr>
                <tr v-if="paginatedProducts.length === 0">
                  <td colspan="6" class="text-center text-muted">
                    Không tìm thấy sản phẩm nào.
                  </td>
                </tr>
              </tbody>
            </table>

            <!-- Phân trang -->
            <nav v-if="totalPages > 1">
              <ul class="pagination justify-content-center">
                <li class="page-item" :class="{ disabled: currentPage === 1 }">
                  <button
                    class="page-link"
                    @click="currentPage--"
                    :disabled="currentPage === 1"
                  >
                    <i class="bi bi-chevron-left"></i>
                  </button>
                </li>
                <li
                  v-for="page in totalPages"
                  :key="page"
                  :class="['page-item', { active: currentPage === page }]"
                >
                  <button class="page-link" @click="currentPage = page">
                    {{ page }}
                  </button>
                </li>
                <li
                  class="page-item"
                  :class="{ disabled: currentPage === totalPages }"
                >
                  <button
                    class="page-link"
                    @click="currentPage++"
                    :disabled="currentPage === totalPages"
                  >
                    <i class="bi bi-chevron-right"></i>
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal Thêm/Sửa -->
    <div
      class="modal fade"
      id="productModal"
      ref="modalRef"
      tabindex="-1"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              {{ mode === "create" ? "Thêm sản phẩm" : "Cập nhật sản phẩm" }}
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Tên sản phẩm</label>
              <input v-model="newProduct.name" class="form-control" />
            </div>

            <!-- Danh mục -->
            <div class="mb-3">
              <label class="form-label">Danh mục</label>
              <select v-model="newProduct.idCategory" class="form-select">
                <option disabled value="">Chọn danh mục</option>
                <option v-for="cat in categories" :key="cat.id" :value="cat.id">
                  {{ cat.name }}
                </option>
              </select>
            </div>

            <!-- Hãng -->
            <div class="mb-3">
              <label class="form-label">Hãng</label>
              <select v-model="newProduct.idCompany" class="form-select">
                <option disabled value="">Chọn hãng</option>
                <option v-for="com in companies" :key="com.id" :value="com.id">
                  {{ com.name }}
                </option>
              </select>
            </div>

            <!-- Trạng thái -->
            <div class="mb-3">
              <label class="form-label d-block mb-2">Trạng thái</label>
              <div class="form-check form-check-inline">
                <input
                  type="radio"
                  class="form-check-input"
                  id="status-yes"
                  :value="true"
                  v-model="newProduct.status"
                />
                <label for="status-yes" class="form-check-label"
                  >Kinh doanh</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  type="radio"
                  class="form-check-input"
                  id="status-no"
                  :value="false"
                  v-model="newProduct.status"
                />
                <label for="status-no" class="form-check-label"
                  >Ngừng kinh doanh</label
                >
              </div>
            </div>
          </div>

          <div class="modal-footer">
            <button class="btn btn-secondary" data-bs-dismiss="modal">
              Đóng
            </button>
            <button class="btn btn-primary" @click="submitForm">
              {{ mode === "create" ? "Thêm" : "Cập nhật" }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { Modal } from "bootstrap";
import { useToast } from "vue-toastification";
import productService from "@/services/ProductService";
import categoryService from "@/services/CategoryService";
import companyService from "@/services/CompanyService";

const toast = useToast();
const modalRef = ref(null);
let modalInstance = null;

const products = ref([]);
const categories = ref([]);
const companies = ref([]);

const keyword = ref("");
const currentPage = ref(1);
const perPage = 15;

const mode = ref("create");
const selectedProduct = ref(null);
const newProduct = ref({
  name: "",
  idCategory: "",
  idCompany: "",
  status: true,
});

const filteredProducts = computed(() => {
  if (!keyword.value) return products.value;
  return products.value.filter((p) =>
    p.name.toLowerCase().includes(keyword.value.toLowerCase())
  );
});

const totalPages = computed(() =>
  Math.ceil(filteredProducts.value.length / perPage)
);

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  return filteredProducts.value.slice(start, start + perPage);
});

const fetchProducts = async () => {
  try {
    products.value = await productService.getAll();
  } catch {
    toast.error("Không thể tải danh sách sản phẩm.");
  }
};

const fetchCompanies = async () => {
  try {
    companies.value = await companyService.getAll();
  } catch {
    toast.error("Không thể tải danh sách công ty.");
  }
};

const fetchCategories = async () => {
  try {
    categories.value = await categoryService.getAll();
  } catch {
    toast.error("Không thể tải danh sách danh mục.");
  }
};

const resetSearch = () => {
  keyword.value = "";
};

const openCreateModal = () => {
  mode.value = "create";
  selectedProduct.value = null;
  newProduct.value = {
    name: "",
    idCategory: "",
    idCompany: "",
    status: true,
  };
  modalInstance.show();
};

const editProduct = (product) => {
  mode.value = "update";
  selectedProduct.value = product;
  newProduct.value = {
    name: product.name,
    idCategory: product.idCategory.id,
    idCompany: product.idCompany.id,
    status: Boolean(product.status),
  };
  modalInstance.show();
};

const submitForm = async () => {
  try {
    const payload = {
      name: newProduct.value.name,
      idCategory: { id: newProduct.value.idCategory },
      idCompany: { id: newProduct.value.idCompany },
      status: newProduct.value.status,
    };

    if (mode.value === "create") {
      await productService.create(payload);
      toast.success("Thêm sản phẩm thành công!");
    } else {
      await productService.update(selectedProduct.value.id, payload);
      toast.success("Cập nhật sản phẩm thành công!");
    }

    await fetchProducts();
    modalInstance.hide();
  } catch {
    toast.error("Có lỗi xảy ra!");
  }
};

onMounted(async () => {
  modalInstance = new Modal(modalRef.value);
  await fetchCompanies();
  await fetchCategories();
  await fetchProducts();
});
</script>

<style scoped>
.table th,
.table td {
  vertical-align: middle;
}
.min-vh-100 {
  min-height: 100vh;
}
</style>
