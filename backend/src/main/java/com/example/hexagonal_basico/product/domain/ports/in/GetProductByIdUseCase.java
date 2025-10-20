package com.example.hexagonal_basico.product.domain.ports.in;

import com.example.hexagonal_basico.product.domain.model.Product;

import java.util.Optional;

public interface GetProductByIdUseCase {
    Optional<Product> getProductById(Long id);
}