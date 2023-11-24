package com.finance.trading.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    public static final Product MISSING_PRODUCT = new Product(-1, "Missing Product Name");
    private int productId;
    private String productName;
}
