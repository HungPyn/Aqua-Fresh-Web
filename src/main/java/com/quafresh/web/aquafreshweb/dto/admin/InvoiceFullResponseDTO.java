package com.quafresh.web.aquafreshweb.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InvoiceFullResponseDTO {
    private Integer invoiceId;
    private LocalDateTime invoiceDate;
    private BigDecimal invoiceTotal;
    private String paymentMethod;
    private String paymentStatus;

    private Integer orderId;
    private LocalDateTime orderDate;
    private BigDecimal orderTotal;
    private BigDecimal shippingPrice;
    private String orderStatus;

    private String customerName;
    private String email;
    private String phoneNumber;
    private String StatusInvoice;
    private List<InvoiceDetailDTO> orderDetails;
}
