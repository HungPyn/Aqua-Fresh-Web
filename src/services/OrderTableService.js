import axios from "axios";

const BASE_URL = "http://localhost:8080/admin/ordertable";

// Hàm lấy header với token
const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Token không hợp lệ hoặc không tồn tại");
  }
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

const OrderService = {
  // Lấy tất cả các đơn hàng
  getAll: async () => {
    const res = await axios.get(BASE_URL, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  // Cập nhật thông tin đơn hàng
  updateOrder: async (id, orderData) => {
    const res = await axios.put(`${BASE_URL}/update/${id}`, orderData, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  // Lấy đơn hàng theo ID
  getOrderById: async (id) => {
    const res = await axios.get(`${BASE_URL}/${id}`, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  // Xóa đơn hàng
  deleteOrder: async (id) => {
    const res = await axios.delete(`${BASE_URL}/delete/${id}`, {
      headers: getAuthHeader(),
    });
    return res.data;
  },
};

export default OrderService;
