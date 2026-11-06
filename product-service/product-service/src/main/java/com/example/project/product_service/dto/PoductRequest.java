package com.example.project.product_service.dto;

import java.math.BigDecimal;

public class PoductRequest {

    private String name;
    private BigDecimal price;
    private Integer quantity;

    public PoductRequest() {
    }

    public PoductRequest(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
