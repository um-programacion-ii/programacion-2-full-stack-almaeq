package com.example.hexagonal_basico.product.domain.ports.in;

import com.example.hexagonal_basico.product.domain.model.Product;

public interface CreateProductUseCase {
    Product createProduct(Product product);
}
