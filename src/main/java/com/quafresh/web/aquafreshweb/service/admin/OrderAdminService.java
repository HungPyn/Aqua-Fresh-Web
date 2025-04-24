package com.quafresh.web.aquafreshweb.service.admin;

import com.quafresh.web.aquafreshweb.dto.admin.OderAdminDTO;

import java.util.List;

public interface OrderAdminService {

    List<OderAdminDTO> getAll();
    OderAdminDTO search(String keyword);
    OderAdminDTO update(Integer id, OderAdminDTO dto);
    String Delete(Integer id);

}
