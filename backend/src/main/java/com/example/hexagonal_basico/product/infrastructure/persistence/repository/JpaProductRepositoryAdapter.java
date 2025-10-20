package com.example.hexagonal_basico.product.infrastructure.persistence.repository;

import com.example.hexagonal_basico.product.domain.model.Product;
import com.example.hexagonal_basico.product.domain.ports.out.ProductRepository;
import com.example.hexagonal_basico.product.infrastructure.persistence.entity.ProductEntity;
import com.example.hexagonal_basico.product.infrastructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;
    private final ProductMapper productMapper;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity savedEntity = jpaProductRepository.save(productEntity);
        return productMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(productMapper::toDomainModel);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll().stream()
                .map(productMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> update(Long id, Product product) {
        if (jpaProductRepository.existsById(id)) {
            ProductEntity productEntity = productMapper.toEntity(product);
            productEntity.setId(id); // Ensure the ID is set for update
            ProductEntity updatedEntity = jpaProductRepository.save(productEntity);
            return Optional.of(productMapper.toDomainModel(updatedEntity));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpaProductRepository.existsById(id)) {
            jpaProductRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

