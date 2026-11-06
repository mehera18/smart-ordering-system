package com.example.project.product_service.dao;

import com.example.project.product_service.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Long id);

    ProductEntity save(ProductEntity productEntity);

    boolean existsById(Long id);

    void deleteById(Long id);
}
