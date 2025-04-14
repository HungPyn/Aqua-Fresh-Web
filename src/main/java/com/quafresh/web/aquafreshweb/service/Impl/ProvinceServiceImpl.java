package com.quafresh.web.aquafreshweb.service.Impl;

import com.quafresh.web.aquafreshweb.entity.Province;
import com.quafresh.web.aquafreshweb.repositories.ProvinceRepository;
import com.quafresh.web.aquafreshweb.service.guess.ProvinceServiceGuess;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceServiceGuess {

    private final ProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<List<Province>> getAllProvince() {
        return ResponseEntity.ok(provinceRepository.findAll());
    }
}
