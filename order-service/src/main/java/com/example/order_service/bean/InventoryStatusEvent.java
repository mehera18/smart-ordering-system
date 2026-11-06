package com.example.order_service.bean;

public class InventoryStatusEvent {

    private Long orderId;
    private Long productId;
    private String status;       // "CONFIRMED" or "REJECTED"
    private String reason;       // nullable
    private Integer remainingQuantity; // nullable

    public InventoryStatusEvent() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Integer getRemainingQuantity() { return remainingQuantity; }
    public void setRemainingQuantity(Integer remainingQuantity) { this.remainingQuantity = remainingQuantity; }

    @Override
    public String toString() {
        return "InventoryStatusEvent{orderId=" + orderId + ", productId=" + productId +
               ", status='" + status + "', reason='" + reason +
               "', remainingQuantity=" + remainingQuantity + "}";
    }
}
