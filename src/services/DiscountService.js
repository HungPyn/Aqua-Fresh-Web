import axios from "axios";

const API_BASE_URL = "http://localhost:8080/discounts";

const DiscountService = {
  getAll() {
    return axios.get(API_BASE_URL);
  },

  getById(id) {
    return axios.get(`${API_BASE_URL}/${id}`);
  },

  create(discount) {
    return axios.post(API_BASE_URL, discount);
  },

  update(id, discount) {
    return axios.put(`${API_BASE_URL}/${id}`, discount);
  },

  delete(id) {
    return axios.delete(`${API_BASE_URL}/${id}`);
  },
};

export default DiscountService;
