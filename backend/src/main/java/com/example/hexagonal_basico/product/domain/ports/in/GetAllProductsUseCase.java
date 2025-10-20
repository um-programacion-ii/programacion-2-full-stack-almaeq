package com.example.hexagonal_basico.product.domain.ports.in;

import com.example.hexagonal_basico.product.domain.model.Product;

import java.util.List;

public interface GetAllProductsUseCase {

    List<Product> getAllProducts();
}
