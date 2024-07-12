package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.ResponseProductDTO;
import com.compass.ecommnerce.dtos.ResponseSaleDTO;
import com.compass.ecommnerce.dtos.SaleDTO;
import com.compass.ecommnerce.dtos.TestDTO;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.entities.Sale;
import com.compass.ecommnerce.projections.SaleProjection;
import com.compass.ecommnerce.repositories.ProductRepository;
import com.compass.ecommnerce.repositories.SaleRepository;
import com.compass.ecommnerce.services.exceptions.EmptySaleException;
import com.compass.ecommnerce.services.exceptions.ProductOutOfStockException;
import com.compass.ecommnerce.services.interfaces.IProductService;
import com.compass.ecommnerce.services.interfaces.ISaleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(productsNames.length == 0){
            throw new EmptySaleException("A sale without products cannot be processed");
        }

        Sale sale = new Sale(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        List<Product> products = productService.findProductsByName(productsNames);

        products.forEach(product -> {
            if(requestSaleDTO.quantity() > product.getQuantity()) {
                throw new ProductOutOfStockException("O produto está fora de estoque");
            }


            sale.getProducts().add(product);
            sale.setQuantitySold(requestSaleDTO.quantity());
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

        ResponseSaleDTO responseSaleDTO = new ResponseSaleDTO(sale.getSaleDate(), saleProducts, sale.getSaleTotal());
        return  responseSaleDTO;
    }


    public List<ResponseSaleDTO> findSaleByDate(String formattedDate) {
        List<Sale> sales = saleRepository.findBySaleDate(formattedDate);

        List<ResponseSaleDTO> responseSalesDTO = new ArrayList<>();
        if(!sales.isEmpty()){
            sales.forEach(sale ->{
                //Retornar a quantidade vendida, o subtotal tbm ta errado
                List<ResponseProductDTO> salesProducts = productService.
                        productsListToDTO(sale.getProducts().stream().toList(), sale.getQuantitySold());

                ResponseSaleDTO responseSaleDTO = new ResponseSaleDTO(sale.getSaleDate(), salesProducts,
                        sale.getSaleTotal());

                responseSalesDTO.add(responseSaleDTO);
            });

            return responseSalesDTO;
        }
        throw new EntityNotFoundException("Sale of date " + formattedDate + " not found");

    }
}
