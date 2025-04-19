// src/services/ColorService.js
import axios from "axios";

const API_URL = "http://localhost:8080/admin/colors";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

const ColorService = {
  // ✅ Lấy danh sách tất cả màu
  getAllColors: async () => {
    try {
      const response = await axios.get(API_URL, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi lấy danh sách màu:", error);
      throw error;
    }
  },

  // ✅ Tìm kiếm màu theo tên
  searchColors: async (name) => {
    try {
      const response = await axios.get(`${API_URL}/search`, {
        headers: getAuthHeader(),
        params: { name },
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi tìm kiếm màu:", error);
      throw error;
    }
  },

  // ✅ Tạo mới màu
  createColor: async (colorData) => {
    try {
      const response = await axios.post(`${API_URL}/create`, colorData, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi tạo màu:", error);
      throw error;
    }
  },

  // ✅ Cập nhật màu
  updateColor: async (id, colorData) => {
    try {
      const response = await axios.put(`${API_URL}/update/${id}`, colorData, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi cập nhật màu:", error);
      throw error;
    }
  },

  // ✅ Xóa màu
  deleteColor: async (id) => {
    try {
      const response = await axios.delete(`${API_URL}/delete/${id}`, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi xóa màu:", error);
      throw error;
    }
  },
};

export default ColorService;
