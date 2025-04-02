import "./assets/main.css";
import { createApp } from "vue";
import App from "./App.vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.bundle";
import router from "./router/router";
import axios from "axios";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";

// Thiết lập base URL cho Axios
axios.defaults.baseURL =
  import.meta.env.VITE_API_URL || "http://localhost:8080";

// Tạo ứng dụng Vue
const app = createApp(App);

// Gán Axios vào Vue để sử dụng trong các component
app.config.globalProperties.$axios = axios;

// Cấu hình Toastification
app.use(Toast, {
  position: "top-right",
  timeout: 3000,
  closeOnClick: true,
  pauseOnFocusLoss: true,
  pauseOnHover: true,
  draggable: true,
  draggablePercent: 0.6,
  showCloseButtonOnHover: false,
  hideProgressBar: false,
  closeButton: "button",
  icon: true,
  rtl: false,
});

// Sử dụng router
app.use(router);

// Mount ứng dụng vào DOM
app.mount("#app");
