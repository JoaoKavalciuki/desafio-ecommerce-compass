package com.compass.ecommnerce.repositories;

import com.compass.ecommnerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);
}
