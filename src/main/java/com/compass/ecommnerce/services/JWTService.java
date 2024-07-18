package com.compass.ecommnerce.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.compass.ecommnerce.dtos.TokenDTO;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.services.exceptions.JWTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class JWTService {
    @Value("${api.security.token.secret}")
    public String secret;
    public TokenDTO generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("ecommerce-project")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
                return new TokenDTO(Instant.now().atZone(ZoneId.of("+00:00")).toInstant(), token, generateExpirationDate());
        } catch(JWTCreationException exception){
            throw new JWTException("Error while generating jwt token,"+ exception);

        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ecommerce-project")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch(JWTVerificationException exception){
            throw new JWTException("Invalid Token");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+00:00"));
    }
}
