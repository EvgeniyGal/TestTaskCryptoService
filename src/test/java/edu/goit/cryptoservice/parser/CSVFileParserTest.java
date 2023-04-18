package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVFileParserTest {

    @Test
    void parseFileTest() {

        final BaseFileTypeParser fileParser = new CSVFileParser();

        List<CryptoCurrency> expected = new ArrayList<>();
        expected.add(CryptoCurrency.builder()
                .symbol("DOGE")
                .price(BigDecimal.valueOf(0.1702))
                .timestamp(new Date(1641013200000L))
                .build());
        expected.add(CryptoCurrency.builder()
                .symbol("DOGE")
                .price(BigDecimal.valueOf(0.1722))
                .timestamp(new Date(1641074400000L))
                .build());

        List<CryptoCurrency> actual = fileParser.parseFile(CryptoCurrency.class,
                "src/test/resources/DOGE_values2.csv").orElse(new ArrayList<>());

        assertEquals(expected, actual);

    }
}