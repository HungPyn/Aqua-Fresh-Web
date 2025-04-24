package com.quafresh.web.aquafreshweb.dto.guess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private Integer id;
    private String fullname;
    private String phone;
    private String email;
    private String specificAddress;
    private Integer wardId;
}
