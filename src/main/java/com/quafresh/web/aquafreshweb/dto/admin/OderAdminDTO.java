package com.quafresh.web.aquafreshweb.dto.admin;

import com.quafresh.web.aquafreshweb.common.contrains.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OderAdminDTO {
    private int id;
    private CustomerDTO idUser;
    private Instant oderDate;
    private BigDecimal total;
    private BigDecimal ShippingPrice;
    private String Status;
}
