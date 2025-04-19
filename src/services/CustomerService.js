import axios from "axios";

// ✅ Đặt đúng URL backend của bạn
const API_URL = "http://localhost:8080/admin/customers";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};
const CustomerService = {
  // ✅ Lấy danh sách tất cả khách hàng
  getAllCustomers: async () => {
    try {
      const response = await axios.get(API_URL, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi lấy danh sách khách hàng:", error);
      throw error;
    }
  },

  // ✅ Tìm kiếm khách hàng theo tên
  searchCustomers: async (name) => {
    try {
      const response = await axios.get(`${API_URL}/search`, {
        headers: getAuthHeader(),
        params: { name },
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi tìm kiếm khách hàng:", error);
      throw error;
    }
  },

  // ✅ Tạo mới khách hàng
  createCustomer: async (customerData) => {
    try {
      const response = await axios.post(`${API_URL}/create`, customerData, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi tạo khách hàng:", error);
      throw error;
    }
  },

  // ✅ Cập nhật thông tin khách hàng
  updateCustomer: async (id, customerData) => {
    try {
      const response = await axios.put(
        `${API_URL}/update/${id}`,
        customerData,
        {
          headers: getAuthHeader(),
        }
      );
      return response.data;
    } catch (error) {
      console.error("Lỗi khi cập nhật khách hàng:", error);
      throw error;
    }
  },

  // ✅ Xóa khách hàng
  deleteCustomer: async (id) => {
    try {
      const response = await axios.delete(`${API_URL}/delete/${id}`, {
        headers: getAuthHeader(),
      });
      return response.data;
    } catch (error) {
      console.error("Lỗi khi xóa khách hàng:", error);
      throw error;
    }
  },
};

export default CustomerService;
