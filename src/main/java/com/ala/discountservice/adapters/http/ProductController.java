package com.ala.discountservice.adapters.http;

import com.ala.discountservice.application.ProductService;
import com.ala.discountservice.domain.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Retrieve product details",
            description = "This endpoint returns the details of a product, including price and applicable discount policies, based on the provided productId."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product details",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid productId format")
    })
    @GetMapping("/{productId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProductDto retrieveProduct(
            @Parameter(description = "The unique ID of the product", example = "bd367a29-7d12-42c4-9f7f-8b7cad7e8f67")
            @PathVariable UUID productId) {
        log.info("Retrieving product for uuid {} ", productId);
        return new ProductDto(productService.findProductDetails(productId));
    }

    public record ProductDto(Product product) {}
}
