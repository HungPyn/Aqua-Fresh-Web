package com.quafresh.web.aquafreshweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "province_name", length = 100)
    private String provinceName;

    @Column(name = "province_id_api",unique = true)
    private Integer provinceIdApi;

}