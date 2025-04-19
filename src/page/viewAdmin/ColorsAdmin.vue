<template>
  <div class="container-fluid px-4">
    <h2 class="text-center mb-4">Quản lý màu sắc</h2>

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
                  placeholder="Tên màu"
                  @input="searchColors"
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

      <!-- Bảng màu -->
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
                  <th>Tên màu</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(color, index) in paginatedColors" :key="color.id">
                  <td>{{ index + 1 + (currentPage - 1) * perPage }}</td>
                  <td>{{ color.name }}</td>
                  <td>
                    <span :class="color.status ? 'text-success' : 'text-muted'">
                      {{ color.status ? "Kinh doanh" : "Ngừng kinh doanh" }}
                    </span>
                  </td>
                  <td>
                    <button
                      class="btn btn-sm btn-warning"
                      @click="editColor(color)"
                    >
                      <i class="bi bi-pencil-square"></i>
                    </button>
                  </td>
                </tr>
                <tr v-if="paginatedColors.length === 0">
                  <td colspan="4" class="text-center text-muted">
                    Không tìm thấy màu nào.
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
    <div class="modal fade" id="colorModal" tabindex="-1" ref="modalRef">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              {{ mode === "create" ? "Thêm màu mới" : "Cập nhật màu" }}
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Tên màu</label>
              <input
                v-model.trim="newColor.name"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': isDuplicateName || !newColor.name }"
              />
              <div class="invalid-feedback" v-if="!newColor.name">
                Vui lòng nhập tên màu.
              </div>
              <div class="invalid-feedback" v-else-if="isDuplicateName">
                Tên màu đã tồn tại.
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
                    v-model="newColor.status"
                  />
                  <label class="form-check-label" for="active"
                    >Kinh doanh</label
                  >
                </div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="inactive"
                    :value="false"
                    v-model="newColor.status"
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
              :disabled="!newColor.name || isDuplicateName"
              @click="mode === 'create' ? addColor() : updateColor()"
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
import ColorService from "@/services/ColorService";

const toast = useToast();
const colors = ref([]);
const keyword = ref("");
const mode = ref("create");
const modalRef = ref(null);
let modalInstance = null;

const newColor = ref({ name: "", status: true });
const selectedColor = ref(null);
const currentPage = ref(1);
const perPage = 15;

const fetchColors = async () => {
  try {
    const res = await ColorService.getAllColors();
    colors.value = Array.isArray(res) ? res : [];
  } catch {
    toast.error("Lỗi khi tải danh sách màu!");
  }
};

const paginatedColors = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  return colors.value.slice(start, start + perPage);
});

const totalPages = computed(() => Math.ceil(colors.value.length / perPage));

const searchColors = async () => {
  try {
    const res = await ColorService.searchColors(keyword.value);
    colors.value = Array.isArray(res) ? res : [];
    currentPage.value = 1;
  } catch {
    toast.error("Lỗi khi tìm kiếm!");
  }
};

const resetSearch = async () => {
  keyword.value = "";
  await fetchColors();
};

const openCreateModal = () => {
  mode.value = "create";
  newColor.value = { name: "", status: true };
  modalInstance.show();
};

const editColor = (color) => {
  mode.value = "update";
  selectedColor.value = color;
  newColor.value = { ...color };
  modalInstance.show();
};

const isDuplicateName = computed(() => {
  const name = newColor.value.name.trim().toLowerCase();
  return colors.value.some((c) => {
    const isSameName = c.name.trim().toLowerCase() === name;
    const isSameId = selectedColor.value && c.id === selectedColor.value.id;
    return isSameName && (mode.value === "create" || !isSameId);
  });
});

const addColor = async () => {
  if (!newColor.value.name || isDuplicateName.value) return;
  try {
    await ColorService.createColor({ ...newColor.value });
    toast.success("Thêm màu thành công!");
    await fetchColors();
    modalInstance.hide();
  } catch {
    toast.error("Lỗi khi thêm màu!");
  }
};

const updateColor = async () => {
  if (!newColor.value.name || isDuplicateName.value) return;
  try {
    await ColorService.updateColor(selectedColor.value.id, {
      ...newColor.value,
    });
    toast.success("Cập nhật thành công!");
    await fetchColors();
    modalInstance.hide();
  } catch {
    toast.error("Lỗi khi cập nhật!");
  }
};

onMounted(async () => {
  await fetchColors();
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
