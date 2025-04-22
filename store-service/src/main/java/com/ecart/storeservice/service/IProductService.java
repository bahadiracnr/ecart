package com.ecart.storeservice.service;

import com.ecart.storeservice.dto.ProductResponse;
import com.ecart.storeservice.dto.SaveProductRequest;
import com.ecart.storeservice.model.Product;

import java.util.List;

public interface IProductService {
    ProductResponse saveOrUpdateProduct(SaveProductRequest request);
    void deleteProduct(Long productId);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    Product getProductByName(String name);
}
