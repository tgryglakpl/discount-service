package com.ala.discountservice.domain.model.discount.policy;

public interface DiscountPolicy {

    DiscountPolicyType getType();
    Integer getPriority();

}
