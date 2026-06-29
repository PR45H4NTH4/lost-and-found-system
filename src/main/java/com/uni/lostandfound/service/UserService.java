package com.uni.lostandfound.service;

import com.uni.lostandfound.dto.UserRegistrationDto;
import com.uni.lostandfound.entity.User;

public interface UserService {
    void saveUser(UserRegistrationDto registrationDto);
    User findByEmail(String email);
}
