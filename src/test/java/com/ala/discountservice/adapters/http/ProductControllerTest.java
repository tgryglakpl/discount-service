package com.ala.discountservice.adapters.http;


import com.ala.discountservice.adapters.exception.GlobalExceptionHandler;
import com.ala.discountservice.utils.TestDataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataHelper testDataHelper;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private final UUID productId = UUID.randomUUID();
    private final String productName = "Test Product";

    @BeforeEach
    public void setup() {
        testDataHelper.cleanup();
        testDataHelper.createProductWithDiscountPolicies(productId, productName);
    }

    @AfterEach
    public void cleanup() {
        testDataHelper.cleanup();
    }

    @Test
    public void shouldRetrieveProductDetailsSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.uniqueId").value(productId.toString()))
                .andExpect(jsonPath("$.product.name").value(productName));
    }

    @Test
    public void shouldReturn404IfProductNotFound() throws Exception {
        UUID nonExistingProductId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/" + nonExistingProductId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn400ForInvalidProductIdFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
