package com.quafresh.web.aquafreshweb.controller.guess;

import com.quafresh.web.aquafreshweb.entity.User;
import com.quafresh.web.aquafreshweb.service.AuthService;
import com.quafresh.web.aquafreshweb.service.Impl.UserGuessImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserGuessImpl userGuess;

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User user){
        System.out.println("➡️ Dữ liệu nhận được từ frontend:");
        System.out.println(user);
        return ResponseEntity.ok(userGuess.editUser(user));
    }

}
