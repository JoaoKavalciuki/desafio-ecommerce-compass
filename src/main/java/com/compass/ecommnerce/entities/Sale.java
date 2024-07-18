package com.compass.ecommnerce.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sale_date")
    private Instant saleDate;

    private Double saleTotal = 0.00;
    private Integer quantitySold;
    @ManyToMany
    @JoinTable(name = "tb_sales_product",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    public Sale() {
    }

    public Sale(Instant saleDate) {
        this.saleDate = saleDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSaleDate() {
        return saleDate;
    }

    public void setDate(Instant SaleDate) {
        this.saleDate = saleDate;
    }

    public Double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Integer productsQuantity) {
        getProducts().forEach(product -> {this.saleTotal += productsQuantity * product.getPrice();}
        );
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
