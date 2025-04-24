package com.quafresh.web.aquafreshweb.controller.admin;

import com.quafresh.web.aquafreshweb.dto.admin.OderAdminDTO;
import com.quafresh.web.aquafreshweb.service.impladmin.OrderAdminImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/ordertable")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class OrderTableController {

    private final OrderAdminImpl orderAdmin;

    @GetMapping
    public ResponseEntity<List<OderAdminDTO>> getAllOrderTables() {
        return ResponseEntity.ok(orderAdmin.getAll());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OderAdminDTO> updateOrderTable(@PathVariable Integer id,@RequestBody OderAdminDTO oderAdminDTO) {
        return ResponseEntity.ok(orderAdmin.update(id, oderAdminDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrderTable(@PathVariable Integer id) {
        return ResponseEntity.ok(orderAdmin.Delete(id));
    }
}
