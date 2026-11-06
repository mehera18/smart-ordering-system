package com.example.order_service.bean;

import com.example.order_service.entity.OrderStatus;

public class OrderResponse {

    private Long id;
    private Long productId;
    private Integer quantity;
    private OrderStatus status;

    public OrderResponse() {}

    public OrderResponse(Long id, Long productId, Integer quantity, OrderStatus status) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}