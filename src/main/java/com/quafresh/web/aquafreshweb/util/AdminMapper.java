package com.quafresh.web.aquafreshweb.util;

import com.quafresh.web.aquafreshweb.dto.LoginRequestDTO;
import com.quafresh.web.aquafreshweb.dto.admin.*;
import com.quafresh.web.aquafreshweb.entity.*;
import jakarta.persistence.criteria.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AdminMapper{

    @Mappings({@Mapping(source = "productName", target = "name")})
    ProductAdminDTO toProductAdminDTO(Product product);
    OderAdminDTO toOrderAdminDTO(Order order);
    ColorAdminDtO toColorAdminDTO(Color color);

    TechnologyAdminDTO toTechnologyAdminDTO(Technology tech);

    DiscountAdminDTO toDiscountAdminDTO(Discount discount);

    PictureAdimDTO toPictureAdminDTO(Picture picture);

    ProductDetailAdminDTO toProductDetailAdminDTO(ProductDetail productDetail);

    CompanyAdminDTO toCompanyAdminDTO(Company company);

    CategoryAdminDTO toCategoryAdminDTO(Category category);
     CustomerDTO toCustomerDTO(User customer);
     LoginRequestDTO toLoginRequestDTO(User user);
}

