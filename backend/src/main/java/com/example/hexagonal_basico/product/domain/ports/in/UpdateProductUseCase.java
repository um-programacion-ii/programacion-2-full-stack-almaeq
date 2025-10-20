package com.example.hexagonal_basico.product.domain.ports.in;

import com.example.hexagonal_basico.product.domain.model.Product;

import java.util.Optional;

public interface UpdateProductUseCase {
    Optional<Product> updateProduct(Long id, Product product);
}
