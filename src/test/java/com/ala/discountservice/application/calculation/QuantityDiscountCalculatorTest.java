package com.ala.discountservice.application.calculation;

import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.ala.discountservice.domain.model.discount.policy.QuantityDiscountPolicy;
import com.ala.discountservice.domain.model.discount.rule.QuantityDiscountRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuantityDiscountCalculatorTest {

    private QuantityDiscountCalculator discountCalculator;
    private Product product;
    private QuantityDiscountPolicy discountPolicy;
    private QuantityDiscountRule discountRule;

    @BeforeEach
    void setUp() {
        discountCalculator = new QuantityDiscountCalculator();
        product = mock(Product.class);
        discountPolicy = mock(QuantityDiscountPolicy.class);
        discountRule = mock(QuantityDiscountRule.class);
    }

    @Test
    void testApplyDiscount_withDiscount() {
        //given
        int quantity = 5;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("475.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.QUANTITY_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(Collections.singletonList(discountRule));
        when(discountRule.getMinQuantity()).thenReturn(3);
        when(discountRule.getMaxQuantity()).thenReturn(10);
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("5.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testApplyDiscount_withNoDiscount() {
        //given
        int quantity = 2;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("200.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.QUANTITY_DISCOUNT, discountPolicy));
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
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.QUANTITY_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(Collections.singletonList(discountRule));
        when(discountRule.getMinQuantity()).thenReturn(1);
        when(discountRule.getMaxQuantity()).thenReturn(10);
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("10.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testApplyDiscount_withQuantityBelowMin() {
        //given
        int quantity = 2;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("200.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.QUANTITY_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(Collections.singletonList(discountRule));
        when(discountRule.getMinQuantity()).thenReturn(5);
        when(discountRule.getMaxQuantity()).thenReturn(10);
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("10.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testApplyDiscount_withQuantityExactlyAtMin() {
        //given
        int quantity = 5;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("475.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.QUANTITY_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(Collections.singletonList(discountRule));
        when(discountRule.getMinQuantity()).thenReturn(5);
        when(discountRule.getMaxQuantity()).thenReturn(10);
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("5.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testApplyDiscount_withQuantityAboveMax() {
        //given
        int quantity = 15;
        BigDecimal basePrice = new BigDecimal("100.00");
        BigDecimal expectedPrice = new BigDecimal("1350.00");

        when(product.getBasePrice()).thenReturn(basePrice);
        when(product.getDiscountPolicies()).thenReturn(Map.of(DiscountPolicyType.QUANTITY_DISCOUNT, discountPolicy));
        when(discountPolicy.getDiscountRules()).thenReturn(Collections.singletonList(discountRule));
        when(discountRule.getMinQuantity()).thenReturn(5);
        when(discountRule.getMaxQuantity()).thenReturn(10);
        when(discountRule.getDiscountPercent()).thenReturn(new BigDecimal("10.00"));

        //when
        BigDecimal actualPrice = discountCalculator.applyDiscount(product, quantity);

        //then
        assertEquals(expectedPrice, actualPrice);
    }
}
