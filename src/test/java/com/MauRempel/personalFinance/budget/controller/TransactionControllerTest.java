package com.MauRempel.personalFinance.budget.controller;

import com.MauRempel.personalFinance.budget.exception.ResourceNotFoundException;
import com.MauRempel.personalFinance.budget.service.TransactionService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService service;

    @Test
    void getBalanceShouldReturnBalance() throws Exception{
        when(service.calculateBalance()).thenReturn(new BigDecimal("849.50"));

        mockMvc.perform(get("/transactions/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(849.50));
    }

    @Test
    void getByIdShouldReturnNotFoundWhenTransactionDoesNotExist() throws Exception {
        when(service.findById(99L))
                .thenThrow(new ResourceNotFoundException("Transaction with id 99 not found"));

        mockMvc.perform(get("/transactions/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Transaction with id 99 not found"));
    }

    @Test
    void addShouldReturnBadRequestWhenAmountisMissing() throws Exception{
        String requestBody = """
                    {
                        "type": "EXPENSE",
                        "category": "FOOD",
                        "timestamp": "2026-05-10T12:00:00",
                        "description": "Lunch"
                    }
                """;

        mockMvc.perform(post("/transactions")
                    .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                    .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("amount: Amount is required"));
    }
}
