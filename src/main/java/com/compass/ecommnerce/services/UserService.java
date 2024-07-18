package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.RegisterUserDTO;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.repositories.UserRepository;
import com.compass.ecommnerce.services.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(RegisterUserDTO user){
        userRepository.save(new User(user.email(), user.password()));

        return "Usuário cadastrado com sucesso";
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));
    }
}
