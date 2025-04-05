package com.ala.discountservice.domain.model.discount.policy;

import com.ala.discountservice.domain.model.discount.rule.QuantityDiscountRule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@EqualsAndHashCode
public class QuantityDiscountPolicy implements DiscountPolicy {

    @JsonIgnore
    Long id;
    UUID uniqueId;
    Integer priority;

    List<QuantityDiscountRule> discountRules;

    @Override
    public DiscountPolicyType getType() {
        return DiscountPolicyType.QUANTITY_DISCOUNT;
    }

}
