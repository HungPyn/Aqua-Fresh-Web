<template>
  <div class="container-fluid px-4">
    <h2 class="text-center mb-4">Quản lý hãng</h2>

    <div class="row">
      <!-- Cột tìm kiếm -->
      <div class="col-md-3">
        <div class="card shadow-sm rounded mb-4">
          <div class="card-body">
            <label class="form-label">Tìm kiếm theo tên</label>
            <div class="input-group">
              <input
                v-model="keyword"
                type="text"
                class="form-control"
                placeholder="Nhập tên công ty"
                @input="handleSearch"
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

      <!-- Cột danh sách công ty -->
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
                  <th>Tên công ty</th>
                  <th>Trạng thái</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(company, index) in paginatedCompanies"
                  :key="company.id"
                >
                  <td>{{ index + 1 + (currentPage - 1) * perPage }}</td>
                  <td>{{ company.name }}</td>
                  <td>
                    <span
                      class="badge"
                      :class="company.status ? 'bg-success' : 'bg-secondary'"
                    >
                      {{ company.status ? "Kinh doanh" : "Ngừng kinh doanh" }}
                    </span>
                  </td>
                  <td>
                    <button
                      class="btn btn-sm btn-warning me-2"
                      @click="editCompany(company)"
                      title="Cập nhật"
                    >
                      <i class="bi bi-pencil-square"></i>
                    </button>
                  </td>
                </tr>
                <tr v-if="paginatedCompanies.length === 0">
                  <td colspan="4" class="text-center text-muted">
                    Không tìm thấy công ty nào.
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

    <!-- Modal thêm/sửa công ty -->
    <div
      class="modal fade"
      id="companyModal"
      tabindex="-1"
      aria-labelledby="companyModalLabel"
      aria-hidden="true"
      ref="modalRef"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="companyModalLabel">
              {{ mode === "create" ? "Thêm công ty" : "Cập nhật công ty" }}
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            />
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label">Tên công ty</label>
              <input v-model.trim="newCompany.name" class="form-control" />
              <div v-if="errors.name" class="text-danger mt-1">
                {{ errors.name }}
              </div>
            </div>
            <div class="mb-3">
              <label class="form-label d-block mb-2">Trạng thái</label>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  id="active-yes"
                  :value="true"
                  v-model="newCompany.status"
                />
                <label class="form-check-label" for="active-yes"
                  >Kinh doanh</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  class="form-check-input"
                  type="radio"
                  id="active-no"
                  :value="false"
                  v-model="newCompany.status"
                />
                <label class="form-check-label" for="active-no"
                  >Không kinh doanh</label
                >
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
              @click="mode === 'create' ? createCompany() : updateCompany()"
              :disabled="!newCompany.name"
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
import companyService from "@/services/CompanyService";
import { useToast } from "vue-toastification";
import { Modal } from "bootstrap";

const toast = useToast();
const modalRef = ref(null);
let modalInstance = null;

const allCompanies = ref([]); // Lưu toàn bộ danh sách
const companies = ref([]); // Danh sách hiển thị sau khi lọc
const keyword = ref("");
const currentPage = ref(1);
const perPage = 15;

const totalPages = computed(() => Math.ceil(companies.value.length / perPage));
const paginatedCompanies = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  return companies.value.slice(start, start + perPage);
});

const mode = ref("create");
const selectedCompany = ref(null);
const newCompany = ref({
  name: "",
  status: true,
});

const errors = ref({
  name: "",
});

const validateForm = () => {
  let isValid = true;
  errors.value.name = "";

  if (!newCompany.value.name.trim()) {
    errors.value.name = "Tên công ty không được để trống.";
    isValid = false;
  } else if (newCompany.value.name.length > 100) {
    errors.value.name = "Tên công ty không được vượt quá 100 ký tự.";
    isValid = false;
  }

  return isValid;
};

onMounted(async () => {
  await fetchCompanies();
  modalInstance = new Modal(modalRef.value);
});

const fetchCompanies = async () => {
  try {
    const res = await companyService.getAll();
    allCompanies.value = res;
    companies.value = [...res]; // Gán đầy đủ ban đầu
  } catch (error) {
    toast.error("Lỗi khi tải danh sách công ty!");
  }
};

const handleSearch = () => {
  const kw = keyword.value.trim().toLowerCase();
  companies.value = allCompanies.value.filter((company) =>
    company.name.toLowerCase().includes(kw)
  );
  currentPage.value = 1;
};

const resetSearch = () => {
  keyword.value = "";
  companies.value = [...allCompanies.value];
  currentPage.value = 1;
};

const openCreateModal = () => {
  mode.value = "create";
  newCompany.value = { name: "", status: true };
  errors.value.name = "";
  modalInstance.show();
};

const editCompany = (company) => {
  mode.value = "update";
  selectedCompany.value = company;
  newCompany.value = {
    name: company.name,
    status: Boolean(company.status),
  };
  errors.value.name = "";
  modalInstance.show();
};

const createCompany = async () => {
  if (!validateForm()) return;
  try {
    await companyService.create({
      name: newCompany.value.name,
      status: newCompany.value.status,
    });
    toast.success("Thêm công ty thành công!");
    await fetchCompanies();
    modalInstance.hide();
  } catch (error) {
    toast.error("Lỗi khi thêm công ty!");
  }
};

const updateCompany = async () => {
  if (!validateForm()) return;
  try {
    await companyService.update(selectedCompany.value.id, {
      name: newCompany.value.name,
      status:
        newCompany.value.status === true || newCompany.value.status === "true",
    });
    toast.success("Cập nhật công ty thành công!");
    await fetchCompanies();
    modalInstance.hide();
  } catch (error) {
    toast.error("Lỗi khi cập nhật công ty!");
  }
};
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
