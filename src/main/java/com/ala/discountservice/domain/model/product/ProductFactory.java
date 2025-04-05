package com.ala.discountservice.domain.model.product;

import com.ala.discountservice.adapters.persistence.discount.DiscountPolicyJpa;
import com.ala.discountservice.adapters.persistence.discount.percentage.PercentageDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.discount.quantity.QuantityDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.product.ProductDiscountPolicyJpa;
import com.ala.discountservice.adapters.persistence.product.ProductJpa;
import com.ala.discountservice.domain.model.Product;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicy;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import com.ala.discountservice.domain.model.discount.policy.PercentageDiscountPolicy;
import com.ala.discountservice.domain.model.discount.policy.QuantityDiscountPolicy;
import com.ala.discountservice.domain.model.discount.rule.PercentageDiscountRule;
import com.ala.discountservice.domain.model.discount.rule.QuantityDiscountRule;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

import static com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType.PERCENTAGE_DISCOUNT;
import static com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType.QUANTITY_DISCOUNT;

@Component
public class ProductFactory {

    public Product createProduct(ProductJpa productJpa) {
        return Product.builder()
                .id(productJpa.getId())
                .uniqueId(productJpa.getUniqueId())
                .name(productJpa.getName())
                .basePrice(productJpa.getBasePrice())
                .discountPolicies(mapDiscountPolicies(productJpa.getProductDiscountPoliciesJpa()))
                .build();
    }

    private Map<DiscountPolicyType, DiscountPolicy> mapDiscountPolicies(List<ProductDiscountPolicyJpa> productDiscountPoliciesJpa) {
        Map<DiscountPolicyType, DiscountPolicy> discountPolicies = new HashMap<>();
        List<DiscountPolicyJpa> discountPoliciesJpa = mapDiscountPoliciesJpa(productDiscountPoliciesJpa);

        findDiscountPolicyWithHighestPriority(discountPoliciesJpa, QUANTITY_DISCOUNT, this::mapQuantityDiscountPolicy)
                .ifPresent(discountPolicy -> discountPolicies.put(QUANTITY_DISCOUNT, discountPolicy));

        findDiscountPolicyWithHighestPriority(discountPoliciesJpa, PERCENTAGE_DISCOUNT, this::mapPercentageDiscountPolicy)
                .ifPresent(discountPolicy -> discountPolicies.put(PERCENTAGE_DISCOUNT, discountPolicy));

        return discountPolicies;
    }

    private Optional<DiscountPolicy> findDiscountPolicyWithHighestPriority(List<DiscountPolicyJpa> discountPoliciesJpa, DiscountPolicyType type, Function<DiscountPolicyJpa, DiscountPolicy> mapper) {
        return discountPoliciesJpa.stream()
                .filter(discountPolicyJpa -> discountPolicyJpa.getType().equals(type))
                .min(Comparator.comparingInt(DiscountPolicyJpa::getPriority))
                .map(mapper);
    }

    private List<DiscountPolicyJpa> mapDiscountPoliciesJpa(List<ProductDiscountPolicyJpa> productDiscountPoliciesJpa) {
        return Optional.ofNullable(productDiscountPoliciesJpa)
                .orElse(Collections.emptyList())
                .stream()
                .map(ProductDiscountPolicyJpa::getDiscountPolicyJpa)
                .filter(Objects::nonNull)
                .toList();
    }

    private QuantityDiscountPolicy mapQuantityDiscountPolicy(DiscountPolicyJpa discountPolicyJpa) {
        return QuantityDiscountPolicy.builder()
                .id(discountPolicyJpa.getId())
                .uniqueId(discountPolicyJpa.getUniqueId())
                .priority(discountPolicyJpa.getPriority())
                .discountRules(mapQuantityDiscountRules(discountPolicyJpa.getQuantityDiscountRuleJpas()))
                .build();
    }

    private PercentageDiscountPolicy mapPercentageDiscountPolicy(DiscountPolicyJpa discountPolicyJpa) {
        return PercentageDiscountPolicy.builder()
                .id(discountPolicyJpa.getId())
                .uniqueId(discountPolicyJpa.getUniqueId())
                .priority(discountPolicyJpa.getPriority())
                .discountRules(mapPercentageDiscountRules(discountPolicyJpa.getPercentageDiscountRuleJpas()))
                .build();
    }

    private List<QuantityDiscountRule> mapQuantityDiscountRules(List<QuantityDiscountRuleJpa> quantityDiscountRules) {
        return quantityDiscountRules.stream()
                .map(this::mapQuantityDiscountRule)
                .toList();
    }

    private List<PercentageDiscountRule> mapPercentageDiscountRules(List<PercentageDiscountRuleJpa> percentageDiscountRules) {
        return percentageDiscountRules.stream()
                .map(this::mapPercentageDiscountRule)
                .toList();
    }

    private QuantityDiscountRule mapQuantityDiscountRule(QuantityDiscountRuleJpa quantityDiscountRuleJpa) {
        return QuantityDiscountRule.builder()
                .id(quantityDiscountRuleJpa.getId())
                .uniqueId(quantityDiscountRuleJpa.getUniqueId())
                .minQuantity(quantityDiscountRuleJpa.getMinQuantity())
                .maxQuantity(quantityDiscountRuleJpa.getMaxQuantity())
                .discountPercent(quantityDiscountRuleJpa.getDiscountPercent())
                .build();
    }

    private PercentageDiscountRule mapPercentageDiscountRule(PercentageDiscountRuleJpa percentageDiscountRuleJpa) {
        return PercentageDiscountRule.builder()
                .id(percentageDiscountRuleJpa.getId())
                .uniqueId(percentageDiscountRuleJpa.getUniqueId())
                .discountPercent(percentageDiscountRuleJpa.getDiscountPercent())
                .build();
    }
}
