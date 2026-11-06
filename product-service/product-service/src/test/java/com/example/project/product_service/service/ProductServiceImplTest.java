package com.example.project.product_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.project.product_service.dao.ProductDao;
import com.example.project.product_service.dto.PoductRequest;
import com.example.project.product_service.dto.ProductResponse;
import com.example.project.product_service.entity.ProductEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProductReturnsMappedResponse() {
        PoductRequest request = new PoductRequest("Laptop", new BigDecimal("999.99"), 5);
        ProductEntity saved = new ProductEntity(1L, "Laptop", new BigDecimal("999.99"), 5);

        when(productDao.save(org.mockito.ArgumentMatchers.any(ProductEntity.class))).thenReturn(saved);

        ProductResponse response = productService.createProduct(request);

        assertEquals(1L, response.getId());
        assertEquals("Laptop", response.getName());
        assertEquals(new BigDecimal("999.99"), response.getPrice());
        assertEquals(5, response.getQuantity());
    }

    @Test
    void getProductByIdThrowsWhenMissing() {
        when(productDao.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.getProductById(99L));
    }

    @Test
    void deleteProductCallsDaoWhenExists() {
        when(productDao.existsById(7L)).thenReturn(true);

        productService.deleteProduct(7L);

        verify(productDao).deleteById(7L);
    }
}
