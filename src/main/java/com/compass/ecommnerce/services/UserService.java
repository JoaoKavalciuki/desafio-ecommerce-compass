package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.RegisterUserDTO;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(RegisterUserDTO user){
        userRepository.save(new User(user.email(), user.password()));

        return "Usuário cadastrado com sucesso";
    }
}
