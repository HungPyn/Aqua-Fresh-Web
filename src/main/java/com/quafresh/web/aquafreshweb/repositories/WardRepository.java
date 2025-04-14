package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.District;
import com.quafresh.web.aquafreshweb.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    public List<Ward> findByDistrict (District district);
    public Ward findByWardName (String address);
}