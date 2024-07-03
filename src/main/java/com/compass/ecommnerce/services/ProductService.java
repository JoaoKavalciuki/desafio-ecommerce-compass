package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.ProductDTO;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.repositories.ProductRepository;
import com.compass.ecommnerce.services.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductDTO saveProduct(ProductDTO productDTO){
        Product product = new Product(productDTO.name(), productDTO.price(), productDTO.quantity());
        productRepository.save(product);

        return productDTO;
    }


    public ProductDTO findProductById(Long id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            return new ProductDTO(product.get().getName(), product.get().getPrice(), product.get().getQuantity());
        }
        return null;
    }

    public List<ProductDTO> findAllProducts() {
        var products = productRepository.findAll();

        List<ProductDTO> productsList = products.stream().
                map(product ->
                        new ProductDTO(product.getName(), product.getPrice(), product.getQuantity()
        )).toList();
        return  productsList;
    }
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
