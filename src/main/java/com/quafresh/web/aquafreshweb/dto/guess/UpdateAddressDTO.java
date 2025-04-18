package com.quafresh.web.aquafreshweb.dto.guess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressDTO {
    private String phone;
    private String specificAddress;
    private WardGuessDTO ward;
}
