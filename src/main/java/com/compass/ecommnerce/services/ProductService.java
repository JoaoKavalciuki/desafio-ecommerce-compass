package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.RequestProductDTO;
import com.compass.ecommnerce.dtos.ResponseProductDTO;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.repositories.ProductRepository;
import com.compass.ecommnerce.services.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ResponseProductDTO saveProduct(RequestProductDTO productDTO){
        Product product = new Product(productDTO.name(), productDTO.price(), productDTO.quantity());
        productRepository.save(product);

        return new ResponseProductDTO(productDTO.name(), product.getPrice(), productDTO.quantity(), product.getSubTotal());
    }


    public ResponseProductDTO findProductById(Long id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            return new ResponseProductDTO(product.get().getName(), product.get().getPrice(), product.get().getQuantity(), product.get().getSubTotal());
        } else {
            throw new EntityNotFoundException("Product of ID " + id + " not found");
        }

    }

    public List<ResponseProductDTO> findAllProducts() {
        var products = productRepository.findAll();

        List<ResponseProductDTO> productsList = products.stream().
                map(product ->
                        new ResponseProductDTO(product.getName(), product.getPrice(), product.getQuantity(), product.getSubTotal()
        )).toList();
        return  productsList;
    }

    @Override
    public List<Product> findProductsByName(String[] names) {
        List<Product> products = new ArrayList<>();

        for(int i=0; i<names.length; i++){
            Product product = productRepository.findProductByName(names[i]);
            products.add(product);
        }
        return  products;
    }


    @Override
    public List<Product> saveAll(List<Product> products) {
        productRepository.saveAll(products);
        return products;
    }


    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
