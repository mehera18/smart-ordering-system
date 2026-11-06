package com.example.project.product_service.repository;

import com.example.project.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Modifying
    @Query("""
            UPDATE ProductEntity p
            SET p.quantity = p.quantity - :orderedQuantity
            WHERE p.id = :productId
              AND p.quantity >= :orderedQuantity
            """)
    int decrementStockIfAvailable(@Param("productId") Long productId,
                                  @Param("orderedQuantity") Integer orderedQuantity);
}