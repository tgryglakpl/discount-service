package com.ala.discountservice.application;

import com.ala.discountservice.application.calculation.DiscountCalculator;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CalculationServiceFactory {

    private final Map<DiscountPolicyType, DiscountCalculator> calculators = new HashMap<>();

    @Autowired
    public CalculationServiceFactory(List<DiscountCalculator> discountCalculators) {
        discountCalculators.forEach(calculator -> calculators.put(calculator.getType(), calculator));
    }

    public DiscountCalculator getCalculator(DiscountPolicyType discountPolicyType) {
        return this.calculators.get(discountPolicyType);
    }
}

