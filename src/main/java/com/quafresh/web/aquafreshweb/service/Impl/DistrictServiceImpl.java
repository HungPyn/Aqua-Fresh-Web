package com.quafresh.web.aquafreshweb.service.Impl;

import com.quafresh.web.aquafreshweb.dto.guess.IdRequest;
import com.quafresh.web.aquafreshweb.entity.District;
import com.quafresh.web.aquafreshweb.entity.Province;
import com.quafresh.web.aquafreshweb.repositories.DistrictRepository;
import com.quafresh.web.aquafreshweb.repositories.ProvinceRepository;
import com.quafresh.web.aquafreshweb.service.guess.DistrictServiceGuess;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictServiceGuess {

    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    @Override
    public ResponseEntity<List<District>> getDistrictByProvinceId(Integer provinceId) {
        Optional<Province> provinceOpt = provinceRepository.findById(provinceId);
        if (provinceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<District> districts = districtRepository.findByProvince(provinceOpt.get());
        return ResponseEntity.ok(districts);
    }

}
