package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.parser.FileParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SelectorTest {

    static List<Iterable<CryptoCurrency>> cryptoData = new ArrayList<>();
    Selector<CryptoCurrency> selector = new Selector<>();
    final Date startPeriod = new Date(1641110400000L);
    final Date endPeriod = new Date(1643148000000L);

    @BeforeAll
    static void beforeAll() {
        FileParser<CryptoCurrency> fileParser = new FileParser<>(CryptoCurrency.class);
        cryptoData.add(fileParser.parse("src/test/resources/BTC_values.txt").orElse(new ArrayList<>()));
        cryptoData.add(fileParser.parse("src/test/resources/DOGE_values.csv").orElse(new ArrayList<>()));
        cryptoData.add(fileParser.parse("src/test/resources/ETH_values.txt").orElse(new ArrayList<>()));
        cryptoData.add(fileParser.parse("src/test/resources/LTC_values.csv").orElse(new ArrayList<>()));
        cryptoData.add(fileParser.parse("src/test/resources/XRP_values.csv").orElse(new ArrayList<>()));
    }


    @Test
    void selectByMinValue() {
        assertEquals("DOGE", Objects.requireNonNull(selector.selectByMinValue(cryptoData, startPeriod, endPeriod).orElse(null)).getSymbol());
        assertEquals(BigDecimal.valueOf(0.129), Objects.requireNonNull(selector.selectByMinValue(cryptoData, startPeriod, endPeriod).orElse(null)).getPrice());
    }

    @Test
    void selectByMaxValue() {
        assertEquals("BTC", Objects.requireNonNull(selector.selectByMaxValue(cryptoData, startPeriod, endPeriod).orElse(null)).getSymbol());
        assertEquals(BigDecimal.valueOf(473.3698), Objects.requireNonNull(selector.selectByMaxValue(cryptoData, startPeriod, endPeriod).orElse(null)).getPrice());
    }

    @Test
    void selectByAverageValue() {
        assertEquals("LTC", Objects.requireNonNull(selector.selectByAverageValue(cryptoData, startPeriod, endPeriod).orElse(null)).getSymbol());
        assertEquals(BigDecimal.valueOf(111.9), Objects.requireNonNull(selector.selectByAverageValue(cryptoData, startPeriod, endPeriod).orElse(null)).getPrice());
    }

    @Test
    void selectByNormalyseValue() {
        assertEquals("DOGE", Objects.requireNonNull(selector.selectByNormaliseValue(cryptoData, startPeriod, endPeriod).orElse(null)).getSymbol());
        assertEquals(BigDecimal.valueOf(0.1941), Objects.requireNonNull(selector.selectByNormaliseValue(cryptoData, startPeriod, endPeriod).orElse(null)).getPrice());
    }
}