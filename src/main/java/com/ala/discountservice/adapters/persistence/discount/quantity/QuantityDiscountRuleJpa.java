package com.ala.discountservice.adapters.persistence.discount.quantity;

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
@Table(name = "quantity_discount_rule")
public class QuantityDiscountRuleJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private UUID uniqueId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "policy_id", nullable = false)
    private DiscountPolicyJpa discountPolicyJpa;

    @Column(nullable = false)
    private Integer minQuantity;

    @Column(nullable = false)
    private Integer maxQuantity;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal discountPercent;
}
