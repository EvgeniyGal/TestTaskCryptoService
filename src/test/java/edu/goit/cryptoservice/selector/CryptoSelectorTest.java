package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.parser.FileParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CryptoSelectorTest {

    static List<Iterable<CryptoCurrency>> cryptoData = new ArrayList<>();
    CryptoSelector cryptoSelector = new CryptoSelector();
    final Date startPeriod = new Date(1641110400000L);
    final Date endPeriod = new Date(1643148000000L);

    @BeforeAll
    static void beforeAll() {
        FileParser<CryptoCurrency> fileParser = new FileParser<>(CryptoCurrency.class, "src/test/resources/BTC_values.txt");
        cryptoData.add(fileParser.parse().get());
        cryptoData.add(fileParser.changeFile("src/test/resources/DOGE_values.csv").parse().get());
        cryptoData.add(fileParser.changeFile("src/test/resources/ETH_values.txt").parse().get());
        cryptoData.add(fileParser.changeFile("src/test/resources/LTC_values.csv").parse().get());
        cryptoData.add(fileParser.changeFile("src/test/resources/XRP_values.csv").parse().get());
    }


    @Test
    void selectByMinValue() throws ParseException {
        assertEquals("DOGE", cryptoSelector.selectByMinValue(cryptoData, startPeriod, endPeriod).get().getSymbol());
        assertEquals(BigDecimal.valueOf(0.129), cryptoSelector.selectByMinValue(cryptoData, startPeriod, endPeriod).get().getPrice());
    }

    @Test
    void selectByMaxValue() {
        assertEquals("BTC", cryptoSelector.selectByMaxValue(cryptoData, startPeriod, endPeriod).get().getSymbol());
        assertEquals(BigDecimal.valueOf(473.3698), cryptoSelector.selectByMaxValue(cryptoData, startPeriod, endPeriod).get().getPrice());
    }

    @Test
    void selectByAverageValue() {
        assertEquals("LTC", cryptoSelector.selectByAverageValue(cryptoData, startPeriod, endPeriod).get().getSymbol());
        assertEquals(BigDecimal.valueOf(111.9), cryptoSelector.selectByAverageValue(cryptoData, startPeriod, endPeriod).get().getPrice());
    }

    @Test
    void selectByNormalyseValue() {
        assertEquals("DOGE", cryptoSelector.selectByNormaliseValue(cryptoData, startPeriod, endPeriod).get().getSymbol());
        assertEquals(BigDecimal.valueOf(0.1941), cryptoSelector.selectByNormaliseValue(cryptoData, startPeriod, endPeriod).get().getPrice());
    }
}