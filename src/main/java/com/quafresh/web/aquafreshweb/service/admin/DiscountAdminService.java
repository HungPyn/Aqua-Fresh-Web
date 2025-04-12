package com.quafresh.web.aquafreshweb.service.admin;

import com.quafresh.web.aquafreshweb.dto.admin.DiscountAdminDTO;

import java.util.List;

public interface DiscountAdminService {
    DiscountAdminDTO getByID(Integer id);

    List<DiscountAdminDTO> getAll();

    List<DiscountAdminDTO> search(String keyword);

    DiscountAdminDTO create(DiscountAdminDTO discountAdminDTO);

    DiscountAdminDTO update(Integer id, DiscountAdminDTO discountAdminDTO);

    String delete(Integer id);
}
