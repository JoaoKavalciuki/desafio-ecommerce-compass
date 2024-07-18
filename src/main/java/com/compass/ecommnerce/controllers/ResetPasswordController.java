package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.PasswordResetDTO;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.events.PasswordRecoveryEvent;
import com.compass.ecommnerce.services.interfaces.IPasswordResetTokenService;
import com.compass.ecommnerce.services.interfaces.IUserService;
import com.compass.ecommnerce.utils.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/forgot-password")
public class ResetPasswordController {
    private IUserService userService;
    private ApplicationEventPublisher publisher;
    private IPasswordResetTokenService tokenService;

    public ResetPasswordController(IUserService userService, ApplicationEventPublisher publisher, IPasswordResetTokenService tokenService) {
        this.userService = userService;
        this.publisher = publisher;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<String> sendPasswordRequest(@RequestBody PasswordResetDTO passwordResetDTO,
                                                      HttpServletRequest request)
    {
        Optional<User> user = userService.findByEmail(passwordResetDTO.email());
        publisher.publishEvent(new PasswordRecoveryEvent(UrlUtil.getApplicationUrl(request), user.get()));

        return ResponseEntity.ok("Email for password recovery sent successfully");
    }
}
