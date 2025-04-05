package com.ala.discountservice.adapters.http;

import com.ala.discountservice.application.CalculationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/discount")
@Slf4j
@RequiredArgsConstructor
public class DiscountController {

    private final CalculationService calculationService;

    @Operation(
            summary = "Calculate discount for a product",
            description = "Calculates the discount for a given product based on its ID and quantity."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Successfully calculated discount",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiscountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public DiscountDto calculatePrice(
            @Parameter(description = "The unique ID of the product, example = bd367a29-7d12-42c4-9f7f-8b7cad7e8f67")
            @RequestParam UUID productId,
            @Parameter(description = "The quantity of the product, example = 10")
            @RequestParam @Min(1) Integer quantity) {
        log.info("Calculating discount for product uuid {} and quantity {}", productId, quantity);
        return new DiscountDto(calculationService.calculateDiscount(productId, quantity));
    }

    public record DiscountDto(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")BigDecimal discount) {
    }

}
