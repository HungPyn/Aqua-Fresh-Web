// src/services/companyService.js
import axios from "axios";

const API_URL = "http://localhost:8080/admin/technologies";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

const technologyService = {
  getAll: async () => {
    try {
      const response = await axios.get(API_URL, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi lấy danh sách ccông nghệ:", error);
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
      console.error("Lỗi khi tìm kiếm ccông nghệ:", error);
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
      console.error("Lỗi khi tạo Công nghệ:", error);
      throw error;
    }
  },

  update: async (id, data) => {
    try {
      const response = await axios.put(`${API_URL}/update/${id}`, data, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi cập nhật công nghệ:", error);
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
      console.error("Lỗi khi xóa công nghệ:", error);
      throw error;
    }
  },
};

export default technologyService;
