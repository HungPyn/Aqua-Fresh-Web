package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.District;
import com.quafresh.web.aquafreshweb.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    public List<Ward> findByDistrict (District district);

    List<Ward> findByDistrict_DistrictIdApi(Integer districtId);
}