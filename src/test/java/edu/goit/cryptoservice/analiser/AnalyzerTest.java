package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.Currency;
import edu.goit.cryptoservice.parser.FileParser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerTest {

    Analyzer analyzer = new Analyzer();
    FileParser<Currency> parser = new FileParser<>(Currency.class);


    @Test
    void getMinValueTest() {
        Optional<List<Currency>> currencies = parser.parseFile("src/test/resources/DOGE_values.csv");
        assertEquals(BigDecimal.valueOf(0.1702), analyzer.getMinValue(currencies.get()).get());
    }

    @Test
    void getMaxValueTest() {
        Optional<List<Currency>> currencies = parser.parseFile("src/test/resources/DOGE_values.csv");
        assertEquals(BigDecimal.valueOf(0.1722), analyzer.getMaxValue(currencies.get()).get());
    }


    @Test
    void getAverageValueTest() {
        Optional<List<Currency>> currencies = parser.parseFile("src/test/resources/DOGE_values.csv");
        assertEquals(BigDecimal.valueOf(0.1712), analyzer.getAverageValue(currencies.get()).get());
    }

    @Test
    void getNormalizeValueTest() {
        Optional<List<Currency>> currencies = parser.parseFile("src/test/resources/DOGE_values.csv");
        assertEquals(BigDecimal.valueOf(0.0118), analyzer.getNormalyseValue(currencies.get()).get());
    }

}