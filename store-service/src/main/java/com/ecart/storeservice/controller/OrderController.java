package com.ecart.storeservice.controller;

import com.ecart.storeservice.dto.OrderResponse;
import com.ecart.storeservice.dto.SaveOrderRequest;
import com.ecart.storeservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody SaveOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @DeleteMapping("/cancel/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }
}
