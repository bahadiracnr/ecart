package com.ecart.storeservice.service.impl;

import com.ecart.exceptioncommon.base.BaseException;
import com.ecart.exceptioncommon.err.ErrorMessage;
import com.ecart.exceptioncommon.model.MessageType;
import com.ecart.storeservice.dto.OrderResponse;
import com.ecart.storeservice.dto.SaveOrderRequest;
import com.ecart.storeservice.model.Order;
import com.ecart.storeservice.model.Product;
import com.ecart.storeservice.repository.OrderRepository;
import com.ecart.storeservice.repository.ProductRepository;
import com.ecart.storeservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(SaveOrderRequest request) {
        Product product = productRepository.findByIdAndIsDeletedFalse(request.getProductId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(
                                MessageType.NO_RECORD_EXIST,
                                "Ürün bulunamadı: " + request.getProductId()
                        )
                ));

        Double totalPrice = product.getPrice() * request.getQuantity();

        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .status("CREATED")
                .build();

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserIdAndIsDeletedFalse(userId);

        if (orders.isEmpty()) {
            throw new BaseException(
                    new ErrorMessage(
                            MessageType.NO_RECORD_EXIST,
                            "Kullanıcıya ait sipariş bulunamadı: " + userId
                    )
            );
        }

        return orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(
                                MessageType.NO_RECORD_EXIST,
                                "Sipariş bulunamadı: " + orderId
                        )
                ));

        order.setStatus("CANCELLED");
        order.setIsDeleted(true);
        orderRepository.save(order);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
