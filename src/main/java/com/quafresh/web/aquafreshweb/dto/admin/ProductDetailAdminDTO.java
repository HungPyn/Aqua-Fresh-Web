package com.quafresh.web.aquafreshweb.dto.admin;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;


@Builder
public class ProductDetailAdminDTO {
    private Integer id;
    private ProductAdminDTO idProduct;
    private BigDecimal price;
    private Integer quantity;
    private ColorAdminDtO idColor;
    private TechnologyAdminDTO idTechnology;
    private DiscountAdminDTO idDiscount;
    private String description;
    private Boolean status;
    private List<PictureAdimDTO> listUrl;
}
