package com.quafresh.web.aquafreshweb.service.Impl;
import com.quafresh.web.aquafreshweb.dto.guess.IdRequest;
import com.quafresh.web.aquafreshweb.entity.District;
import com.quafresh.web.aquafreshweb.entity.Ward;
import com.quafresh.web.aquafreshweb.repositories.DistrictRepository;
import com.quafresh.web.aquafreshweb.repositories.WardRepository;
import com.quafresh.web.aquafreshweb.service.guess.WardServiceGuess;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WardServiceImpl implements WardServiceGuess {
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    @Override
    public ResponseEntity<List<Ward>> getWardByDistrictId(Integer id) {
        District district = districtRepository.findById(id).get();
        return ResponseEntity.ok(wardRepository.findByDistrict(district));
    }

    @Override
    public ResponseEntity<Ward> getWard(Integer id) {
        return ResponseEntity.ok(wardRepository.findById(id).get());
    }
}
