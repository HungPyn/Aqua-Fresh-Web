package com.quafresh.web.aquafreshweb.service.guess;

import com.quafresh.web.aquafreshweb.dto.guess.IdRequest;
import com.quafresh.web.aquafreshweb.entity.Ward;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WardServiceGuess {
    public ResponseEntity<List<Ward>> getWardByDistrictId(Integer id);
    public ResponseEntity<Ward> getWard(Integer id);
}
