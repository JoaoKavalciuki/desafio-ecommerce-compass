package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.dtos.TestDTO;
import com.compass.ecommnerce.entities.Sale;
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

    @GetMapping("/all")
    public ResponseEntity<List<ResponseSaleDTO>> findAll(){
        return ResponseEntity.ok(saleService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestParam String[] newProductsNames){
        return ResponseEntity.ok(saleService.updateSale(id, newProductsNames));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleById(@PathVariable Long id){
        saleService.deleteSaleById(id);
        return ResponseEntity.noContent().build();
    }


}
