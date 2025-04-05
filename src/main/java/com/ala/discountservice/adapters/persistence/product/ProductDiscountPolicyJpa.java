package com.ala.discountservice.adapters.persistence.product;

import com.ala.discountservice.adapters.persistence.discount.DiscountPolicyJpa;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "product_discount_policy")
public class ProductDiscountPolicyJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", unique = true, updatable = false)
    private UUID uniqueId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpa productJpa;

    @ManyToOne
    @JoinColumn(name = "discount_policy_id", nullable = false)
    private DiscountPolicyJpa discountPolicyJpa;
}
