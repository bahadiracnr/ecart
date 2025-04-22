package com.ecart.storeservice.repository;

import com.ecart.storeservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdAndIsDeletedFalse(Long userId);
}
