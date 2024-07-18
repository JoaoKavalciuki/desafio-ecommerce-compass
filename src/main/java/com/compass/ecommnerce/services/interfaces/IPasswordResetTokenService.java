package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.entities.User;

import java.util.Optional;

public interface IPasswordResetTokenService {
    void saveResetPasswordToken(User user, String token);
    String validateToken(String token);
    Optional<User> findUserByToken(String token);
    void resetPassword(User user, String newPassword);
}
