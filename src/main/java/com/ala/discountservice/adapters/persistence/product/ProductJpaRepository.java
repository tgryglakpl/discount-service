package com.ala.discountservice.adapters.persistence.product;

import com.ala.discountservice.adapters.exception.EntityNotFoundException;
import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.product.ProductFactory;
import com.ala.discountservice.domain.ports.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductJpaRepository implements ProductRepository {

    private final SpringProductRepository springProductRepository;
    private final ProductFactory productFactory;

    @Override
    public Product findProductWithDiscountPolicies(UUID uuid) {
        return productFactory.createProduct(springProductRepository.findProductByUniqueIdWithDiscountPolicies(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product was not found for uuid %s", uuid))));
    }

    @Override
    public Product saveProductWithDiscountPolicies(ProductJpa productJpa) {
        return productFactory.createProduct(springProductRepository.save(productJpa));
    }
}
