<template>
  <div class="container-fluid px-5">
    <div class="row">
      <div class="text-center bg-light rounded-3 shadow-sm py-3 mb-4">
        <h4 class="fw-bold text-dark m-0">Danh Sách Sản Phẩm</h4>
      </div>
      <div class="col-lg-2">
        <h5 class="mb-3 text-start">Bộ lọc</h5>
        <div class="mb-3">
          <input
            class="form-control"
            placeholder="Tên sản phẩm.."
            v-model="duLieu"
            @keyup.enter="timKiem"
            @input="timKiem"
          />
        </div>
        <div class="mb-3">
          <input
            class="form-control"
            placeholder="Số lượng.."
            v-model="soLuong"
            @keyup.enter="timKiem"
            @input="timKiem"
          />
        </div>
        <div class="mb-3">
          <label for="vaiTro" class="form-label text-start">Danh mục</label>
          <select class="form-control" @change="timKiem" v-model="danhMuc">
            <option value="">Tất cả</option>
            <option v-for="ct in categorys" :key="ct.id" :value="ct.id">
              {{ ct.name }}
            </option>
          </select>
        </div>
        <div class="mb-3">
          <label for="vaiTro" class="form-label text-start">Giá</label>
          <select class="form-control" @change="setGia" v-model="gia">
            <option value="">Tất cả</option>
            <option value="0">Dưới 3 triệu</option>
            <option value="1">3 triệu - 7 triệu</option>
            <option value="2">Trên 7 triệu</option>
          </select>
        </div>
      </div>

      <div class="col-lg-7">
        <div
          v-for="product in products"
          :key="product.id"
          class="card mb-3 shadow-sm"
        >
          <div class="row g-0 align-items-center">
            <div class="col-md-4 text-center p-2">
              <img
                :src="product.image"
                class="img-fluid rounded"
                alt="Hình ảnh sản phẩm"
                style="max-height: 150px; object-fit: cover"
              />
            </div>
            <div class="col-md-8">
              <div class="card-body">
                <h5 class="card-title fw-bold">{{ product.name }}</h5>
                <p class="card-text text-muted">{{ product.description }}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <small class="text-body-secondary"
                    >Ngày thêm: <b>{{ product.createAt }}</b></small
                  >
                  <small class="text-body-secondary"
                    >Số lượng: <b>{{ product.quantity }}</b></small
                  >
                  <small class="text-danger fw-bold"
                    >Giá:
                    {{ Number(product.price).toLocaleString("vi-VN") }}
                    vnđ</small
                  >
                </div>
                <div class="mt-2 d-flex justify-content-end">
                  <button
                    class="btn btn-warning btn-sm"
                    @click="deleteProduct(product.id)"
                  >
                    Xóa
                  </button>
                  <button
                    class="btn btn-success btn-sm ms-2"
                    @click="detailUpdate(product)"
                  >
                    Sửa
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center text-danger" v-if="products.length === 0">
          <b><h5>Không tìm thấy kết quả</h5></b>
          <br /><br /><br />
        </div>

        <nav aria-label="Pagination">
          <ul class="pagination justify-content-center">
            <li class="page-item disabled"><a class="page-link">Trước</a></li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">Sau</a></li>
          </ul>
        </nav>
      </div>

      <div class="col-lg-3">
        <div class="card shadow p-3">
          <h5 class="fw-bold text-center">
            {{ isShow ? "Cập nhật sản phẩm" : "Thêm sản phẩm mới" }}
          </h5>
          <div class="mb-3">
            <label class="form-label">Tên sản phẩm</label>
            <input
              type="text"
              class="form-control"
              v-model="newProduct.name"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Giá</label>
            <input
              type="number"
              class="form-control"
              v-model="newProduct.price"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Số lượng</label>
            <input
              type="number"
              class="form-control"
              v-model="newProduct.quantity"
              min="1"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Ảnh</label>
            <input
              type="file"
              class="form-control"
              @change="saveImage($event)"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Danh mục</label>
            <select v-model="newProduct.category" class="form-select">
              <option v-for="ct in categorys" :key="ct.id" :value="ct.id">
                {{ ct.name }}
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Mô tả</label>
            <textarea
              class="form-control"
              v-model="newProduct.description"
              rows="3"
              required
            ></textarea>
          </div>
          <div class="text-center">
            <button
              v-if="isShow"
              class="btn btn-secondary me-2"
              @click="previousAdd"
            >
              Hủy
            </button>
            <button v-if="!isShow" class="btn btn-primary" @click="addProduct">
              Thêm sản phẩm
            </button>
            <button v-else class="btn btn-success" @click="updateProduct">
              Cập nhật
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useToast } from "vue-toastification";
import Swal from "sweetalert2";

