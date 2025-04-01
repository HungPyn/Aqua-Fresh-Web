package com.quafresh.web.aquafreshweb.repositories;

import com.quafresh.web.aquafreshweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

}
