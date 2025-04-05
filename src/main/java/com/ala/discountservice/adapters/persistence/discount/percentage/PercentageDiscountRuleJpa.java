package com.ala.discountservice.adapters.persistence.discount.percentage;

import com.ala.discountservice.adapters.persistence.discount.DiscountPolicyJpa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "percentage_discount_rule")
public class PercentageDiscountRuleJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private UUID uniqueId;

    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "policy_id", nullable = false)
    private DiscountPolicyJpa discountPolicyJpa;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal discountPercent;
}
