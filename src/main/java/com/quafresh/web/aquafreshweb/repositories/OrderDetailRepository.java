package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.dto.guess.OrderDetailGuessDTO;
import com.quafresh.web.aquafreshweb.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetailGuessDTO> findByIdOrder_Id(Integer idOrder);
    List<OrderDetail> findByIdProductDetail_Id(Integer productDetailId);
}