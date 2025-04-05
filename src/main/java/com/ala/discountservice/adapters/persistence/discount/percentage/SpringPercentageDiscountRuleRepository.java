package com.ala.discountservice.adapters.persistence.discount.percentage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringPercentageDiscountRuleRepository extends JpaRepository<PercentageDiscountRuleJpa, Long> {
}
