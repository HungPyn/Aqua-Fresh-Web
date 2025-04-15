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
    public BigDecimal updateTotalQuantity(Integer orderId) {
        List<OrderDetail> details = orderDetailRepository.findByIdOrder_Id(orderId);

        return details.stream()
                .map(detail -> detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
