package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.District;
import com.quafresh.web.aquafreshweb.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    public List<District> findByProvince (Province province);
}