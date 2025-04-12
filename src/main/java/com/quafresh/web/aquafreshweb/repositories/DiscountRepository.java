package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.Discount;
import com.quafresh.web.aquafreshweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    @Query("SELECT d from Discount d where d.discountName like %:name%")
    List<Discount> findByName(@Param("name") String name);
}
