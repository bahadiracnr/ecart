package com.ecart.storeservice.controller;

import com.ecart.storeservice.dto.ProductResponse;
import com.ecart.storeservice.dto.SaveProductRequest;
import com.ecart.storeservice.model.Product;
import com.ecart.storeservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class RestProductController {

    private final IProductService productService;

    @PostMapping("/save")
    public ProductResponse saveOrUpdate(@RequestBody SaveProductRequest request) {
        return productService.saveOrUpdateProduct(request);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/id/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/all")
    public List<ProductResponse> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/name/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }
}
