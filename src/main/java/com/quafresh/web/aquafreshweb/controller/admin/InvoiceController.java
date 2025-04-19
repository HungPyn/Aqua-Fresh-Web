package com.quafresh.web.aquafreshweb.controller.admin;

import com.quafresh.web.aquafreshweb.dto.admin.InvoiceFullResponseDTO;
import com.quafresh.web.aquafreshweb.repositories.InvoiceRepository;
import com.quafresh.web.aquafreshweb.service.impladmin.InvoiceAdminImple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/invoice")
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class InvoiceController {

    private  final InvoiceAdminImple invoiceAdminImple;

    @GetMapping
    public ResponseEntity<List<InvoiceFullResponseDTO>> getAllInvoices() {
        return ResponseEntity.ok(invoiceAdminImple.getAll());
    }
    // Endpoint tìm hóa đơn theo id
    @GetMapping("/{invoiceId}")
    public List<Object[]> getInvoiceByInvoiceId(@PathVariable Integer invoiceId) {
        return invoiceAdminImple.getInvoiceByInvoiceId(invoiceId);
    }
}
