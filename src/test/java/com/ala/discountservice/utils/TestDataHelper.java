package com.ala.discountservice.utils;

import com.ala.discountservice.adapters.persistence.discount.DiscountPolicyJpa;
import com.ala.discountservice.adapters.persistence.discount.SpringDiscountPolicyRepository;
import com.ala.discountservice.adapters.persistence.discount.percentage.PercentageDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.discount.quantity.QuantityDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.product.ProductDiscountPolicyJpa;
import com.ala.discountservice.adapters.persistence.product.ProductJpa;
import com.ala.discountservice.adapters.persistence.product.SpringProductDiscountPolicyRepository;
import com.ala.discountservice.adapters.persistence.product.SpringProductRepository;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TestDataHelper {

    @Autowired
    private SpringProductRepository springProductRepository;
    @Autowired
    private SpringDiscountPolicyRepository springDiscountPolicyRepository;
    @Autowired
    private SpringProductDiscountPolicyRepository springProductDiscountPolicyJpa;

    @Transactional
    public void cleanup() {
        springProductDiscountPolicyJpa.deleteAll();
        springDiscountPolicyRepository.deleteAll();
        springProductRepository.deleteAll();
    }

    @Transactional
    public void createProductWithDiscountPolicies(UUID productId, String name) {
        var quantityDiscountPolicyJpa = createQuantityDiscountPolicyWithRules();
        var percentageDiscountPolicyJpa = createPercentageDiscountPolicyWithRules();
        springDiscountPolicyRepository.saveAll(List.of(quantityDiscountPolicyJpa, percentageDiscountPolicyJpa));

        var productJpa = ProductJpa.builder()
                .uniqueId(productId)
                .name(name)
                .basePrice(new BigDecimal("100.00"))
                .build();
        springProductRepository.save(productJpa);

        var productDiscountPolicies = createProductDiscountPolicies(productJpa, quantityDiscountPolicyJpa, percentageDiscountPolicyJpa);
        productJpa.addProductDiscountPolicyJpa(productDiscountPolicies);
        springProductDiscountPolicyJpa.saveAll(productDiscountPolicies);
    }

    private static List<ProductDiscountPolicyJpa> createProductDiscountPolicies(ProductJpa productJpa, DiscountPolicyJpa quantityDiscountPolicyJpa, DiscountPolicyJpa percentageDiscountPolicyJpa) {
        return List.of(
                ProductDiscountPolicyJpa.builder()
                        .uniqueId(UUID.randomUUID())
                        .productJpa(productJpa)
                        .discountPolicyJpa(quantityDiscountPolicyJpa)
                        .build(),
                ProductDiscountPolicyJpa.builder()
                        .uniqueId(UUID.randomUUID())
                        .productJpa(productJpa)
                        .discountPolicyJpa(percentageDiscountPolicyJpa)
                        .build()
        );
    }

    private static DiscountPolicyJpa createQuantityDiscountPolicyWithRules() {
        var quantityDiscountPolicyJpa = DiscountPolicyJpa.builder()
                .uniqueId(UUID.randomUUID())
                .type(DiscountPolicyType.QUANTITY_DISCOUNT)
                .priority(1)
                .build();

        quantityDiscountPolicyJpa.addQuantityDiscountRuleJpa(List.of(
                QuantityDiscountRuleJpa.builder()
                        .uniqueId(UUID.randomUUID())
                        .minQuantity(1)
                        .maxQuantity(10)
                        .discountPercent(new BigDecimal("5.00"))
                        .discountPolicyJpa(quantityDiscountPolicyJpa)
                        .build(),
                QuantityDiscountRuleJpa.builder()
                        .uniqueId(UUID.randomUUID())
                        .minQuantity(11)
                        .maxQuantity(50)
                        .discountPercent(new BigDecimal("10.00"))
                        .discountPolicyJpa(quantityDiscountPolicyJpa)
                        .build()
        ));
        return quantityDiscountPolicyJpa;
    }

    private DiscountPolicyJpa createPercentageDiscountPolicyWithRules() {
        var percentageDiscountPolicyJpa = DiscountPolicyJpa.builder()
                .uniqueId(UUID.randomUUID())
                .type(DiscountPolicyType.PERCENTAGE_DISCOUNT)
                .priority(2)
                .build();

        percentageDiscountPolicyJpa.addPercentageDiscountRuleJpa(List.of(
                PercentageDiscountRuleJpa.builder()
                        .uniqueId(UUID.randomUUID())
                        .discountPercent(new BigDecimal("20.00"))
                        .discountPolicyJpa(percentageDiscountPolicyJpa)
                        .build()
        ));
        return percentageDiscountPolicyJpa;
    }
}
