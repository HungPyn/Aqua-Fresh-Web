<template>
  <div class="container-fluid px-4">
    <h2 class="text-center mb-4">Quản lý sản phẩm chi tiết</h2>

    <div class="mb-3 row">
      <div class="col-md-6">
        <input
          v-model="keyword"
          class="form-control"
          type="text"
          placeholder="Tìm kiếm theo tên sản phẩm..."
        />
      </div>
      <div class="col-md-6 text-end">
        <button class="btn btn-primary" @click="openCreateModal">
          <i class="bi bi-plus-circle"></i> Thêm sản phẩm chi tiết
        </button>
      </div>
    </div>

    <div class="card shadow-sm rounded">
      <div class="card-body table-responsive">
        <table class="table table-hover align-middle">
          <thead>
            <tr>
              <th>#</th>
              <th>Tên sản phẩm</th>
              <th>Mã sản phẩm</th>
              <th>Màu</th>
              <th>Công nghệ</th>
              <th>Giá</th>
              <th>Số lượng</th>
              <th>Trạng thái</th>
              <th>Ảnh</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(detail, index) in filteredDetails" :key="detail.id">
              <td>{{ index + 1 }}</td>
              <td>{{ detail.idProduct.name }}</td>
              <td>{{ detail.codeProductDetail }}</td>
              <td>{{ detail.idColor.name }}</td>
              <td>{{ detail.idTechnology.name }}</td>
              <td>{{ detail.price.toLocaleString() }} đ</td>
              <td>{{ detail.quantity }}</td>
              <td>
                <span :class="detail.status ? 'text-success' : 'text-muted'">
                  {{ detail.status ? "Đang bán" : "Hết hàng" }}
                </span>
              </td>
              <td>
                <img
                  v-if="detail.listUrl && detail.listUrl.length > 0"
                  :src="detail.listUrl[0].url"
                  width="50"
                  height="50"
                  class="rounded border"
                />
              </td>
              <td>
                <button
                  class="btn btn-sm btn-warning"
                  @click="editDetail(detail)"
                >
                  <i class="bi bi-pencil-square"></i>
                </button>
              </td>
            </tr>
            <tr v-if="filteredDetails.length === 0">
              <td colspan="10" class="text-center text-muted">
                Không tìm thấy sản phẩm chi tiết.
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="detailModal" tabindex="-1" ref="modalRef">
      <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">
              {{
                mode === "create"
                  ? "Thêm sản phẩm chi tiết"
                  : "Cập nhật sản phẩm chi tiết"
              }}
            </h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body row">
            <div class="col-md-6">
              <label class="form-label">Tên sản phẩm</label>
              <select v-model="form.idProduct" class="form-select">
                <option disabled value="">Chọn sản phẩm</option>
                <option v-for="p in products" :key="p.id" :value="p.id">
                  {{ p.name }}
                </option>
              </select>

              <label class="form-label mt-2">Mã sản phẩm</label>
              <input v-model="form.codeProductDetail" class="form-control" />

              <label class="form-label mt-2">Màu sắc</label>
              <select v-model="form.idColor" class="form-select">
                <option disabled value="">Chọn màu sắc</option>
                <option v-for="c in colors" :key="c.id" :value="c.id">
                  {{ c.name }}
                </option>
              </select>

              <label class="form-label mt-2">Công nghệ</label>
              <select v-model="form.idTechnology" class="form-select">
                <option disabled value="">Chọn công nghệ</option>
                <option v-for="t in technologies" :key="t.id" :value="t.id">
                  {{ t.name }}
                </option>
              </select>
            </div>

            <div class="col-md-6">
              <label class="form-label">Giá</label>
              <input v-model="form.price" type="number" class="form-control" />

              <label class="form-label mt-2">Số lượng</label>
              <input
                v-model="form.quantity"
                type="number"
                class="form-control"
              />

              <label class="form-label mt-2">Mô tả</label>
              <textarea
                v-model="form.description"
                class="form-control"
              ></textarea>

              <label class="form-label mt-2">Trạng thái</label>
              <div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="status1"
                    :value="true"
                    v-model="form.status"
                  />
                  <label class="form-check-label" for="status1">Đang bán</label>
                </div>
                <div class="form-check form-check-inline">
                  <input
                    class="form-check-input"
                    type="radio"
                    id="status2"
                    :value="false"
                    v-model="form.status"
                  />
                  <label class="form-check-label" for="status2">Hết hàng</label>
                </div>
              </div>

              <label class="form-label mt-2">Ảnh</label>
              <!-- Input ảnh -->
              <div class="d-flex flex-column gap-2">
                <input
                  type="file"
                  multiple
                  @change="handleFileUpload"
                  class="form-control"
                />

                <!-- Hiển thị ảnh -->
                <div class="d-flex flex-wrap gap-2">
                  <div
                    v-for="(img, index) in form.listUrl"
                    :key="img.url"
                    class="image-preview"
                  >
                    <img :src="img.url" />
                    <button
                      type="button"
                      class="image-remove-btn"
                      @click="removeImage(index)"
                    >
                      <i class="bi bi-x"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" data-bs-dismiss="modal">
              Đóng
            </button>
            <button class="btn btn-primary" @click="saveDetail">
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
import ProductDetailService from "@/services/ProductDetailService";
import ProductService from "@/services/ProductService";
import ColorService from "@/services/ColorService";
import TechnologyService from "@/services/TechnologyService";

