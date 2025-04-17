package com.quafresh.web.aquafreshweb.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueStatDTO {
    private String date; // yyyy-MM-dd hoặc yyyy-MM
    private Double totalRevenue;
    private Long totalOrders;
}
