package com.quafresh.web.aquafreshweb.service.guess;

import com.quafresh.web.aquafreshweb.dto.guess.IdRequest;
import com.quafresh.web.aquafreshweb.entity.District;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DistrictServiceGuess {
     List<District> getDistrictsByProvinceApiId(Integer id);
}
