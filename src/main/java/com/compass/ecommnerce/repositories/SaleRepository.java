package com.compass.ecommnerce.repositories;

import com.compass.ecommnerce.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
