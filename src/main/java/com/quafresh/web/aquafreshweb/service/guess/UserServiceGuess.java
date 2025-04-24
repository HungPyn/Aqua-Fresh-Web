package com.quafresh.web.aquafreshweb.service.guess;

import com.quafresh.web.aquafreshweb.dto.guess.UserUpdateRequest;
import com.quafresh.web.aquafreshweb.entity.User;
import com.quafresh.web.aquafreshweb.entity.Ward;

public interface UserServiceGuess {
    void editUser(UserUpdateRequest dto);

}
