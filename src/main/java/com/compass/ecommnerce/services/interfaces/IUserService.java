package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.dtos.RegisterUserDTO;
import com.compass.ecommnerce.entities.User;

import java.util.Optional;

public interface IUserService {
    String registerUser(RegisterUserDTO user);

    Optional<User> findByEmail(String email);

}
