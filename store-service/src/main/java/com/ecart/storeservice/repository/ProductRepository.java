package com.ecart.storeservice.repository;

import com.ecart.storeservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIsDeletedFalse();
    Optional<Product> findByIdAndIsDeletedFalse(Long id);
    Optional<Product> findByName(String name);
}
