package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.dtos.TestDTO;
import com.compass.ecommnerce.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TestDTO> getSaleByDate(@RequestParam(name = "date") String date){
        return ResponseEntity.ok(saleService.findSaleByDate(date));
    }

}
