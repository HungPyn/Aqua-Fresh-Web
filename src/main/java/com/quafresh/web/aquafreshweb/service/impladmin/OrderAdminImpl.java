package com.quafresh.web.aquafreshweb.service.impladmin;

import com.quafresh.web.aquafreshweb.dto.admin.OderAdminDTO;
import com.quafresh.web.aquafreshweb.entity.OrderTable;
import com.quafresh.web.aquafreshweb.repositories.OrderTableRepository;
import com.quafresh.web.aquafreshweb.service.admin.OrderAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderAdminImpl implements OrderAdminService {
    private final OrderTableRepository orderTableRepository;

    private  OderAdminDTO convertToDto(OrderTable orderTable) {
        OderAdminDTO dto = new OderAdminDTO();
        dto.setId(orderTable.getId());
        dto.setTotal(orderTable.getTotal());
        dto.setOderDate(orderTable.getOrderDate());
        dto.setShippingPrice(orderTable.getShippingPrice());
        dto.setStatus(orderTable.getStatus());
        return dto;
    }
    @Override
    public List<OderAdminDTO> getAll() {
        List<OrderTable> list = orderTableRepository.findAll();
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public OderAdminDTO search(String keyword) {
        return null;
    }

    @Override
    public OderAdminDTO update(Integer id, OderAdminDTO dto) {
        OrderTable orderTable = orderTableRepository.findById(id).get();
        orderTable.setStatus(dto.getStatus());
        orderTableRepository.save(orderTable);
        return convertToDto(orderTable);
    }

    @Override
    public String Delete(Integer id) {
        OrderTable orderTable = orderTableRepository.findById(id).get();
        orderTableRepository.delete(orderTable);
        return "Deleted Successfully";
    }
}
