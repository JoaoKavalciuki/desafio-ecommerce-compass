package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.RequestLoginDTO;
import com.compass.ecommnerce.dtos.TokenDTO;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody RequestLoginDTO requestLoginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(requestLoginDTO.email(), requestLoginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        TokenDTO token = jwtService.generateToken((User)auth.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
