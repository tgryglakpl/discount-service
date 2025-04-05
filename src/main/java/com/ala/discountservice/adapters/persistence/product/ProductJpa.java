package com.ala.discountservice.adapters.persistence.product;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "product")
public class ProductJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private UUID uniqueId;

    @Column(nullable = false)
    private String name;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Builder.Default
    @OneToMany(mappedBy = "productJpa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDiscountPolicyJpa> productDiscountPoliciesJpa = new ArrayList<>();

    public void addProductDiscountPolicyJpa(List<ProductDiscountPolicyJpa> productDiscountPoliciesJpa){
        this.productDiscountPoliciesJpa.addAll(productDiscountPoliciesJpa);
    }
}