//tim kiếm
const duLieu = ref("");
const danhMuc = ref("");
const soLuong = ref("");
const gia = ref("");
const gia1 = ref("");
const gia2 = ref("");
const setGia = () => {
  if (gia.value === "0") {
    gia1.value = "0";
    gia2.value = "3000000";
  } else if (gia.value === "1") {
    gia1.value = "3000000";
    gia2.value = "7000000";
  } else if (gia.value === "2") {
    gia1.value = "7000000";
    gia2.value = "100000000";
  } else {
    gia1.value = "";
    gia2.value = "";
  }
  timKiem();
};

const timKiem = async () => {
  try {
    const res = await axios("http://localhost:8080/products/search", {
      params: {
        duLieu: duLieu.value,
        danhMuc: danhMuc.value,
        gia1: gia1.value,
        gia2: gia2.value,
        soLuong: soLuong.value,
      },
    });
    console.log(res.data);
    products.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tim product", error);
  }
};

// tro ve
const previousAdd = () => {
  resetNewProduct();
  isShow.value = false;
};

//thong bao
const toast = useToast();

// category lấy ra;
const categorys = ref([]);
//product để lấy ra
const products = ref([]);
//hàm lưu ảnh vào biến
const fileImage = ref(null);
const saveImage = (image) => {
  fileImage.value = image.target.files[0];
};
// upda nahr lên
const uploadImage = async () => {
  if (!fileImage.value) return null;

  const formData = new FormData();
  formData.append("image", fileImage.value);

  try {
    const response = await axios.post(
      "https://api.imgbb.com/1/upload",
      formData,
      {
        params: {
          key: "5eda1e9473b687a7d46d23eb95dc4d13", // API Key ImgBB
        },
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );

    // Lấy link ảnh từ phản hồi API
    const imageUrl = response.data.data.url;
    console.log("Ảnh đã upload thành công:", imageUrl);
    return imageUrl;
  } catch (error) {
    console.error("Lỗi upload ảnh:", error.response?.data || error.message);
    return null;
  }
};
// hàm new để gán vào dom
const newProduct = ref({
  id: null,
  name: "",
  price: null,
  quantity: null,
  image: "",
  description: "",
  category: 2,
  createAt: null,
});
//resert newProduct
const resetNewProduct = () => {
  newProduct.value = {
    id: null,
    name: "",
    price: null,
    quantity: null,
    image: "",
    description: "",
    category: 2,
    createAt: null,
  };
};

//lấy category
const getCategory = async () => {
  try {
    const res = await axios.get(
      "http://localhost:8080/products/hien-thi/category"
    );
    console.log(res.data);
    categorys.value = res.data;
  } catch (error) {
    console.error("Lỗi khi gọi category:", error);
  }
};
// lấy productproduct
const getProduct = async () => {
  try {
    const res = await axios.get("http://localhost:8080/products/hien-thi");
    console.log(res.data);
    products.value = res.data;
  } catch (error) {
    console.error("Lỗi khi gọi product:", error);
  }
};
onMounted(() => {
  getCategory();
  getProduct();
});

// tạo iswhow để hiện update
const isShow = ref(false);
// tạo hàm showUpdate để hiện update
const detailUpdate = (product) => {
  newProduct.value = { ...product, category: product.category?.id };
  isShow.value = true;
};
//chewck form
const validateForm = (isEdit = false) => {
  if (!newProduct.value.name.trim()) {
    toast.error("Tên sản phẩm không được để trống.");
    return false;
  }
  if (!newProduct.value.price || newProduct.value.price <= 0) {
    toast.error("Giá sản phẩm phải lớn hơn 0.");
    return false;
  }
  if (!newProduct.value.quantity || newProduct.value.quantity <= 0) {
    toast.error("Số lượng sản phẩm phải lớn hơn 0.");
    return false;
  }
  if (!isEdit && !fileImage.value) {
    // Thêm mới: bắt buộc chọn ảnh
    toast.error("Vui lòng chọn ảnh sản phẩm.");
    return false;
  } else if (isEdit && !fileImage.value && !newProduct.value.image) {
    // Cập nhật: không có ảnh cũ và không chọn mới
    toast.error("Vui lòng chọn ảnh sản phẩm.");
    return false;
  }

  if (!newProduct.value.description.trim()) {
    toast.error("Mô tả sản phẩm không được để trống.");
    return false;
  }
  if (!newProduct.value.category) {
    toast.error("Vui lòng chọn danh mục sản phẩm.");
    return false;
  }
  return true;
};
// hàm add
const addProduct = async () => {
  if (!validateForm(true)) return;

  Swal.fire({
    title: "Bạn có chắc muốn thêm sản phẩm này?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Có",
    cancelButtonText: "Không",
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        // Upload ảnh
        const imageUrl = await uploadImage();
        if (!imageUrl) {
          toast.error("Không thể upload ảnh.");
          return;
        }

        // Tạo sản phẩm mới
        const productData = {
          id: null,
          name: newProduct.value.name,
          price: newProduct.value.price,
          quantity: newProduct.value.quantity,
          image: imageUrl,
          description: newProduct.value.description,
          category: { id: newProduct.value.category },
          createAt: new Date().toISOString().split("T")[0],
        };
        console.log("Dữ liệu trước khi thêm:", productData);
        console.log("Dữ liệu JSON:", JSON.stringify(productData, null, 2));

        // Gửi yêu cầu thêm
        const res = await axios.post(
          "http://localhost:8080/products/add",
          productData
        );
        toast.success("Thêm sản phẩm thành công!");
        timKiem(); // Làm mới danh sách
        resetNewProduct(); // Reset form
        fileImage.value = null;
      } catch (error) {
        toast.error("Thêm sản phẩm thất bại.");
        console.error(error);
      }
    }
  });
};

