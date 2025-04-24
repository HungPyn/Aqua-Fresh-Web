package com.quafresh.web.aquafreshweb.service.Impl;

import com.quafresh.web.aquafreshweb.dto.guess.UserUpdateRequest;
import com.quafresh.web.aquafreshweb.entity.User;
import com.quafresh.web.aquafreshweb.entity.Ward;
import com.quafresh.web.aquafreshweb.repositories.UserRepository;
import com.quafresh.web.aquafreshweb.repositories.WardRepository;
import com.quafresh.web.aquafreshweb.service.guess.UserServiceGuess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGuessImpl implements UserServiceGuess {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WardRepository wardRepository;

    @Override
    public void editUser(UserUpdateRequest dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với ID: " + dto.getId()));

        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setSpecificAddress(dto.getSpecificAddress());

        System.out.println("Ward ID: " + dto.getWardId());

        // Tìm theo ward_id_api (kiểu String), không phải id (Integer)
//        Ward ward = wardRepository.findByWardIdApi(String.valueOf(dto.getWardId()))
//                .orElseThrow(() -> new RuntimeException("Ward không tồn tại"));

//        user.setAddress(ward);
        userRepository.save(user);
    }

}
