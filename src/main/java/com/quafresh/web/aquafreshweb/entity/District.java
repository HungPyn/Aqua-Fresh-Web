package com.quafresh.web.aquafreshweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "district_name", length = 100)
    private String districtName;

    @Column(name = "district_id_api",unique = true)
    private Integer districtIdApi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id_api")
    private Province province;
}