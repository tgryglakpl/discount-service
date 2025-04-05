package com.ala.discountservice.adapters.persistence.discount;

import com.ala.discountservice.adapters.persistence.discount.percentage.PercentageDiscountRuleJpa;
import com.ala.discountservice.adapters.persistence.discount.quantity.QuantityDiscountRuleJpa;
import com.ala.discountservice.domain.model.discount.policy.DiscountPolicyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "discount_policy")
public class DiscountPolicyJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", unique = true, updatable = false)
    private UUID uniqueId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountPolicyType type;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int priority;

    @Builder.Default
    @OneToMany(mappedBy = "discountPolicyJpa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuantityDiscountRuleJpa> quantityDiscountRuleJpas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "discountPolicyJpa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PercentageDiscountRuleJpa> percentageDiscountRuleJpas = new ArrayList<>();

    public void addQuantityDiscountRuleJpa(List<QuantityDiscountRuleJpa> quantityDiscountRuleJpas){
        this.quantityDiscountRuleJpas.addAll(quantityDiscountRuleJpas);
    }

    public void addPercentageDiscountRuleJpa(List<PercentageDiscountRuleJpa> percentageDiscountRuleJpas){
        this.percentageDiscountRuleJpas.addAll(percentageDiscountRuleJpas);
    }
}
