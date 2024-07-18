package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.entities.Sale;

import java.util.List;


public interface ISaleService {

    List<ResponseSaleDTO> findAll();

    ResponseSaleDTO saveSale(String[] productsNames, SaleDTO requestSaleDTO);

    Sale updateSale(Long id, String[] newProducts);
    void updateData(Sale sale, List<Product> products);

}
