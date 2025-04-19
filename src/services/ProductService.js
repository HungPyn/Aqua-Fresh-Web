import axios from "axios";

const API_URL = "http://localhost:8080/admin/products";

const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

export default {
  getAll: async () => {
    const res = await axios.get(`${API_URL}`, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  search: async (keyword) => {
    const res = await axios.get(`${API_URL}/search`, {
      headers: getAuthHeader(),
      params: { keyword },
    });
    return res.data;
  },

  getById: async (id) => {
    const res = await axios.get(`${API_URL}/detail/${id}`, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  create: async (data) => {
    const res = await axios.post(`${API_URL}/create`, data, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  update: async (id, data) => {
    const res = await axios.put(`${API_URL}/update/${id}`, data, {
      headers: getAuthHeader(),
    });
    return res.data;
  },

  delete: async (id) => {
    const res = await axios.delete(`${API_URL}/delete/${id}`, {
      headers: getAuthHeader(),
    });
    return res.data;
  },
};
