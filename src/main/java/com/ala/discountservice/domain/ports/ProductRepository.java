package com.ala.discountservice.domain.ports;

import com.ala.discountservice.adapters.persistence.product.ProductJpa;
import com.ala.discountservice.domain.model.Product;

import java.util.UUID;

public interface ProductRepository {

    Product findProductWithDiscountPolicies(UUID uuid);

    Product saveProductWithDiscountPolicies(ProductJpa productJpa);
}
