package com.finance.trading.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.finance.trading.constant.Constant.DATE_TIME_FORMATTER;

@Data
public class Trade {
    private LocalDate date;
    private Product product;
    private String currency;
    private BigDecimal price;

    public String toString() {
        return String.join(",",
                date.format(DATE_TIME_FORMATTER),
                product.getProductName(),
                currency,
                price.stripTrailingZeros().toPlainString());
    }
}
