package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.Cart;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import com.quafresh.web.aquafreshweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    public List<Cart> getCartByIdUser (User user);
    public Boolean existsCartByIdProductDetail(ProductDetail productDetail);
    public Cart findCartByIdProductDetail (ProductDetail productDetail);

    Optional<Cart> findByIdUserAndIdProductDetail(User user, ProductDetail productDetail);
}