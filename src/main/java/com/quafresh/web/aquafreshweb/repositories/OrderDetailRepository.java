package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.dto.guess.OrderDetailGuessDTO;
import com.quafresh.web.aquafreshweb.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
     <T> List<T> findByIdOrder_Id(Integer idOrder,Class<T> type);
    List<OrderDetail> findByIdProductDetail_Id(Integer productDetailId);
}