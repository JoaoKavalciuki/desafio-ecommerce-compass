package com.compass.ecommnerce.services;

import com.compass.ecommnerce.entities.PasswordResetToken;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.repositories.PasswordTokenRepository;
import com.compass.ecommnerce.repositories.UserRepository;
import com.compass.ecommnerce.services.interfaces.IPasswordResetTokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class PasswordResetTokenService implements IPasswordResetTokenService {
    private PasswordTokenRepository tokenRepository;
    private UserRepository userRepository;

    public PasswordResetTokenService(PasswordTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public void saveResetPasswordToken(User user, String token){

        PasswordResetToken newToken = new PasswordResetToken(token, user);
        tokenRepository.save(newToken);

    }

    public String validateToken(String token){
        Optional<PasswordResetToken> passwordResetToken = tokenRepository.findByToken(token);

        Instant presentInstant = Instant.now();

        if(passwordResetToken.isEmpty()){
            return "INVALID";
        } else if(passwordResetToken.get().getExpiresAt().until(presentInstant, ChronoUnit.SECONDS) <= 0){
            tokenRepository.delete(passwordResetToken.get());
            return "EXPIRED";
        }

        return "VALID";
    }

    public Optional<User> findUserByToken(String token) {
        return Optional.ofNullable(tokenRepository.findByToken(token).get().getUser());
    }


    public void resetPassword(User user, String newPassword) {
        Optional<User> userToBeFound = userRepository.findByEmail(user.getEmail());

        if(userToBeFound.isPresent()){
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}
