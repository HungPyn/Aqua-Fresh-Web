package com.quafresh.web.aquafreshweb.dto.guess;

import com.quafresh.web.aquafreshweb.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailClientDTO {
    private BigDecimal total;
    private Integer idUser;
    private String status;
    private BigDecimal shippingPrice;
    private List<OrderDetailGuessDTO> detailGuessDTOList;
    public OrderDetailClientDTO(OrderDetail orderDetail, List<OrderDetailGuessDTO> list) {
        this.total = orderDetail.getPrice();
        this.idUser = orderDetail.getIdOrder().getIdUser().getId();
        this.status = orderDetail.getIdOrder().getStatus();
        this.detailGuessDTOList = list;
        this.shippingPrice = orderDetail.getIdOrder().getShippingPrice();
    }
}
