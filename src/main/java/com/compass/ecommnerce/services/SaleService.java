package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.ResponseProductDTO;
import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.entities.Sale;
import com.compass.ecommnerce.repositories.ProductRepository;
import com.compass.ecommnerce.repositories.SaleRepository;
import com.compass.ecommnerce.services.exceptions.ProductOutOfStockException;
import com.compass.ecommnerce.services.interfaces.IProductService;
import com.compass.ecommnerce.services.interfaces.ISaleService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SaleService implements ISaleService {
    private SaleRepository saleRepository;
    private IProductService productService;
    ProductRepository productRepository;


    public SaleService(SaleRepository saleRepository, IProductService productService, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public ResponseSaleDTO saveSale(String[] productsNames, SaleDTO requestSaleDTO) {
        Sale sale = new Sale(Instant.now());
        List<Product> products = productService.findProductsByName(productsNames);

        products.forEach(product -> {
            if(requestSaleDTO.quantity() > product.getQuantity()) {
                throw new ProductOutOfStockException("O produto está fora de estoque");
            }

            sale.getProducts().add(product);
            product.getSales().add(sale);

            if (product.getQuantity() - requestSaleDTO.quantity() == 0) {
                product.setActive(false);
            }

            product.setQuantity(product.getQuantity() - requestSaleDTO.quantity());
            productRepository.save(product);
        });
        sale.setSaleTotal(requestSaleDTO.quantity());
        saleRepository.save(sale);

        List<ResponseProductDTO> saleProducts = products.stream().map(product ->
                new ResponseProductDTO(product.getName(), product.getPrice(), requestSaleDTO.quantity(),
                        (product.getPrice() * requestSaleDTO.quantity()))).toList();

        ResponseSaleDTO responseSaleDTO = new ResponseSaleDTO(sale.getDate(), saleProducts, sale.getSaleTotal());
        return  responseSaleDTO;
    }
}
