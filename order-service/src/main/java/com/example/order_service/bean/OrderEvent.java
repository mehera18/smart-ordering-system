package com.example.order_service.bean;

public class OrderEvent {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String status;
    private String eventType; // CREATED, UPDATED, DELETED

    public OrderEvent() {}

    public OrderEvent(Long orderId, Long productId, Integer quantity, String status, String eventType) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.eventType = eventType;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    @Override
    public String toString() {
        return "OrderEvent{orderId=" + orderId + ", productId=" + productId +
               ", quantity=" + quantity + ", status='" + status +
               "', eventType='" + eventType + "'}";
    }
}
