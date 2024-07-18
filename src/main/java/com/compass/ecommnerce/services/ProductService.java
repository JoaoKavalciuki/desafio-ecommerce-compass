package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.RequestProductDTO;
import com.compass.ecommnerce.dtos.ResponseProductDTO;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.repositories.ProductRepository;
import com.compass.ecommnerce.services.exceptions.DuplicatedRecordException;
import com.compass.ecommnerce.services.exceptions.InvalidRequestException;
import com.compass.ecommnerce.services.exceptions.ProductAlredyOnSaleException;
import com.compass.ecommnerce.services.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
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
        try{
            Product product = new Product(productDTO.name(), productDTO.price(), productDTO.quantity());
            productRepository.save(product);

            return new ResponseProductDTO(productDTO.name(), product.getPrice(), productDTO.quantity(), product.getSubTotal());
        } catch (jakarta.validation.ConstraintViolationException exception){
            throw new InvalidRequestException("The field value must be positive and not null or blank");
        } catch (org.springframework.dao.DataIntegrityViolationException exception){
            throw new DuplicatedRecordException("Product already saved");
        }
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
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent() && product.get().getSales().isEmpty()){
            productRepository.deleteById(id);
        } else if(product.isEmpty()){
            throw new EntityNotFoundException("Product of ID " + id + " not found");
        } else {
            throw new  ProductAlredyOnSaleException("The product of id " +
                    product.get().getId() + " is already on sale");
        }
    }

    @Override
    public List<ResponseProductDTO> productsListToDTO(List<Product> products, Integer soldQuantity) {
        List<ResponseProductDTO> productsDTOList = new ArrayList<>();

        products.forEach(product -> {
            productsDTOList.add(new ResponseProductDTO(product.getName(), product.getPrice(), soldQuantity,
                    (product.getPrice() * soldQuantity)));
        });
        return productsDTOList;
    }

    @Override
    public ResponseProductDTO updateProduct(Long id, RequestProductDTO product) {
        try{
            Product target = productRepository.getReferenceById(id);
            updateData(product, target);
            productRepository.save(target);
            return new ResponseProductDTO(target.getName(), target.getPrice(), target.getQuantity(),
                    target.getSubTotal());
        } catch (EntityNotFoundException exception){
            throw new EntityNotFoundException("Product of id: " + id + "not found");
        }
    }

    @Override
    public void updateData(RequestProductDTO source, Product target) {
        target.setName(source.name());
        target.setQuantity(source.quantity());
        target.setPrice(source.price());
    }


}
