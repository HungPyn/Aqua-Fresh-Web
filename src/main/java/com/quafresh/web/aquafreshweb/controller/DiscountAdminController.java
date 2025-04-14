package com.quafresh.web.aquafreshweb.controller;

import com.quafresh.web.aquafreshweb.dto.admin.DiscountAdminDTO;
import com.quafresh.web.aquafreshweb.service.impladmin.DiscountAdminImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/discount")
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class DiscountAdminController {
    private final DiscountAdminImpl discountAdminImpl;

    @GetMapping
    public ResponseEntity<List<DiscountAdminDTO>> getAll() {
        return ResponseEntity.ok(discountAdminImpl.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiscountAdminDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(discountAdminImpl.search(keyword));
    }

    @PostMapping("/create")
    public ResponseEntity<DiscountAdminDTO> create(@RequestBody DiscountAdminDTO discountAdminDTO) {
        return ResponseEntity.ok(discountAdminImpl.create(discountAdminDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DiscountAdminDTO> update(@PathVariable Integer id, @RequestBody DiscountAdminDTO discountAdminDTO) {
        return ResponseEntity.ok(discountAdminImpl.update(id, discountAdminDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(discountAdminImpl.delete(id));
    }
}
