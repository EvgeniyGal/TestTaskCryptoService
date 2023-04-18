package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class TXTFileParserTest {

    @Test
    void parseFilTest() throws ParseException {

        final BaseFileTypeParser fileParser = new TXTFileParser();

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

        List<CryptoCurrency> actual = fileParser.parseFile(CryptoCurrency.class,
                "src/test/resources/ETH_values2.txt").orElse(new ArrayList<>());

        assertEquals(expected, actual);

    }
}