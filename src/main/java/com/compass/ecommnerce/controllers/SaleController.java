package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.RelatoryDTO;
import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.dtos.TestDTO;
import com.compass.ecommnerce.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<ResponseSaleDTO> saveSale(@RequestParam String[] productsNames, @RequestBody SaleDTO saleDTO){
        return ResponseEntity.status(202).body(saleService.saveSale(productsNames, saleDTO));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseSaleDTO>> getSaleByDate(@RequestParam(name = "date") String date){
        return ResponseEntity.ok(saleService.findSaleByDate(date));
    }

    @GetMapping("/relatory")
    public ResponseEntity<RelatoryDTO> getSalesBetweenDates(@RequestParam(name = "initDate") String date,
                                                                  @RequestParam(name = "endDate") String endDate)
    {
        return ResponseEntity.ok(saleService.findSalesBetweenDates(date, endDate));
    }

}
