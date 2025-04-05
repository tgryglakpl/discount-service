package com.ala.discountservice.adapters.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringProductDiscountPolicyRepository extends JpaRepository<ProductDiscountPolicyJpa, Long> {
}
