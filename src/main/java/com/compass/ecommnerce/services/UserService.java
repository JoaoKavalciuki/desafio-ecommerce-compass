package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.RegisterUserDTO;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.entities.enums.Role;
import com.compass.ecommnerce.repositories.UserRepository;
import com.compass.ecommnerce.services.exceptions.JWTException;
import com.compass.ecommnerce.services.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(RegisterUserDTO user){
        userRepository.save(new User(user.email(), encoder.encode(user.password()), Role.USER));

        return "Usuário cadastrado com sucesso";
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));
    }

    @Override
    public User getAuthenticatedUserInfo(Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String getEmail = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<User> userOptional = userRepository.findByEmail(getEmail);
            if (userOptional.isPresent()) {
                return userOptional.get();
            }
        }
        throw new JWTException("Token JWT expirado ou não informado, por favor tente novamente");
    }
}
