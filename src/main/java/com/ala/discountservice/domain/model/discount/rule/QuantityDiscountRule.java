package com.ala.discountservice.domain.model.discount.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@EqualsAndHashCode
public class QuantityDiscountRule {
    @JsonIgnore
    Long id;
    UUID uniqueId;
    Integer minQuantity;
    Integer maxQuantity;
    BigDecimal discountPercent;
}
