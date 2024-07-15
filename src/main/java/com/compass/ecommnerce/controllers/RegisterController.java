package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.RegisterUserDTO;
import com.compass.ecommnerce.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO);
        return ResponseEntity.ok("User successfully registered");
    }
}
