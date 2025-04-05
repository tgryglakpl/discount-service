package com.ala.discountservice.adapters.http;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataHelper testDataHelper;

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
    public void shouldCalculateDiscountSuccessfully() throws Exception {
        int validQuantity = 5;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/discount")
                        .param("productId", productId.toString())
                        .param("quantity", String.valueOf(validQuantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discount").value("475.00"));
    }

    @Test
    public void shouldReturn404IfProductNotFound() throws Exception {
        UUID nonExistingProductId = UUID.randomUUID();
        int validQuantity = 5;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/discount")
                        .param("productId", nonExistingProductId.toString())
                        .param("quantity", String.valueOf(validQuantity)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn400ForInvalidProductIdFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/discount")
                        .param("productId", "invalid-uuid")
                        .param("quantity", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400ForInvalidQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/discount")
                        .param("productId", productId.toString())
                        .param("quantity", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400IfQuantityIsNegative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/discount")
                        .param("productId", productId.toString())
                        .param("quantity", "-5"))
                .andExpect(status().isBadRequest());
    }
}