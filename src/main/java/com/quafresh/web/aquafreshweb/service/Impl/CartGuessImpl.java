package com.quafresh.web.aquafreshweb.service.Impl;

import com.quafresh.web.aquafreshweb.dto.guess.CartGuessDTO;
import com.quafresh.web.aquafreshweb.entity.Cart;
import com.quafresh.web.aquafreshweb.entity.ProductDetail;
import com.quafresh.web.aquafreshweb.entity.User;
import com.quafresh.web.aquafreshweb.repositories.CartRepository;
import com.quafresh.web.aquafreshweb.repositories.ProductDetailRepository;
import com.quafresh.web.aquafreshweb.repositories.UserRepository;
import com.quafresh.web.aquafreshweb.service.guess.CartServiceGuess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartGuessImpl implements CartServiceGuess {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public ResponseEntity<List<Cart>> getCartByUserId(Integer id) {
        try {
            User user = userRepository.findById(id).get();
            return new ResponseEntity<>(cartRepository.getCartByIdUser(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Cart> save(CartGuessDTO cartGuessDTO) {
        try {
            ProductDetail productDetail = productDetailRepository.findById(cartGuessDTO.getIdProductDetail())
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));
            User user = userRepository.findById(cartGuessDTO.getIdUSer())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            Optional<Cart> existingCartOpt = cartRepository.findByIdUserAndIdProductDetail(user, productDetail);
            Cart cart;

            boolean status = cartGuessDTO.getStatus(); // true = cộng thêm, false = ghi đè

            if (existingCartOpt.isPresent()) {
                cart = existingCartOpt.get();
                switch (status ? 1 : 0) {
                    case 1:
                        cart.setQuantity(cart.getQuantity() + cartGuessDTO.getQuantity());
                        break;
                    case 0:
                        cart.setQuantity(cartGuessDTO.getQuantity());
                        break;
                }
                cart.setDateAdded(new Date().toInstant());
            } else {
                cart = new Cart();
                cart.setDateAdded(new Date().toInstant());
                cart.setIdProductDetail(productDetail);
                cart.setIdUser(user);
                cart.setQuantity(cartGuessDTO.getQuantity());
            }

            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Cart(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteById(Integer id) {
        try {
            cartRepository.deleteById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Delete failed", HttpStatus.NOT_FOUND);
        }
    }
}

