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
public class PercentageDiscountRule {
    @JsonIgnore
    Long id;
    UUID uniqueId;
    BigDecimal discountPercent;
}
