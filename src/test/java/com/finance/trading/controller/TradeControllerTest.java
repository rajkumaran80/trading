package com.finance.trading.controller;

import com.finance.trading.exception.InternalException;
import com.finance.trading.exception.ValidationException;
import com.finance.trading.service.TradingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TradeControllerTest {

    @Mock
    private TradingService tradingService;

    @InjectMocks
    private TradeController tradeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnrichTrades() throws ValidationException, InternalException {
        // Mock the enrichTrades method in TradingService
        when(tradingService.enrichTrades("Trades")).thenReturn("Enriched Trades");

        // Test the enrichTrades method in TradeController
        final ResponseEntity<String> responseEntity = tradeController.enrichTrades("Trades");

        // Assert the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Enriched Trades", responseEntity.getBody());
    }
}
