package com.ala.discountservice.domain.model;

import com.ala.discountservice.domain.model.discount.policy.DiscountPolicy;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Value
@Builder
@EqualsAndHashCode
public class Product {
    @JsonIgnore
    private Long id;
    private UUID uniqueId;
    private String name;
    private BigDecimal basePrice;
    private Map<DiscountPolicyType, DiscountPolicy> discountPolicies;
}
