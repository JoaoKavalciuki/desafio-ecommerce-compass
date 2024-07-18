package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.dtos.RelatoryDTO;
import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.dtos.TestDTO;

import java.time.Instant;
import java.util.List;

public interface ISaleService {

    ResponseSaleDTO saveSale(String[] productsNames, SaleDTO requestSaleDTO);

    RelatoryDTO findSalesBetweenDates(String initDate, String endDate);

    void deleteSaleById(Long id);
}
