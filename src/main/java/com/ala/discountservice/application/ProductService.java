package com.ala.discountservice.application;

import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.ports.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findProductDetails(UUID uuid) {
        return productRepository.findProductWithDiscountPolicies(uuid);
    }
}
