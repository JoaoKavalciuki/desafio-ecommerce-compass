package com.compass.ecommnerce.services.interfaces;

import com.compass.ecommnerce.dtos.RequestProductDTO;
import com.compass.ecommnerce.dtos.ResponseProductDTO;
import com.compass.ecommnerce.entities.Product;

import java.util.List;

public interface IProductService {

    ResponseProductDTO saveProduct(RequestProductDTO productDTO);

    ResponseProductDTO findProductById(Long id);

    List<ResponseProductDTO> findAllProducts();

    List<Product> findProductsByName(String[] name);

    List<Product> saveAll(List<Product> products);
    void deleteProductById(Long id);

    List<ResponseProductDTO> productsListToDTO(List<Product> products);
}
