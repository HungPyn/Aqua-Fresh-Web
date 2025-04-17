package com.quafresh.web.aquafreshweb.service.guess;

import com.quafresh.web.aquafreshweb.dto.guess.OrderDetailClientDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface OderGuessService {
    public ResponseEntity<String> addOrder(OrderDetailClientDTO orderDetailClientDTO);
    ResponseEntity<List<OrderDetailClientDTO>> getAll(Integer id);

    ResponseEntity<String> deleteOrderIfPending(Integer id);
}
