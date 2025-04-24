package com.quafresh.web.aquafreshweb.service.impladmin;

import com.quafresh.web.aquafreshweb.dto.admin.InvoiceDetailDTO;
import com.quafresh.web.aquafreshweb.dto.admin.InvoiceFullResponseDTO;
import com.quafresh.web.aquafreshweb.repositories.InvoiceRepository;
import com.quafresh.web.aquafreshweb.service.admin.InvoiceAdminService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class  InvoiceAdminImple implements InvoiceAdminService {
    private final InvoiceRepository invoiceRepository;
    @Override
    public List<InvoiceFullResponseDTO> getAll() {
        List<Object[]> results = invoiceRepository.getFullInvoiceList();

        Map<Integer, InvoiceFullResponseDTO> invoiceMap = new LinkedHashMap<>();

        for (Object[] row : results) {
            Integer invoiceId = (Integer) row[0];

            InvoiceFullResponseDTO dto = invoiceMap.get(invoiceId);
            if (dto == null) {
                dto = new InvoiceFullResponseDTO();
                dto.setInvoiceId(invoiceId);
                dto.setInvoiceDate((LocalDateTime) row[1]);
                dto.setInvoiceTotal((BigDecimal) row[2]);
                dto.setPaymentMethod((String) row[3]);
                dto.setPaymentStatus((String) row[4]);
                dto.setStatusInvoice((String) row[5]);

                dto.setOrderId((Integer) row[6]);
                dto.setOrderDate((LocalDateTime) row[7]);
                dto.setOrderTotal((BigDecimal) row[8]);
                dto.setShippingPrice((BigDecimal) row[9]);
                dto.setOrderStatus((String) row[10]);

                dto.setCustomerName((String) row[11]);
                dto.setEmail((String) row[12]);
                dto.setPhoneNumber((String) row[13]);

                dto.setOrderDetails(new ArrayList<>());
                invoiceMap.put(invoiceId, dto);
            }

            InvoiceDetailDTO detail = new InvoiceDetailDTO();
            detail.setOrderDetailId((Integer) row[14]);
            detail.setQuantity((Integer) row[15]);
            detail.setPrice((BigDecimal) row[16]);
            detail.setCodeProductDetail((String) row[17]);
            detail.setProductName((String) row[18]);

            dto.getOrderDetails().add(detail);
        }

        return new ArrayList<>(invoiceMap.values());
    }


    @Override
    // Lấy hóa đơn theo id hóa đơn
    public List<Object[]> getInvoiceByInvoiceId(Integer invoiceId) {
        return invoiceRepository.findInvoiceById(invoiceId);
    }
}
