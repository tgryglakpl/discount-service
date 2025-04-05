package com.ala.discountservice.adapters.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringProductRepository extends JpaRepository<ProductJpa, Long> {
    @Query("SELECT product FROM ProductJpa product " +
            "LEFT JOIN FETCH product.productDiscountPoliciesJpa productDiscountPolicies " +
            "LEFT JOIN FETCH productDiscountPoliciesJpa.discountPolicyJpa discountPolicy " +
            "WHERE product.uniqueId = :uniqueId")
    Optional<ProductJpa> findProductByUniqueIdWithDiscountPolicies(UUID uniqueId);
}

