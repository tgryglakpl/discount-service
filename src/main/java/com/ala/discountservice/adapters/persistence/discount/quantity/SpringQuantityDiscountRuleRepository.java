package com.ala.discountservice.adapters.persistence.discount.quantity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringQuantityDiscountRuleRepository extends JpaRepository<QuantityDiscountRuleJpa, Long> {
}
