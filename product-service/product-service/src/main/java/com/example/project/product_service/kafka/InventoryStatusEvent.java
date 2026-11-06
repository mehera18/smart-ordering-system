package com.example.project.product_service.kafka;

public class InventoryStatusEvent {

    private Long orderId;
    private Long productId;
    private String status;
    private String reason;
    private Integer remainingQuantity;

    public InventoryStatusEvent() {
    }

    public InventoryStatusEvent(Long orderId, Long productId, String status, String reason, Integer remainingQuantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.status = status;
        this.reason = reason;
        this.remainingQuantity = remainingQuantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
