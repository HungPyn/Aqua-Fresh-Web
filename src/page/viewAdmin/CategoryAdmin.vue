<template>
  <div class="container-fluid px-4">
    <h2 class="text-center mb-4">Quản lý danh mục</h2>

    <div class="row">
      <!-- Tìm kiếm -->
      <div class="col-md-4">
        <div class="card shadow-sm rounded mb-4">
          <div class="card-body">
            <div class="mb-3">
              <label class="form-label">Tìm kiếm</label>
              <div class="input-group input-group-sm">
                <input
                  v-model="keyword"
                  type="text"
                  class="form-control"
                  placeholder="Tên danh mục"
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
      </div>

      <!-- Bảng danh mục -->
      <div class="col-md-8 min-vh-100">
        <div class="d-flex justify-content-start mb-3">
          <button class="btn btn-primary" @click="openCreateModal">
            <i class="bi bi-plus-circle me-1"></i> Thêm
          </button>
        </div>

        <div class="card shadow-sm rounded animate__animated animate__fadeIn">
          <div class="card-body">
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th>#</th>
                  <th>Tên danh mục</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(category, index) in paginatedCategories"
                  :key="category.id"
                >
                  <td>{{ index + 1 + (currentPage - 1) * perPage }}</td>
                  <td>{{ category.name }}</td>
                  <td>
                    <span
                      :class="category.status ? 'text-success' : 'text-muted'"
                    >
                      {{ category.status ? "Kinh doanh" : "Ngừng kinh doanh" }}
                    </span>
                  </td>
                  <td>
                    <button
                      class="btn btn-sm btn-warning"
                      @click="editCategory(category)"
                    >
                      <i class="bi bi-pencil-square"></i>
                    </button>
                  </td>
                </tr>
                <tr v-if="paginatedCategories.length === 0">
                  <td colspan="4" class="text-center text-muted">
                    Không tìm thấy danh mục nào.
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
                  class="page-item"
                  v-for="page in totalPages"
                  :key="page"
                  :class="{ active: currentPage === page }"
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

    <!-- Modal -->
    <div class="modal fade" id="categoryModal" tabindex="-1" ref="modalRef">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              {{
                mode === "create" ? "Thêm danh mục mới" : "Cập nhật danh mục"
              }}
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Tên danh mục</label>
              <input
                v-model.trim="newCategory.name"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': isDuplicateName || !newCategory.name }"
              />
              <div class="invalid-feedback" v-if="!newCategory.name">
                Vui lòng nhập tên danh mục.
              </div>
              <div class="invalid-feedback" v-else-if="isDuplicateName">
                Tên danh mục đã tồn tại.
              </div>
            </div>
            <div class="mb-3">
              <label class="form-label">Trạng thái</label>
              <div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="active"
                    :value="true"
                    v-model="newCategory.status"
                  />
                  <label class="form-check-label" for="active">
                    Kinh doanh</label
                  >
                </div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="inactive"
                    :value="false"
                    v-model="newCategory.status"
                  />
                  <label class="form-check-label" for="inactive"
                    >Ngừng kinh doanh</label
                  >
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Đóng
            </button>
            <button
              type="button"
              class="btn btn-primary"
              :disabled="!newCategory.name || isDuplicateName"
              @click="mode === 'create' ? addCategory() : updateCategory()"
            >
              {{ mode === "create" ? "Thêm" : "Lưu thay đổi" }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { Modal } from "bootstrap";
import { useToast } from "vue-toastification";
import CategoryService from "@/services/CategoryService";

const toast = useToast();
const categories = ref([]);
const keyword = ref("");
const mode = ref("create");
const modalRef = ref(null);
let modalInstance = null;

const newCategory = ref({ name: "", status: true });
const selectedCategory = ref(null);
const currentPage = ref(1);
const perPage = 15;

const fetchCategories = async () => {
  try {
    const res = await CategoryService.getAll();
    categories.value = Array.isArray(res) ? res : [];
  } catch {
    toast.error("Lỗi khi tải danh sách danh mục!");
  }
};

// Tìm kiếm local
const filteredCategories = computed(() => {
  const kw = keyword.value.toLowerCase().trim();
  return categories.value.filter((c) => c.name.toLowerCase().includes(kw));
});

const paginatedCategories = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  return filteredCategories.value.slice(start, start + perPage);
});

const totalPages = computed(() =>
  Math.ceil(filteredCategories.value.length / perPage)
);

const resetSearch = () => {
  keyword.value = "";
  currentPage.value = 1;
};

const openCreateModal = () => {
  mode.value = "create";
  newCategory.value = { name: "", status: true };
  modalInstance.show();
};

const editCategory = (category) => {
  mode.value = "update";
  selectedCategory.value = category;
  newCategory.value = { ...category };
  modalInstance.show();
};

const isDuplicateName = computed(() => {
  const name = newCategory.value.name.trim().toLowerCase();
  return categories.value.some((c) => {
    const isSameName = c.name.trim().toLowerCase() === name;
    const isSameId =
      selectedCategory.value && c.id === selectedCategory.value.id;
    return isSameName && (mode.value === "create" || !isSameId);
  });
});

const addCategory = async () => {
  if (!newCategory.value.name || isDuplicateName.value) return;
  try {
    await CategoryService.create({ ...newCategory.value });
    toast.success("Thêm danh mục thành công!");
    await fetchCategories();
    modalInstance.hide();
  } catch {
    toast.error("Lỗi khi thêm danh mục!");
  }
};

const updateCategory = async () => {
  if (!newCategory.value.name || isDuplicateName.value) return;
  try {
    await CategoryService.update(selectedCategory.value.id, {
      ...newCategory.value,
    });
    toast.success("Cập nhật thành công!");
    await fetchCategories();
    modalInstance.hide();
  } catch {
    toast.error("Lỗi khi cập nhật!");
  }
};

onMounted(async () => {
  await fetchCategories();
  modalInstance = new Modal(modalRef.value);
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
.pagination .page-link {
  cursor: pointer;
}
.invalid-feedback {
  display: block;
}
</style>
