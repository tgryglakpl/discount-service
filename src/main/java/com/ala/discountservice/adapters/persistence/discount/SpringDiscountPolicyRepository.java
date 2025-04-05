package com.ala.discountservice.adapters.persistence.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDiscountPolicyRepository extends JpaRepository<DiscountPolicyJpa, Long> {
}