//ham xoa
const deleteProduct = async (id) => {
  if (!id) {
    toast.error("ID không hợp lệ!");
    return;
  }

  Swal.fire({
    title: "Bạn có muốn xóa không?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Có",
    cancelButtonText: "Không",
  }).then(async (result) => {
    // Cần async ở đây vì có await bên trong
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/products/delete/${id}`);
        toast.success("Xóa thành công!");
        timKiem(); // Load lại danh sách sau khi xóa
      } catch (error) {
        console.error("Lỗi khi xóa sản phẩm:", error);
        toast.error("Xóa thất bại!");
      }
    }
  });
};

//hamf update
const updateProduct = async () => {
  if (!validateForm(true)) return;

  Swal.fire({
    title: "Bạn có chắc muốn cập nhật sản phẩm này?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Có",
    cancelButtonText: "Không",
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        let imageUrl = newProduct.value.image; // Giữ nguyên URL ảnh cũ

        // Nếu người dùng chọn ảnh mới, upload và cập nhật URL
        if (fileImage.value) {
          const uploadedUrl = await uploadImage();
          if (!uploadedUrl) {
            toast.error("Không thể upload ảnh.");
            return;
          }
          imageUrl = uploadedUrl; // Cập nhật URL ảnh mới
        }

        // Tạo dữ liệu sản phẩm để update
        const productData = {
          id: newProduct.value.id, // ID sản phẩm cần cập nhật
          name: newProduct.value.name,
          price: newProduct.value.price,
          quantity: newProduct.value.quantity,
          image: imageUrl, // Giữ ảnh cũ hoặc ảnh mới
          description: newProduct.value.description,
          category: { id: newProduct.value.category },
          createAt: newProduct.value.createAt,
        };

        console.log("Dữ liệu trước khi update:", productData);
        console.log("Dữ liệu JSON:", JSON.stringify(productData, null, 2));

        // Gửi yêu cầu cập nhật
        const res = await axios.put(
          `http://localhost:8080/products/update`,
          productData
        );

        toast.success("Cập nhật sản phẩm thành công!");
        timKiem(); // Làm mới danh sách sản phẩm
        resetNewProduct(); // Reset form
        fileImage.value = null;
      } catch (error) {
        toast.error("Cập nhật sản phẩm thất bại.");
        console.error(error);
      }

      isShow.value = false;
    }
  });
};

// phân trang
const page = ref(1);
</script>
