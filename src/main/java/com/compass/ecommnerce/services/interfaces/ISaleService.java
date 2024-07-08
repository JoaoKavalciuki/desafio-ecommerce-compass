package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;

public interface ISaleService {

    ResponseSaleDTO saveSale(String[] productsNames, SaleDTO requestSaleDTO);
}
