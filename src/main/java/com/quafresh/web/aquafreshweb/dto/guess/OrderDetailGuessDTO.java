package com.quafresh.web.aquafreshweb.dto.guess;

import com.quafresh.web.aquafreshweb.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailGuessDTO {
    private BigDecimal price;
    private Integer quantity;
    private Integer productDetailId;
    public OrderDetailGuessDTO(OrderDetail orderDetail) {
        this.productDetailId = orderDetail.getIdProductDetail().getId();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
    }
}
