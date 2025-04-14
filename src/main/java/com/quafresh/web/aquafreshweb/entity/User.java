package com.quafresh.web.aquafreshweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "day_creation")
    private Date dayCreation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address", referencedColumnName = "ward_id_api")
    private Ward address;
    @Column(name = "role")
    private Boolean role;
    @Column(name = "specific_address")
    private String specificAddress;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về quyền dựa trên giá trị của role
        return Collections.singletonList(
                new SimpleGrantedAuthority(role ? "ROLE_ADMIN" : "ROLE_USER")
        );
    }

}
