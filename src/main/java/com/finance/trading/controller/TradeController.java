package com.finance.trading.controller;

import com.finance.trading.exception.InternalException;
import com.finance.trading.exception.ValidationException;
import com.finance.trading.service.TradingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TradeController {

    private final TradingService tradingService;

    public TradeController(final TradingService tradingService) {
        this.tradingService = tradingService;
    }

    @PostMapping(path = "/enrich", consumes = "text/csv", produces = "text/csv")
    public ResponseEntity<String> enrichTrades(@RequestBody String fileContent) throws ValidationException, InternalException {
        return new ResponseEntity<>(tradingService.enrichTrades(fileContent), HttpStatus.OK);
    }
}
