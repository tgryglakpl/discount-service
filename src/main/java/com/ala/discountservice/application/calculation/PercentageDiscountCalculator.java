package com.ala.discountservice.application.calculation;

import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.ala.discountservice.domain.model.discount.policy.PercentageDiscountPolicy;
import com.ala.discountservice.domain.model.discount.rule.PercentageDiscountRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType.PERCENTAGE_DISCOUNT;

@Component
public class PercentageDiscountCalculator implements DiscountCalculator {

    public static final DiscountPolicyType TYPE = PERCENTAGE_DISCOUNT;

    @Override
    public DiscountPolicyType getType() {
        return TYPE;
    }

    @Override
    public BigDecimal applyDiscount(Product product, Integer quantity) {
        PercentageDiscountPolicy discountPolicy = (PercentageDiscountPolicy) product.getDiscountPolicies().get(TYPE);
        BigDecimal percentageDiscount = discountPolicy.getDiscountRules().stream()
                .findFirst()
                .map(PercentageDiscountRule::getDiscountPercent)
                .orElse(BigDecimal.ZERO);

        var totalPrice = product.getBasePrice().multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
        var discountFactor = percentageDiscount.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return totalPrice.multiply(BigDecimal.ONE.subtract(discountFactor)).setScale(2, RoundingMode.HALF_UP);
    }
}