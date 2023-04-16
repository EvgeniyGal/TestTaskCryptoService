package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class FileFileParserTest {

    final FileParser<CryptoCurrency> fileParser = new FileParser<>(CryptoCurrency.class, "src/test/resources/ETH_values2.txt");

    @Test
    void parseFileTestTXT() throws ParseException {

        List<CryptoCurrency> expected = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        expected.add(CryptoCurrency.builder()
                .symbol("ETH")
                .price(BigDecimal.valueOf(37.1532))
                .timestamp(dateFormat.parse("2022-01-01T08:00"))
                .build());
        expected.add(CryptoCurrency.builder()
                .symbol("ETH")
                .price(BigDecimal.valueOf(37.1867))
                .timestamp(dateFormat.parse("2022-01-01T10:00"))
                .build());
        List<CryptoCurrency> actual = fileParser.parse().orElse(new ArrayList<>());

        assertEquals(expected, actual);

    }

    @Test
    void parseFileTestCSV() {
        fileParser.changeFile("src/test/resources/DOGE_values2.csv");
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

        List<CryptoCurrency> actual = fileParser.parse().orElse(new ArrayList<>());

        assertEquals(expected, actual);

    }
}