package com.compass.ecommnerce.repositories;

import com.compass.ecommnerce.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(nativeQuery = true,
    value = "SELECT * from tb_sales where date(sale_date) =:requestDate")
    List<Sale> findBySaleDate(String requestDate);
}
