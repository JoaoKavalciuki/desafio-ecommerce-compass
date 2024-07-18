package com.compass.ecommnerce.services;

import com.compass.ecommnerce.dtos.*;
import com.compass.ecommnerce.entities.Product;
import com.compass.ecommnerce.entities.Sale;
import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.projections.SaleProjection;
import com.compass.ecommnerce.repositories.ProductRepository;
import com.compass.ecommnerce.repositories.SaleRepository;
import com.compass.ecommnerce.services.exceptions.EmptySaleException;
import com.compass.ecommnerce.services.exceptions.ProductOutOfStockException;
import com.compass.ecommnerce.services.interfaces.IProductService;
import com.compass.ecommnerce.services.interfaces.ISaleService;
import com.compass.ecommnerce.services.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private IUserService userService;
    private Authentication authentication;
    ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, IProductService productService, ProductRepository productRepository,
                       IUserService userService)
    {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public ResponseSaleDTO saveSale(String[] productsNames, SaleDTO requestSaleDTO) {
        if(productsNames.length == 0){
            throw new EmptySaleException("A sale without products cannot be processed");
        }

        Sale sale = new Sale(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        List<Product> products = productService.findProductsByName(productsNames);

        products.forEach(product -> {
            if(requestSaleDTO.quantity() > product.getQuantity()) {
                throw new ProductOutOfStockException("The product is out of stock or the quantity on stock isn't enough ");
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

        User buyer = userService.getAuthenticatedUserInfo(authentication);
        buyer.getSales().add(sale);
        sale.setBuyer(buyer);
        saleRepository.save(sale);

        List<ResponseProductDTO> saleProducts = products.stream().map(product ->
                new ResponseProductDTO(product.getName(), product.getPrice(), requestSaleDTO.quantity(),
                        (product.getPrice() * requestSaleDTO.quantity()))).toList();

        ResponseSaleDTO responseSaleDTO = new ResponseSaleDTO(sale.getSaleDate(), saleProducts, sale.getSaleTotal());
        return  responseSaleDTO;
    }

    @Override
    public RelatoryDTO findSalesBetweenDates(String initDate, String endDate) {
        List<Sale> sales = saleRepository.findBetweenDates(initDate, endDate);

        List<ResponseSaleDTO> responseSalesDTO = new ArrayList<>();
        if(!sales.isEmpty()){
            sales.forEach(sale ->{
                List<ResponseProductDTO> salesProducts = productService.
                        productsListToDTO(sale.getProducts().stream().toList(), sale.getQuantitySold());


                ResponseSaleDTO responseSaleDTO = new ResponseSaleDTO(sale.getSaleDate(), salesProducts,
                        sale.getSaleTotal());

                responseSalesDTO.add(responseSaleDTO);
            });

            Integer relatoryQuantitySold = 0;
            for(Sale sale: sales){
                relatoryQuantitySold += sale.getQuantitySold();
            }

            Double relatoryTotalSold = 0.00;
            for(ResponseSaleDTO sale: responseSalesDTO){
                relatoryTotalSold += sale.saleTotal();

            }

            RelatoryDTO relatoryDTO = new RelatoryDTO(Instant.now(),  relatoryTotalSold, relatoryQuantitySold, responseSalesDTO);
            return relatoryDTO;
        }
        throw new EntityNotFoundException("Sales between dates " + initDate + " and " + endDate +" not found");

    }

    @Override
    public void deleteSaleById(Long id) {
        saleRepository.deleteById(id);
    }


    public List<ResponseSaleDTO> findSaleByDate(String formattedDate) {
        List<Sale> sales = saleRepository.findBySaleDate(formattedDate);

        List<ResponseSaleDTO> responseSalesDTO = new ArrayList<>();
        if(!sales.isEmpty()){
            sales.forEach(sale ->{
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
