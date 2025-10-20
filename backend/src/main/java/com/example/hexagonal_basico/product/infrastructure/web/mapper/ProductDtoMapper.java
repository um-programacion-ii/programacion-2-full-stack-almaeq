package com.example.hexagonal_basico.product.infrastructure.web.mapper;

import com.example.hexagonal_basico.product.domain.model.Product;
import com.example.hexagonal_basico.product.infrastructure.web.dto.ProductRequest;
import com.example.hexagonal_basico.product.infrastructure.web.dto.ProductResponse;

import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {

    public Product toDomain(ProductRequest request) {
        if (request == null) {
            return null;
        }
        // ID is null because it's a request for a new or updated product
        return new Product(null, request.getDescription(), request.getPrice());
    }

    public ProductResponse toResponse(Product domain) {
        if (domain == null) {
            return null;
        }
        ProductResponse response = new ProductResponse();
        response.setId(domain.getId());
        response.setDescription(domain.getDescription());
        response.setPrice(domain.getPrice());
        return response;
    }
}
