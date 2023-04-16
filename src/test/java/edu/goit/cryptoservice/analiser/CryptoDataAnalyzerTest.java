package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.parser.FileParser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CryptoDataAnalyzerTest {

    final List<CryptoCurrency> currencies = (new FileParser<>(CryptoCurrency.class,
            "src/test/resources/XRP_values.csv").parse().get());
    final Date startPeriod = new Date(1641283200000L);
    final Date endPeriod = new Date(1642093200000L);
    final CryptoDataAnalyzer cryptoDataAnalyzer = new CryptoDataAnalyzer();

    @Test
    void getMinValueTest() {
        assertEquals(BigDecimal.valueOf(0.7226), cryptoDataAnalyzer.getMinValue(currencies, startPeriod, endPeriod).get());
    }

    @Test
    void getMaxValueTest() {
        assertEquals(BigDecimal.valueOf(0.8337), cryptoDataAnalyzer.getMaxValue(currencies, startPeriod, endPeriod).get());
    }


    @Test
    void getAverageValueTest() {
        assertEquals(BigDecimal.valueOf(0.7683), cryptoDataAnalyzer.getAverageValue(currencies, startPeriod, endPeriod).get());
    }

    @Test
    void getNormalizeValueTest() {
        assertEquals(BigDecimal.valueOf(0.1538), cryptoDataAnalyzer.getNormaliseValue(currencies, startPeriod, endPeriod).get());
    }

}