import axios from "axios";

// Base path riêng cho các API liên quan đến order
const BASE_URL = "/admin/ordertable";

// API key hoặc token có thể lấy từ localStorage hoặc bất kỳ đâu
const token = localStorage.getItem("token");

const headers = {
  Authorization: `Bearer ${token}`,
};

const OrderService = {
  getAllOrders() {
    return axios.get(`${BASE_URL}`, { headers });
  },

  updateOrder(id, orderData) {
    return axios.put(`${BASE_URL}/update/${id}`, orderData, { headers });
  },

  getOrderById(id) {
    return axios.get(`${BASE_URL}/${id}`, { headers });
  },

  deleteOrder(id) {
    return axios.delete(`${BASE_URL}/delete/${id}`, { headers });
  },
};

export default OrderService;
