package com.example.project.product_service.service;

import com.example.project.product_service.dao.ProductDao;
import com.example.project.product_service.dto.PoductRequest;
import com.example.project.product_service.dto.ProductResponse;
import com.example.project.product_service.entity.ProductEntity;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductResponse createProduct(PoductRequest request) {
        ProductEntity entity = new ProductEntity();
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuantity());
        return toResponse(productDao.save(entity));
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productDao.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productDao.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public ProductResponse updateProduct(Long id, PoductRequest request) {
        ProductEntity existing = productDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        existing.setName(request.getName());
        existing.setPrice(request.getPrice());
        existing.setQuantity(request.getQuantity());
        return toResponse(productDao.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productDao.deleteById(id);
    }

    private ProductResponse toResponse(ProductEntity entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantity());
    }
}
