package com.finance.trading.service;

import com.finance.trading.entity.Product;
import com.finance.trading.exception.InternalException;
import com.finance.trading.exception.ValidationException;
import com.finance.trading.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TradingServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private TradingService tradingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnrichTrades() throws InternalException, ValidationException {
        // Mock product data
        final Map<Integer, Product> products = new HashMap<>();
        products.put(1, new Product(1, "Product1"));
        products.put(2, new Product(2, "Product2"));
        when(productRepository.findAll()).thenReturn(products);

        final String trades = "date,product_name,currency,price\n" +
                "20160101,1,EUR,10.0\n" +
                "20160101,2,EUR,20.1";

        final String expectedTrades = "date,product_name,currency,price\n" +
                "20160101,Product1,EUR,10\n" +
                "20160101,Product2,EUR,20.1";

        final String result = tradingService.enrichTrades(trades);
        assertEquals(expectedTrades, result);
    }
}
