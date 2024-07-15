package com.compass.ecommnerce.repositories;

import com.compass.ecommnerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}
