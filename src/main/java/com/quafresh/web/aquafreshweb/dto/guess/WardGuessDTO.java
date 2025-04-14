package com.quafresh.web.aquafreshweb.dto.guess;

import com.quafresh.web.aquafreshweb.entity.Ward;

public class WardGuessDTO {
    private Integer id;
    private String WardName;
    private String WardIdAPI;
    private Integer districtId;

    public WardGuessDTO(Ward ward) {
        this.id = ward.getId();
        WardName = ward.getWardName();
        WardIdAPI = ward.getWardIdApi();
        this.districtId = ward.getDistrict().getId();
    }
}
