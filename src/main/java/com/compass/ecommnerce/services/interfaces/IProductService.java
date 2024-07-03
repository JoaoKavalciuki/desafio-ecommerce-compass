package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.dtos.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO findProductById(Long id);

    List<ProductDTO> findAllProducts();

    void deleteProductById(Long id);
}
