package com.compass.ecommnerce.repositories;

import com.compass.ecommnerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