const toast = useToast();

const details = ref([]);
const products = ref([]);
const colors = ref([]);
const technologies = ref([]);

const keyword = ref("");
const modalRef = ref(null);
let modalInstance = null;

const mode = ref("create");
const currentId = ref(null);
const form = ref({
  idProduct: "",
  codeProductDetail: "",
  idColor: "",
  idTechnology: "",
  price: 0,
  quantity: 0,
  description: "",
  status: true,
  listUrl: [],
});

const fetchAll = async () => {
  details.value = await ProductDetailService.getAll();
  products.value = await ProductService.getAll();
  colors.value = await ColorService.getAllColors();
  technologies.value = await TechnologyService.getAll();

  details.value.forEach((d) => {
    d.status = d.status === true || d.status === "true" || d.status === 1;
  });
};

const filteredDetails = computed(() =>
  details.value.filter((d) =>
    (d.idProduct?.name ?? "")
      .toLowerCase()
      .includes((keyword.value ?? "").toLowerCase())
  )
);

const openCreateModal = () => {
  mode.value = "create";
  currentId.value = null;
  form.value = {
    idProduct: "",
    codeProductDetail: "",
    idColor: "",
    idTechnology: "",
    price: 0,
    quantity: 0,
    description: "",
    status: true,
    listUrl: [],
  };
  modalInstance.show();
};

const editDetail = (detail) => {
  mode.value = "edit";
  currentId.value = detail.id;
  form.value = {
    ...detail,
    idProduct: detail.idProduct.id,
    idColor: detail.idColor.id,
    idTechnology: detail.idTechnology.id,
    listUrl: [...detail.listUrl], // đảm bảo reactive
  };
  modalInstance.show();
};

const saveDetail = async () => {
  try {
    if (
      !form.value.idProduct ||
      !form.value.idColor ||
      !form.value.idTechnology ||
      form.value.price <= 0
    ) {
      toast.warning("Vui lòng nhập đầy đủ và hợp lệ các trường thông tin!");
      return;
    }

    const payload = {
      ...form.value,
      idProduct: { id: form.value.idProduct },
      idColor: { id: form.value.idColor },
      idTechnology: { id: form.value.idTechnology },
      status:
        form.value.status === true ||
        form.value.status === "true" ||
        form.value.status === 1,
    };

    if (mode.value === "create") {
      await ProductDetailService.create(payload);
      toast.success("Thêm thành công");
    } else {
      await ProductDetailService.update(currentId.value, payload);
      toast.success("Cập nhật thành công");
    }

    await fetchAll();
    modalInstance.hide();
  } catch (err) {
    toast.error("Lỗi khi lưu dữ liệu");
    console.error(err);
  }
};

const handleFileUpload = async (event) => {
  const files = event.target.files;
  for (let file of files) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("upload_preset", "hungvu");
    try {
      const res = await fetch(
        "https://api.cloudinary.com/v1_1/de6p22cld/image/upload",
        {
          method: "POST",
          body: formData,
        }
      );
      const data = await res.json();
      if (data.secure_url) {
        form.value.listUrl.push({ url: data.secure_url });
      } else {
        toast.warning("Tải ảnh thất bại!");
      }
    } catch (error) {
      toast.error("Lỗi khi upload ảnh!");
    }
  }
};

const removeImage = async (index) => {
  // Nếu đang ở chế độ sửa và ảnh đã có (tức là đã tồn tại trong CSDL)
  if (mode.value === "edit" && form.value.listUrl[index]?.url) {
    try {
      await ProductDetailService.deleteImage(currentId.value, index);
      toast.success("Xóa ảnh thành công!");
    } catch (error) {
      toast.error("Lỗi khi xóa ảnh!");
      return; // không xóa ở frontend nếu backend lỗi
    }
  }
  form.value.listUrl.splice(index, 1); // xóa ảnh khỏi mảng
};

onMounted(async () => {
  modalInstance = new Modal(modalRef.value);
  await fetchAll();
});
</script>

<style scoped>
.image-preview {
  position: relative;
  margin: 5px;
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  width: 80px;
  height: 80px;
  animation: fadeIn 0.3s ease-in-out;
  transition: transform 0.2s ease-in-out;
}

.image-preview:hover {
  transform: scale(1.03);
}

.image-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-remove-btn {
  position: absolute;
  top: 4px;
  right: 4px;
  background-color: #6c757d; /* Màu xám */
  color: white;
  border: 1px solid white;
  border-radius: 50%;
  padding: 2px 6px;
  font-size: 0.75rem;
  line-height: 1;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.image-remove-btn:hover {
  background-color: #495057; /* xám đậm hơn */
}

.table th,
.table td {
  vertical-align: middle;
}
img {
  object-fit: cover;
}
.image-preview {
  position: relative;
  animation: fadeIn 0.3s ease-in-out;
}
.image-remove-btn {
  position: absolute;
  top: 0;
  right: 0;
  transform: translate(50%, -50%);
  background-color: red;
  color: white;
  border: none;
  border-radius: 50%;
  padding: 0 6px;
  font-size: 0.75rem;
  line-height: 1;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
