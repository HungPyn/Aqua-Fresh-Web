import { createWebHistory, createRouter } from "vue-router";
import Controllers from "@/page/view/Controllers.vue";
import Discount from "@/page/view/Discount.vue";
import GioHang from "@/page/view/GioHang.vue";
import Whisthlist from "@/page/view/Whisthlist.vue";
import DiscountAdmin from "@/page/viewAdmin/DiscountAdmin.vue";
import DonHangAdmin from "@/page/viewAdmin/DonHangAdmin.vue";
import HoaDonAdmin from "@/page/viewAdmin/HoaDonAdmin.vue";
import ProductAdmin from "@/page/viewAdmin/ProductAdmin.vue";
import UserAdmin from "@/page/viewAdmin/UserAdmin.vue";
import ProductDetail from "@/components/ProductDetail.vue";
import Login from "@/page/Login.vue";
import TimKiem from "@/components/TimKiem.vue";
import DonHang from "@/page/view/DonHang.vue";
import ThanhToan from "@/components/ThanhToan.vue";
import ColorsAdmin from "@/page/viewAdmin/ColorsAdmin.vue";
import CompanyAdmin from "@/page/viewAdmin/CompanyAdmin.vue";
import TechnologyAdmin from "@/page/viewAdmin/TechnologyAdmin.vue";
import CategoryAdmin from "@/page/viewAdmin/CategoryAdmin.vue";
import ProductDetailAdmin from "@/page/viewAdmin/ProductDetailAdmin.vue";

const routes = [
  { path: "/", redirect: "/home" }, // Chuyển hướng mặc định
  { path: "/home", name: "home", component: Controllers },
  { path: "/timKiem", name: "timKiem", component: TimKiem },
  { path: "/donHang", name: "donHang", component: DonHang },
  { path: "/thanhToan", name: "thanhToan", component: ThanhToan },
  { path: "/login", name: "login", component: Login },
  { path: "/discount", name: "discount", component: Discount },
  { path: "/gioHang", name: "gioHang", component: GioHang },
  { path: "/whishlist", name: "whishlist", component: Whisthlist },
  { path: "/customer-admin", name: "userAdmin", component: UserAdmin },
  { path: "/product-admin", name: "productAdmin", component: ProductAdmin },
  { path: "/hoaDonAdmin", name: "hoaDonAdmin", component: HoaDonAdmin },
  { path: "/donHangAdmin", name: "donHangAdmin", component: DonHangAdmin },
  { path: "/discountAdmin", name: "discountAdmin", component: DiscountAdmin },
  { path: "/:pathMatch(.*)*", redirect: "/home" },
  { path: "/color-admin", name: "color-admin", component: ColorsAdmin },
  { path: "/company-admin", name: "company-admin", component: CompanyAdmin },
  { path: "/category-admin", name: "category-admin", component: CategoryAdmin },
  {
    path: "/technogoly-admin",
    name: "technogoly-admin",
    component: TechnologyAdmin,
  },
  {
    path: "/product-detail-admin",
    name: "product-detail-admin",
    component: ProductDetailAdmin,
  },
  {
    path: "/productDetail/:id", // Định nghĩa route có tham số ":id"
    name: "ProductDetail",
    component: ProductDetail,
    props: true, // Cho phép truyền id dưới dạng props vào component
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,

  scrollBehavior(to, from, savedPosition) {
    // Nếu có hash trong URL, cuộn đến phần tử có id đó
    if (to.hash) {
      const element = document.querySelector(to.hash);
      if (element) {
        const offset = 10; // Chiều cao của navbar cố định
        const elementTop = element.getBoundingClientRect().top + window.scrollY;
        window.history.pushState(
          "",
          document.title,
          window.location.pathname + window.location.search
        );
        return { top: elementTop - offset, behavior: "smooth" }; // Cuộn đến vị trí đã điều chỉnh
      }
    }

    // Nếu không có hash, hãy giữ lại vị trí cuộn đã lưu
    if (savedPosition) {
      return savedPosition; // Quay lại vị trí đã lưu trước đó
    }

    // Mặc định: cuộn lên đầu trang
    return { top: 0, behavior: "smooth" };
  },
});

export default router;
