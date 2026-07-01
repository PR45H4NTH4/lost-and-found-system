package com.uni.lostandfound.service;

import com.uni.lostandfound.dto.UserRegistrationDto;
import com.uni.lostandfound.entity.User;

public interface UserService {
    void saveUser(UserRegistrationDto registrationDto);
    void saveAdmin(UserRegistrationDto registrationDto);
    void softDeleteUser(Long id);
    void restoreUser(Long id);
    void permanentDeleteUser(Long id);
    void resetPassword(Long id, String newPassword);
    User findByEmail(String email);
}
