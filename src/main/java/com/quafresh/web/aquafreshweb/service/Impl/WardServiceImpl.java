package com.quafresh.web.aquafreshweb.service.Impl;
import com.quafresh.web.aquafreshweb.entity.Ward;
import com.quafresh.web.aquafreshweb.repositories.WardRepository;
import com.quafresh.web.aquafreshweb.service.guess.WardServiceGuess;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WardServiceImpl implements WardServiceGuess {
    private final WardRepository wardRepository;
    @Override
    public ResponseEntity<List<Ward>> getAllAddress() {
        return new ResponseEntity<>(wardRepository.findAll(), HttpStatus.OK);
    }
}
