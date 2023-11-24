package com.finance.trading;

import com.finance.trading.service.TradingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TradingApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TradingApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradingService tradingService;

    @Test
    void testEnrichTrades() throws Exception {
        final String tradeInput = Files.readString(Path.of(this.getClass().getClassLoader().getResource("data/trade_valid.csv").getFile()));
        final String expectedOutput = Files.readString(Path.of(this.getClass().getClassLoader().getResource("data/trade_output.csv").getFile()));

        mockMvc.perform(post("/api/v1/enrich")
                        .content(tradeInput)
                        .contentType(MediaType.parseMediaType("text/csv"))
                        .accept(MediaType.parseMediaType("text/csv")))
                .andExpect(status().isOk())
                .andExpect(content().string((expectedOutput)));
    }

    @Test
    void testEnrichTradesFailsDueToInvalidInput() throws Exception {
        final String tradeInput = Files.readString(Path.of(this.getClass().getClassLoader().getResource("data/trade_invalid.csv").getFile()));

        mockMvc.perform(post("/api/v1/enrich")
                        .content(tradeInput)
                        .contentType(MediaType.parseMediaType("text/csv"))
                        .accept(MediaType.parseMediaType("text/csv")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file content"));
    }
}
