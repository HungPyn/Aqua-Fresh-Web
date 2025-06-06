package com.quafresh.web.aquafreshweb.dto.guess;

import com.quafresh.web.aquafreshweb.entity.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@NoArgsConstructor
public class CartGuessDTO {
    private Integer idProductDetail;
    private Integer idUSer;
    private Integer quantity;
    private Boolean status;
    public CartGuessDTO(Cart cart,Boolean status) {
        this.quantity = cart.getQuantity();
        this.idProductDetail = cart.getIdProductDetail().getId();
        this.idUSer = cart.getIdUser().getId();
        this.status= status;
    }
}
