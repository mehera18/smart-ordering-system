package com.example.project.product_service.service;

import com.example.project.product_service.dto.PoductRequest;
import com.example.project.product_service.dto.ProductResponse;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(PoductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, PoductRequest request);

    void deleteProduct(Long id);
}
