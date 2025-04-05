package com.ala.discountservice.domain.model.discount.policy;

import com.ala.discountservice.domain.model.discount.rule.PercentageDiscountRule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import static com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType.PERCENTAGE_DISCOUNT;

@Value
@Builder
@EqualsAndHashCode
public class PercentageDiscountPolicy implements DiscountPolicy {

    @JsonIgnore
    Long id;
    UUID uniqueId;
    Integer priority;

    List<PercentageDiscountRule> discountRules;

    @Override
    public DiscountPolicyType getType() {
        return PERCENTAGE_DISCOUNT;
    }
}
