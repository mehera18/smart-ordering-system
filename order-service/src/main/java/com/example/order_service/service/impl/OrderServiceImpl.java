package com.example.order_service.service.impl;

import com.example.order_service.bean.OrderEvent;
import com.example.order_service.bean.OrderRequest;
import com.example.order_service.bean.OrderResponse;
import com.example.order_service.dao.OrderDao;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderStatus;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderProducer orderProducer;

    public OrderServiceImpl(OrderDao orderDao, OrderProducer orderProducer) {
        this.orderDao = orderDao;
        this.orderProducer = orderProducer;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        Order saved = orderDao.save(order);
        orderProducer.sendOrderEvent(new OrderEvent(
                saved.getId(), saved.getProductId(), saved.getQuantity(),
                saved.getStatus().name(), "CREATED"));
        return mapToResponse(saved);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderDao.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        Order updated = orderDao.save(order);
        orderProducer.sendOrderEvent(new OrderEvent(
                updated.getId(), updated.getProductId(), updated.getQuantity(),
                updated.getStatus().name(), "UPDATED"));
        return mapToResponse(updated);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDao.deleteById(id);
        orderProducer.sendOrderEvent(new OrderEvent(id, null, null, "DELETED", "DELETED"));
    }

    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(order.getId(), order.getProductId(), order.getQuantity(), order.getStatus());
    }
}