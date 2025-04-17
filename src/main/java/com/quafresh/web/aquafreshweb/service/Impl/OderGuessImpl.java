package com.quafresh.web.aquafreshweb.service.Impl;

import com.quafresh.web.aquafreshweb.dto.guess.OrderDetailClientDTO;
import com.quafresh.web.aquafreshweb.dto.guess.OrderDetailGuessDTO;
import com.quafresh.web.aquafreshweb.entity.OrderDetail;
import com.quafresh.web.aquafreshweb.entity.OrderTable;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import com.quafresh.web.aquafreshweb.entity.User;
import com.quafresh.web.aquafreshweb.repositories.OrderDetailRepository;
import com.quafresh.web.aquafreshweb.repositories.OrderTableRepository;
import com.quafresh.web.aquafreshweb.repositories.ProductDetailRepository;
import com.quafresh.web.aquafreshweb.repositories.UserRepository;
import com.quafresh.web.aquafreshweb.service.guess.OderGuessService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OderGuessImpl implements OderGuessService {

    private final OrderTableRepository orderTableRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserRepository userRepository;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<String> addOrder(OrderDetailClientDTO orderDetailClientDTO) {
        try {
            // B1: Lấy thông tin user
            Optional<User> userOptional = userRepository.findById(orderDetailClientDTO.getIdUser());
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            User user = userOptional.get();

            // B2: Tạo đơn hàng (OrderTable)
            OrderTable orderTable = new OrderTable();
            orderTable.setOrderDate(new Date().toInstant());
            orderTable.setIdUser(user);
            orderTable.setStatus(orderDetailClientDTO.getStatus());
            orderTable.setShippingPrice(orderDetailClientDTO.getShippingPrice());
            orderTable.setTotal(orderDetailClientDTO.getTotal());
            orderTable = orderTableRepository.save(orderTable);

            // B3: Lưu các OrderDetail
            for (OrderDetailGuessDTO dto : orderDetailClientDTO.getDetailGuessDTOList()) {
                ProductDetail productDetail = productDetailRepository.findById(dto.getProductDetailId())
                        .orElseThrow(() -> new RuntimeException("ProductDetail with ID " + dto.getProductDetailId() + " not found"));

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setIdOrder(orderTable);
                orderDetail.setIdProductDetail(productDetail);
                orderDetail.setPrice(dto.getPrice());
                orderDetail.setQuantity(dto.getQuantity());

                orderDetailRepository.save(orderDetail);
            }
            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // ép rollback
            return ResponseEntity.status(500).body("Failed to create order: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<List<OrderDetailClientDTO>> getAll(Integer id) {
        List<OrderTable> orderTable = orderTableRepository.findByIdUser(userRepository.findById(id).get());
        List<OrderDetailClientDTO> orderDetailClientDTO =new ArrayList<>();
        for (OrderTable odtb : orderTable){
            OrderDetailClientDTO orderDetailClientDTO1 = new OrderDetailClientDTO();
            orderDetailClientDTO1.setIdUser(id);
            orderDetailClientDTO1.setShippingPrice(odtb.getShippingPrice());
            orderDetailClientDTO1.setStatus(odtb.getStatus());
            orderDetailClientDTO1.setTotal(odtb.getTotal());
            orderDetailClientDTO1.setDetailGuessDTOList(orderDetailRepository.findByIdOrder_Id(odtb.getId(),OrderDetailGuessDTO.class));
            orderDetailClientDTO.add(orderDetailClientDTO1);
        }
        return  ResponseEntity.ok(orderDetailClientDTO);
    }

    @Override
    public ResponseEntity<String> deleteOrderIfPending(Integer id) {
        Optional<OrderTable> optionalOrder = orderTableRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.badRequest().body("Order không tồn tại");
        }

        OrderTable order = optionalOrder.get();
        if (!"PENDING".equals(order.getStatus())) {
            return ResponseEntity.badRequest().body("Order không ở trạng thái PENDING");
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByIdOrder_Id(id, OrderDetail.class);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.badRequest().body("Null value in OrderDetails");
        }

        for (OrderDetail detail : orderDetails) {
            orderDetailRepository.deleteById(detail.getId());
        }

        orderTableRepository.deleteById(order.getId());

        return ResponseEntity.ok("Xoá thành công");
    }


}
