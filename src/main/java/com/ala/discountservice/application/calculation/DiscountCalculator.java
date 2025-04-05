package com.ala.discountservice.application.calculation;

import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;

import java.math.BigDecimal;

public interface DiscountCalculator {
    BigDecimal applyDiscount(Product product, Integer quantity);

    DiscountPolicyType getType();
}
