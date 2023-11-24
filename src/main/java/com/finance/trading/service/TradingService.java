package com.finance.trading.service;

import com.finance.trading.entity.Product;
import com.finance.trading.entity.Trade;
import com.finance.trading.exception.InternalException;
import com.finance.trading.exception.ValidationException;
import com.finance.trading.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.finance.trading.constant.Constant.DATE_TIME_FORMATTER;

@Service
@Slf4j
public class TradingService {
    private static final String HEADER = "date,product_name,currency,price\n";

    private final ProductRepository productRepository;

    public TradingService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String enrichTrades(final String tradesString) throws ValidationException, InternalException {

        final Map<Integer, Product> products = productRepository.findAll();

        final String[] tradeLines = tradesString.split("\n");

        final List<Trade> enrichedTrades;
        try {
            enrichedTrades = Arrays.stream(tradeLines, 1, tradeLines.length)
                    .map(line -> line.split(","))
                    .map(fields -> enrichTrade(products, fields))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ValidationException("Invalid file content", e);
        }

        return HEADER + enrichedTrades.stream().map(Trade::toString).collect(Collectors.joining("\n"));
    }

    private static Trade enrichTrade(final Map<Integer, Product> products, final String[] tradeLineFields) {
        final Trade trade = new Trade();

        try {
            final LocalDate date = LocalDate.parse(tradeLineFields[0], DATE_TIME_FORMATTER);
            trade.setDate(date);
        } catch (DateTimeParseException e) {
            log.error("Date {} should be in the format of YYYYMMDD", tradeLineFields[0]);
            return null;
        }

        Product product = products.get(Integer.parseInt(tradeLineFields[1]));
        if (product == null) {
            product = Product.MISSING_PRODUCT;
            log.error("Product name is not available for product id {}", tradeLineFields[1]);
        }
        trade.setProduct(product);
        trade.setCurrency(tradeLineFields[2]);
        trade.setPrice(new BigDecimal(tradeLineFields[3]));
        return trade;
    }

}
