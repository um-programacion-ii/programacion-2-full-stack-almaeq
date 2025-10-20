package com.example.hexagonal_basico.product.infrastructure.persistence.mapper;

import com.example.hexagonal_basico.product.domain.model.Product;
import com.example.hexagonal_basico.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        return entity;
    }

    public Product toDomainModel(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(
                entity.getId(),
                entity.getDescription(),
                entity.getPrice()
        );
    }
}
