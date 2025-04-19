package com.quafresh.web.aquafreshweb.controller.guess;

import com.quafresh.web.aquafreshweb.dto.guess.OrderDetailClientDTO;
import com.quafresh.web.aquafreshweb.entity.OrderDetail;
import com.quafresh.web.aquafreshweb.service.Impl.OderGuessImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
@AllArgsConstructor
@RestController
public class OrderController {
    private final OderGuessImpl orOderGuess;
    //Oder khi co User
    @PostMapping
    public ResponseEntity<String> addOrder (@RequestBody OrderDetailClientDTO orderDetailClientDTO){
        return  orOderGuess.addOrder(orderDetailClientDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDetailClientDTO>> getAllOrderTableById(@PathVariable Integer id){
        return orOderGuess.getAll(id);
    }
    @PostMapping("/{id}")
    public ResponseEntity<String> deleteOrderIfPending(@PathVariable Integer id){
        return orOderGuess.deleteOrderIfPending(id);
    }
}
