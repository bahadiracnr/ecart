package com.ecart.storeservice.service.impl;

import com.ecart.exceptioncommon.base.BaseException;
import com.ecart.exceptioncommon.err.ErrorMessage;
import com.ecart.exceptioncommon.model.MessageType;
import com.ecart.storeservice.dto.ProductResponse;
import com.ecart.storeservice.dto.SaveProductRequest;
import com.ecart.storeservice.model.Product;
import com.ecart.storeservice.repository.ProductRepository;
import com.ecart.storeservice.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    @CachePut(value = "products", key = "#request.name")
    public ProductResponse saveOrUpdateProduct(SaveProductRequest request) {
        Product product = (request.getId() != null)
                ? productRepository.findByIdAndIsDeletedFalse(request.getId()).orElse(new Product())
                : new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = productRepository.save(product);

        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    @Override
    @CacheEvict(value = "products", key = "#productId")
    public void deleteProduct(Long productId) {
        productRepository.findByIdAndIsDeletedFalse(productId).ifPresent(product -> {
            product.setIsDeleted(true);
            productRepository.save(product);
        });
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(
                                MessageType.NO_RECORD_EXIST,
                                "Ürün bulunamadı: " + id
                        )
                ));

        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    @Override
    @Cacheable(value = "products", key = "'allProducts'")
    public List<ProductResponse> getAllProducts() {
        System.out.println("🔥 getAllProducts() çalıştı — veritabanından çekiliyor...");

        return productRepository.findAllByIsDeletedFalse().stream()
                .map(product -> {
                    ProductResponse res = new ProductResponse();
                    BeanUtils.copyProperties(product, res);
                    return res;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "products", key = "#name")
    public Product getProductByName(String name) {
        System.out.println("🔥 Veritabanından çekiliyor: " + name);
        try {
            return productRepository.findByName(name)
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(
                                    MessageType.NO_RECORD_EXIST,
                                    "Ürün bulunamadı: " + name
                            )
                    ));
        } catch (ClassCastException e) {

            System.out.println("🔥 Redis'ten gelen veri dönüştürülüyor: " + name);

            return productRepository.findByName(name)
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(
                                    MessageType.NO_RECORD_EXIST,
                                    "Ürün bulunamadı: " + name
                            )
                    ));
        }
    }
}
