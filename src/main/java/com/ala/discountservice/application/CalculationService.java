package com.ala.discountservice.application;

import com.ala.discountservice.domain.model.discount.policy.DiscountPolicy;
import com.ala.discountservice.domain.ports.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final ProductRepository productRepository;
    private final CalculationServiceFactory calculatorFactory;

    @Transactional(readOnly = true)
    public BigDecimal calculateDiscount(UUID productUuid, Integer quantity) {
        var product = productRepository.findProductWithDiscountPolicies(productUuid);
        var basePrice = product.getBasePrice().multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
        //Apply first applicable discount with the highest priority
        return product.getDiscountPolicies()
                .values()
                .stream()
                .sorted(Comparator.comparingInt(DiscountPolicy::getPriority))
                .map(DiscountPolicy::getType)
                .map(calculatorFactory::getCalculator)
                .map(calculator -> calculator.applyDiscount(product,quantity))
                .filter(v -> v.compareTo(basePrice) < 0)
                .findFirst()
                .orElse(basePrice);
    }
}
