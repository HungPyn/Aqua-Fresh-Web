package com.quafresh.web.aquafreshweb.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class InvoiceDetailDTO {
    private Integer orderDetailId;
    private Integer quantity;
    private BigDecimal price;
    private String codeProductDetail;
    private String productName;
}
