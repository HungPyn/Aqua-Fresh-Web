package com.quafresh.web.aquafreshweb.service.guess;

import com.quafresh.web.aquafreshweb.entity.Province;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProvinceServiceGuess {
    public ResponseEntity<List<Province>> getAllProvince();
}
