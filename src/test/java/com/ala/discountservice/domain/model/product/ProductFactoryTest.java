package com.ala.discountservice.domain.model.product;

import com.ala.discountservice.adapters.persistence.discount.DiscountPolicyJpa;
import com.ala.discountservice.adapters.persistence.discount.percentage.PercentageDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.discount.quantity.QuantityDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.product.ProductDiscountPolicyJpa;
import com.ala.discountservice.adapters.persistence.product.ProductJpa;
import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.ala.discountservice.domain.model.discount.policy.PercentageDiscountPolicy;
import com.ala.discountservice.domain.model.discount.policy.QuantityDiscountPolicy;
import liquibase.exception.MissingRequiredArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductFactoryTest {

    private ProductFactory productFactory;
    private ProductJpa productJpa;
    private ProductDiscountPolicyJpa quantityProductDiscountPolicyJpa;
    private ProductDiscountPolicyJpa percentageProductDiscountPolicyJpa;
    private List<QuantityDiscountRuleJpa> quantityDiscountRulesJpa;
    private List<PercentageDiscountRuleJpa> percentageDiscountRulesJpa;

    @BeforeEach
    void setUp() {
        productFactory = new ProductFactory();

        productJpa = ProductJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .name("Product 1")
                .basePrice(new BigDecimal("100.00"))
                .build();

        quantityDiscountRulesJpa = List.of(
                QuantityDiscountRuleJpa.builder()
                        .id(1L)
                        .uniqueId(UUID.randomUUID())
                        .minQuantity(1)
                        .maxQuantity(10)
                        .discountPercent(new BigDecimal("5.00"))
                        .build(),
                QuantityDiscountRuleJpa.builder()
                        .id(2L)
                        .uniqueId(UUID.randomUUID())
                        .minQuantity(11)
                        .maxQuantity(50)
                        .discountPercent(new BigDecimal("10.00"))
                        .build()
        );

        percentageDiscountRulesJpa = List.of(PercentageDiscountRuleJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .discountPercent(new BigDecimal("10.00"))
                .build());

        DiscountPolicyJpa quantityDiscountPolicyJpa = DiscountPolicyJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .type(DiscountPolicyType.QUANTITY_DISCOUNT)
                .priority(1)
                .quantityDiscountRuleJpas(quantityDiscountRulesJpa)
                .build();

        DiscountPolicyJpa percentageDiscountPolicyJpa = DiscountPolicyJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .type(DiscountPolicyType.PERCENTAGE_DISCOUNT)
                .priority(2)
                .percentageDiscountRuleJpas(percentageDiscountRulesJpa)
                .build();

        quantityProductDiscountPolicyJpa = ProductDiscountPolicyJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .productJpa(productJpa)
                .discountPolicyJpa(quantityDiscountPolicyJpa)
                .build();

        percentageProductDiscountPolicyJpa = ProductDiscountPolicyJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .productJpa(productJpa)
                .discountPolicyJpa(percentageDiscountPolicyJpa)
                .build();
    }

    @Test
    void createProduct_shouldReturnProductWithMappedDiscountPolicies() {
        productJpa = ProductJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .name("Product 1")
                .basePrice(new BigDecimal("100.00"))
                .productDiscountPoliciesJpa(List.of(quantityProductDiscountPolicyJpa, percentageProductDiscountPolicyJpa))
                .build();

        Product result = productFactory.createProduct(productJpa);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product 1", result.getName());
        assertEquals(new BigDecimal("100.00"), result.getBasePrice());
        assertTrue(result.getDiscountPolicies().containsKey(DiscountPolicyType.QUANTITY_DISCOUNT));
        assertTrue(result.getDiscountPolicies().containsKey(DiscountPolicyType.PERCENTAGE_DISCOUNT));
    }

    @Test
    void createProduct_shouldReturnProductWithMappedDiscountPoliciesRules() throws MissingRequiredArgumentException {
        productJpa = ProductJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .name("Product 1")
                .basePrice(new BigDecimal("100.00"))
                .productDiscountPoliciesJpa(List.of(quantityProductDiscountPolicyJpa, percentageProductDiscountPolicyJpa))
                .build();

        Product result = productFactory.createProduct(productJpa);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        var mappedQuantityDiscountPolicy = (QuantityDiscountPolicy) result.getDiscountPolicies().get(DiscountPolicyType.QUANTITY_DISCOUNT);
        var mappedPercentageDiscountPolicy = (PercentageDiscountPolicy) result.getDiscountPolicies().get(DiscountPolicyType.PERCENTAGE_DISCOUNT);

        assertEquals(quantityProductDiscountPolicyJpa.getDiscountPolicyJpa().getId(), mappedQuantityDiscountPolicy.getId());
        assertEquals(quantityProductDiscountPolicyJpa.getDiscountPolicyJpa().getUniqueId(), mappedQuantityDiscountPolicy.getUniqueId());
        assertEquals(quantityProductDiscountPolicyJpa.getDiscountPolicyJpa().getType(), mappedQuantityDiscountPolicy.getType());
        assertEquals(quantityProductDiscountPolicyJpa.getDiscountPolicyJpa().getPriority(), mappedQuantityDiscountPolicy.getPriority());

        assertEquals(percentageProductDiscountPolicyJpa.getDiscountPolicyJpa().getId(), mappedPercentageDiscountPolicy.getId());
        assertEquals(percentageProductDiscountPolicyJpa.getDiscountPolicyJpa().getUniqueId(), mappedPercentageDiscountPolicy.getUniqueId());
        assertEquals(percentageProductDiscountPolicyJpa.getDiscountPolicyJpa().getType(), mappedPercentageDiscountPolicy.getType());
        assertEquals(percentageProductDiscountPolicyJpa.getDiscountPolicyJpa().getPriority(), mappedPercentageDiscountPolicy.getPriority());

        var mappedQuantityDiscountPolicyRules = mappedQuantityDiscountPolicy.getDiscountRules();
        var mappedPercentageDiscountPolicyRules = mappedPercentageDiscountPolicy.getDiscountRules();

        for (QuantityDiscountRuleJpa rule : quantityDiscountRulesJpa) {
            var mappedRule = mappedQuantityDiscountPolicyRules.stream()
                    .filter(v -> rule.getId().equals(v.getId()))
                    .findFirst()
                    .orElseThrow(() -> new MissingRequiredArgumentException(String.format("Rule was not found %s", rule)));

            assertEquals(rule.getId(), mappedRule.getId());
            assertEquals(rule.getUniqueId(), mappedRule.getUniqueId());
            assertEquals(rule.getMinQuantity(), mappedRule.getMinQuantity());
            assertEquals(rule.getMaxQuantity(), mappedRule.getMaxQuantity());
            assertEquals(rule.getDiscountPercent(), mappedRule.getDiscountPercent());
        }

        for (PercentageDiscountRuleJpa rule : percentageDiscountRulesJpa) {
            var mappedRule = mappedPercentageDiscountPolicyRules.stream()
                    .filter(v -> rule.getId().equals(v.getId()))
                    .findFirst()
                    .orElseThrow(() -> new MissingRequiredArgumentException(String.format("Rule was not found %s", rule)));

            assertEquals(rule.getId(), mappedRule.getId());
            assertEquals(rule.getUniqueId(), mappedRule.getUniqueId());
            assertEquals(rule.getDiscountPercent(), mappedRule.getDiscountPercent());
        }
    }

    @Test
    void createProduct_shouldHandleNoDiscountPolicies() {
        productJpa = ProductJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .name("Product 1")
                .basePrice(new BigDecimal("100.00"))
                .productDiscountPoliciesJpa(Collections.emptyList())
                .build();

        Product result = productFactory.createProduct(productJpa);

        assertNotNull(result);
        assertTrue(result.getDiscountPolicies().isEmpty());
    }

    @Test
    void createProduct_shouldReturnProductWithOnlyPercentageDiscountPolicy() {
        productJpa = ProductJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .name("Product 1")
                .basePrice(new BigDecimal("100.00"))
                .productDiscountPoliciesJpa(Collections.singletonList(percentageProductDiscountPolicyJpa))
                .build();

        Product result = productFactory.createProduct(productJpa);

        assertNotNull(result);
        assertEquals(1, result.getDiscountPolicies().size());
        assertTrue(result.getDiscountPolicies().containsKey(DiscountPolicyType.PERCENTAGE_DISCOUNT));
    }

    @Test
    void createProduct_shouldReturnProductWithOnlyQuantityDiscountPolicy() {
        productJpa = ProductJpa.builder()
                .id(1L)
                .uniqueId(UUID.randomUUID())
                .name("Product 1")
                .basePrice(new BigDecimal("100.00"))
                .productDiscountPoliciesJpa(Collections.singletonList(quantityProductDiscountPolicyJpa))
                .build();

        Product result = productFactory.createProduct(productJpa);

        assertNotNull(result);
        assertEquals(1, result.getDiscountPolicies().size());
        assertTrue(result.getDiscountPolicies().containsKey(DiscountPolicyType.QUANTITY_DISCOUNT));
    }
}



