package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

    private final FileParser<Currency> fileParser = new FileParser<>(Currency.class);

    @Test
    void parseFileTestTXT() throws ParseException {

        List<Currency> expected = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        expected.add(Currency.builder()
                .symbol("ETH")
                .price(BigDecimal.valueOf(371532))
                .timestamp(dateFormat.parse("2022-01-01T08:00"))
                .build());
        expected.add(Currency.builder()
                .symbol("ETH")
                .price(BigDecimal.valueOf(371867))
                .timestamp(dateFormat.parse("2022-01-01T10:00"))
                .build());
        List<Currency> actual = fileParser.parseFile("src/test/resources/ETH_values.txt").get();

        assertEquals(expected, actual);

    }

    @Test
    void parseFileTestCSV() {

        List<Currency> expected = new ArrayList<>();
        expected.add(Currency.builder()
                .symbol("DOGE")
                .price(BigDecimal.valueOf(0.1702))
                .timestamp(new Date(1641013200000L))
                .build());
        expected.add(Currency.builder()
                .symbol("DOGE")
                .price(BigDecimal.valueOf(0.1722))
                .timestamp(new Date(1641074400000L))
                .build());

        List<Currency> actual = fileParser.parseFile("src/test/resources/DOGE_values.csv").get();

        assertEquals(expected, actual);

    }


}