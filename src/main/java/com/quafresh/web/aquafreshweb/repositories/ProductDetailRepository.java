package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.dto.admin.ProductDetailAdminDTO;
import com.quafresh.web.aquafreshweb.dto.guess.BestSellingProductDTO;
import com.quafresh.web.aquafreshweb.entity.Product;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    @Query("SELECT pd FROM ProductDetail pd WHERE pd.codeProductDetail LIKE %:name%")
    List<ProductDetail> searchByProductName(@Param("keyword") String keyword);

//    List<ProductDetail> findByProductName(@Param("name") String name);
    @Query("""
    SELECT pd 
    FROM OrderDetail od 
    JOIN od.idOrder o 
    JOIN od.idProductDetail pd 
    WHERE o.status = 'Delivered'
    GROUP BY pd  
    ORDER BY SUM(od.quantity) DESC
""")
    List<BestSellingProductDTO> findBestSellingProductDetails();
}