package com.quafresh.web.aquafreshweb.service.admin;

import com.quafresh.web.aquafreshweb.dto.admin.InvoiceFullResponseDTO;

import java.util.List;

public interface InvoiceAdminService {
    List<InvoiceFullResponseDTO> getAll();
    List<Object[]> getInvoiceByInvoiceId(Integer invoiceId);
}
