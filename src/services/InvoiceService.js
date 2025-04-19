import axios from "axios";

const API_URL = "/invoice"; // Cập nhật đường dẫn nếu cần

// Lấy tất cả hóa đơn
const getAllInvoices = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data; // Trả về danh sách hóa đơn
  } catch (error) {
    console.error("Error fetching invoices:", error);
    throw new Error("Không thể tải danh sách hóa đơn.");
  }
};

// Lấy chi tiết hóa đơn theo ID
const getInvoiceById = async (invoiceId) => {
  try {
    const response = await axios.get(`${API_URL}/${invoiceId}`);
    return response.data; // Trả về chi tiết hóa đơn
  } catch (error) {
    console.error("Error fetching invoice details:", error);
    throw new Error("Không thể tải chi tiết hóa đơn.");
  }
};

export default {
  getAllInvoices,
  getInvoiceById,
};
