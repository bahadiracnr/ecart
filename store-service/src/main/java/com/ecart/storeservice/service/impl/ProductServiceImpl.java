package com.ecart.storeservice.service.impl;

import com.ecart.exceptioncommon.base.BaseException;
import com.ecart.exceptioncommon.err.ErrorMessage;
import com.ecart.exceptioncommon.model.MessageType;
import com.ecart.storeservice.dto.ProductResponse;
import com.ecart.storeservice.dto.SaveProductRequest;
import com.ecart.storeservice.model.Product;
import com.ecart.storeservice.repository.ProductRepository;
import com.ecart.storeservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
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
    public void deleteProduct(Long productId) {
        productRepository.findByIdAndIsDeletedFalse(productId).ifPresent(product -> {
            product.setIsDeleted(true);
            productRepository.save(product);
        });
    }

    @Override
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
    public List<ProductResponse> getAllProducts() {
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
        return productRepository.findByName(name)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(
                                MessageType.NO_RECORD_EXIST,
                                "Ürün bulunamadı: " + name
                        )
                ));
    }
}
