<template>
  <div class="container-fluid px-4">
    <h2 class="text-center mb-4">Quản lý công nghệ</h2>

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
                  placeholder="Tên công nghệ"
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

      <!-- Bảng công nghệ -->
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
                  <th>Tên công nghệ</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(tech, index) in paginatedTechnologies"
                  :key="tech.id"
                >
                  <td>{{ index + 1 + (currentPage - 1) * perPage }}</td>
                  <td>{{ tech.name }}</td>
                  <td>
                    <span :class="tech.status ? 'text-success' : 'text-muted'">
                      {{ tech.status ? "Kinh doanh" : "Ngừng kinh doanh" }}
                    </span>
                  </td>
                  <td>
                    <button
                      class="btn btn-sm btn-warning"
                      @click="editTechnology(tech)"
                    >
                      <i class="bi bi-pencil-square"></i>
                    </button>
                  </td>
                </tr>
                <tr v-if="paginatedTechnologies.length === 0">
                  <td colspan="4" class="text-center text-muted">
                    Không tìm thấy công nghệ nào.
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
    <div class="modal fade" id="techModal" tabindex="-1" ref="modalRef">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              {{
                mode === "create" ? "Thêm công nghệ mới" : "Cập nhật công nghệ"
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
              <label class="form-label">Tên công nghệ</label>
              <input
                v-model.trim="newTechnology.name"
                type="text"
                class="form-control"
                :class="{
                  'is-invalid': isDuplicateName || !newTechnology.name,
                }"
              />
              <div class="invalid-feedback" v-if="!newTechnology.name">
                Vui lòng nhập tên công nghệ.
              </div>
              <div class="invalid-feedback" v-else-if="isDuplicateName">
                Tên công nghệ đã tồn tại.
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
                    v-model="newTechnology.status"
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
                    v-model="newTechnology.status"
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
              :disabled="!newTechnology.name || isDuplicateName"
              @click="mode === 'create' ? addTechnology() : updateTechnology()"
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
import TechnologyService from "@/services/TechnologyService";

const toast = useToast();
const technologies = ref([]);
const keyword = ref("");
const mode = ref("create");
const modalRef = ref(null);
let modalInstance = null;

const newTechnology = ref({ name: "", status: true });
const selectedTechnology = ref(null);
const currentPage = ref(1);
const perPage = 15;

const fetchTechnologies = async () => {
  try {
    const res = await TechnologyService.getAll();
    technologies.value = Array.isArray(res) ? res : [];
  } catch {
    toast.error("Lỗi khi tải danh sách công nghệ!");
  }
};

// Tìm kiếm local
const filteredTechnologies = computed(() => {
  const kw = keyword.value.toLowerCase().trim();
  return technologies.value.filter((t) => t.name.toLowerCase().includes(kw));
});

const paginatedTechnologies = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  return filteredTechnologies.value.slice(start, start + perPage);
});

const totalPages = computed(() =>
  Math.ceil(filteredTechnologies.value.length / perPage)
);

const resetSearch = () => {
  keyword.value = "";
  currentPage.value = 1;
};

const openCreateModal = () => {
  mode.value = "create";
  newTechnology.value = { name: "", status: true };
  modalInstance.show();
};

const editTechnology = (tech) => {
  mode.value = "update";
  selectedTechnology.value = tech;
  newTechnology.value = { ...tech };
  modalInstance.show();
};

const isDuplicateName = computed(() => {
  const name = newTechnology.value.name.trim().toLowerCase();
  return technologies.value.some((t) => {
    const isSameName = t.name.trim().toLowerCase() === name;
    const isSameId =
      selectedTechnology.value && t.id === selectedTechnology.value.id;
    return isSameName && (mode.value === "create" || !isSameId);
  });
});

const addTechnology = async () => {
  if (!newTechnology.value.name || isDuplicateName.value) return;
  try {
    await TechnologyService.create(newTechnology.value);
    toast.success("Thêm công nghệ thành công!");
    await fetchTechnologies();
    modalInstance.hide();
  } catch {
    toast.error("Lỗi khi thêm công nghệ!");
  }
};

const updateTechnology = async () => {
  if (!newTechnology.value.name || isDuplicateName.value) return;
  try {
    await TechnologyService.update(
      selectedTechnology.value.id,
      newTechnology.value
    );
    toast.success("Cập nhật thành công!");
    await fetchTechnologies();
    modalInstance.hide();
  } catch {
    toast.error("Lỗi khi cập nhật!");
  }
};

onMounted(async () => {
  await fetchTechnologies();
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
