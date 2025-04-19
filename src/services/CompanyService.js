// src/services/companyService.js
import axios from "axios";

const API_URL = "http://localhost:8080/admin/companies";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

const companyService = {
  // ✅ Lấy danh sách tất cả công ty
  getAll: async () => {
    try {
      const response = await axios.get(API_URL, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi lấy danh sách công ty:", error);
      throw error;
    }
  },

  // ✅ Tìm kiếm công ty theo tên
  search: async (name) => {
    try {
      const response = await axios.get(`${API_URL}/search`, {
        headers: getAuthHeader(),
        params: { name },
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi tìm kiếm công ty:", error);
      throw error;
    }
  },

  // ✅ Tạo mới công ty
  create: async (data) => {
    try {
      const response = await axios.post(`${API_URL}/create`, data, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi tạo công ty:", error);
      throw error;
    }
  },

  // ✅ Cập nhật công ty
  update: async (id, data) => {
    try {
      const response = await axios.put(`${API_URL}/update/${id}`, data, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi cập nhật công ty:", error);
      throw error;
    }
  },

  // ✅ Xóa công ty
  remove: async (id) => {
    try {
      const response = await axios.delete(`${API_URL}/delete/${id}`, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi xóa công ty:", error);
      throw error;
    }
  },
};

export default companyService;
