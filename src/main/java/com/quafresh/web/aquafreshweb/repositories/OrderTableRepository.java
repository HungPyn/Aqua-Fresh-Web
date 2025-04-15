package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.OrderTable;
import com.quafresh.web.aquafreshweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTableRepository extends JpaRepository<OrderTable, Integer> {
  public List<OrderTable> findByIdUser(User user);
  }