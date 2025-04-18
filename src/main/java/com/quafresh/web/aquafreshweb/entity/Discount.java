package com.quafresh.web.aquafreshweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "discount_name")
    private String discountName;
    @Column(name = "discount_value")
    private BigDecimal discountValue;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "idDiscount",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    List<ProductDetail> productDetailList;

    public void add(ProductDetail productDetail) {
        productDetailList.add(productDetail);
    }
    public void remove(ProductDetail productDetail) {
        productDetailList.remove(productDetail);
    }
}
