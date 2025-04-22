package com.ecart.storeservice.service;

import com.ecart.storeservice.dto.OrderResponse;
import com.ecart.storeservice.dto.SaveOrderRequest;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(SaveOrderRequest request);
    List<OrderResponse> getUserOrders(Long userId);
    void cancelOrder(Long orderId);
}
