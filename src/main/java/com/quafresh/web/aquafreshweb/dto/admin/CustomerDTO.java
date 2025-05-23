package com.quafresh.web.aquafreshweb.dto.admin;

import com.quafresh.web.aquafreshweb.entity.Ward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class CustomerDTO {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String phone;
    private String email;
    private Date dayCreation;
    private Ward address;
}
