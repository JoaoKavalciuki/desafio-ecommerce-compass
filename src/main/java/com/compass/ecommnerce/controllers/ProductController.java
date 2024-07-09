package com.compass.ecommnerce.controllers;

import com.compass.ecommnerce.dtos.RequestProductDTO;
import com.compass.ecommnerce.dtos.ResponseProductDTO;
import com.compass.ecommnerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ResponseProductDTO> saveProduct(@RequestBody @Valid RequestProductDTO productDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDTO));
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDTO>> findAllProducts(){
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> findProductById(@PathVariable Long id){
        return  ResponseEntity.ok(productService.findProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }

}
