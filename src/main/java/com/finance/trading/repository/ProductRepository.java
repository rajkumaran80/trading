package com.finance.trading.repository;

import com.finance.trading.entity.Product;
import com.finance.trading.exception.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private final String productFile;

    public ProductRepository(@Value("${product.file}") String productFile) {
        this.productFile = productFile;
    }

    public Map<Integer, Product> findAll() throws InternalException {
        try {
            final String fileContent = Files.readString(Path.of(this.getClass().getClassLoader().getResource(productFile).getFile()));

            final String[] productLines = fileContent.split("\n");

            final List<Product> products = Arrays.stream(productLines, 1, productLines.length)
                    .map(line -> line.split(","))
                    .map(fields -> new Product(Integer.parseInt(fields[0]), fields[1]))
                    .collect(Collectors.toList());

            return products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));

        } catch (Exception e) {
            throw new InternalException("Unable to read the product file", e);
        }
    }

}
