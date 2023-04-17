package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.parser.FileParser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalyzerTest {

    final List<CryptoCurrency> currencies = (new FileParser<>(CryptoCurrency.class,
            "src/test/resources/XRP_values.csv").parse().orElse(new ArrayList<>()));
    final Date startPeriod = new Date(1641283200000L);
    final Date endPeriod = new Date(1642093200000L);
    final DataAnalyzer<CryptoCurrency> dataAnalyzer = new DataAnalyzer<>();

    @Test
    void getMinValueTest() {
        assertEquals(BigDecimal.valueOf(0.7226), dataAnalyzer.getMinValue(currencies, startPeriod, endPeriod).orElse(null));
    }

    @Test
    void getMaxValueTest() {
        assertEquals(BigDecimal.valueOf(0.8337), dataAnalyzer.getMaxValue(currencies, startPeriod, endPeriod).orElse(null));
    }


    @Test
    void getAverageValueTest() {
        assertEquals(BigDecimal.valueOf(0.7683), dataAnalyzer.getAverageValue(currencies, startPeriod, endPeriod).orElse(null));
    }

    @Test
    void getNormalizeValueTest() {
        assertEquals(BigDecimal.valueOf(0.1538), dataAnalyzer.getNormaliseValue(currencies, startPeriod, endPeriod).orElse(null));
    }

}