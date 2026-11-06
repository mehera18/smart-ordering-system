package com.example.order_service.service;

import com.example.order_service.bean.OrderRequest;
import com.example.order_service.bean.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrder(Long id, OrderRequest orderRequest);

    void deleteOrder(Long id);
}
