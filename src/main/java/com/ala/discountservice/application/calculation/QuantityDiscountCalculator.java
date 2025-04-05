package com.ala.discountservice.application.calculation;

import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.ala.discountservice.domain.model.discount.policy.QuantityDiscountPolicy;
import com.ala.discountservice.domain.model.discount.rule.QuantityDiscountRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.function.Predicate;

import static com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType.QUANTITY_DISCOUNT;

@Component
public class QuantityDiscountCalculator implements DiscountCalculator {

    public static final DiscountPolicyType TYPE = QUANTITY_DISCOUNT;

    @Override
    public DiscountPolicyType getType() {
        return TYPE;
    }

    @Override
    public BigDecimal applyDiscount(Product product, Integer quantity) {
        QuantityDiscountPolicy discountPolicy = (QuantityDiscountPolicy) product.getDiscountPolicies().get(TYPE);

        BigDecimal quantityDiscount = discountPolicy.getDiscountRules().stream()
                .filter(findQuantityDiscountThreshold(quantity))
                .max(Comparator.comparingInt(QuantityDiscountRule::getMaxQuantity))
                .map(QuantityDiscountRule::getDiscountPercent)
                .orElse(BigDecimal.ZERO);

        var totalPrice = product.getBasePrice().multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
        var discountFactor = quantityDiscount.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return totalPrice.multiply(BigDecimal.ONE.subtract(discountFactor)).setScale(2, RoundingMode.HALF_UP);
    }

    private static Predicate<QuantityDiscountRule> findQuantityDiscountThreshold(Integer quantity) {
        return (v -> quantity.compareTo(v.getMinQuantity()) >= 0 && quantity.compareTo(v.getMaxQuantity()) < 0
                || quantity.compareTo(v.getMaxQuantity()) > 0);
    }
}
