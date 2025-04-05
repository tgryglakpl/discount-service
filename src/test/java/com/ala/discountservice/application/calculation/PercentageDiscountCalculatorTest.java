package com.ala.discountservice.application.calculation;

import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.ala.discountservice.domain.model.discount.policy.PercentageDiscountPolicy;
import com.ala.discountservice.domain.model.discount.rule.PercentageDiscountRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PercentageDiscountCalculatorTest {

    private PercentageDiscountCalculator discountCalculator;
    private Product product;
    private PercentageDiscountPolicy discountPolicy;
    private PercentageDiscountRule discountRule;

    @BeforeEach
    void setUp() {
        discountCalculator = new PercentageDiscountCalculator();
        product = mock(Product.class);
        discountPolicy = mock(PercentageDiscountPolicy.class);
        discountRule = mock(PercentageDiscountRule.class);
    }

    @Test
    void testApplyDiscount_withDiscount() {
        //given
        int quantity = 2;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("180.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.PERCENTAGE_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(List.of(discountRule));
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("10.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testApplyDiscount_withNoDiscount() {
        // given
        int quantity = 2;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("200.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.PERCENTAGE_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(Collections.emptyList());

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testApplyDiscount_withZeroBasePrice() {
        //given
        int quantity = 5;
        BigDecimal basePrice = BigDecimal.ZERO;
        BigDecimal expectedPrice = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.PERCENTAGE_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(List.of(discountRule));
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("20.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }
}