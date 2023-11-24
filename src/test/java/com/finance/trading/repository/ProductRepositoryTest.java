package com.finance.trading.repository;

import com.finance.trading.entity.Product;
import com.finance.trading.exception.InternalException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Test
    void testFindAll() throws InternalException {
        productRepository = new ProductRepository("data/product_valid.csv");

        // Call the method to be tested
        final Map<Integer, Product> products = productRepository.findAll();

        // Assert the result
        assertEquals(2, products.size());
        assertEquals(new Product(1, "Product1"), products.get(1));
        assertEquals(new Product(2, "Product2"), products.get(2));
    }

    @Test
    void testFindAllWithException() {
        productRepository = new ProductRepository("data/product_invalid.csv");

        // Call the method to be tested and expect an InternalException
        InternalException exception = assertThrows(
                InternalException.class,
                () -> productRepository.findAll(),
                "Expected to throw InternalException"
        );

        // Assert the exception message
        assertEquals("Unable to read the product file", exception.getMessage());
    }
}
