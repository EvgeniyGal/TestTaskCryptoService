package edu.goit.cryptoservice;

import edu.goit.cryptoservice.analiser.DataAnalyzer;
import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.parser.BaseFileParser;
import edu.goit.cryptoservice.parser.FileParserFactory;
import edu.goit.cryptoservice.selector.Selector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        FileParserFactory factory = FileParserFactory.getInstance();
        BaseFileParser<CryptoCurrency> fileParser = factory.of(CryptoCurrency.class);

        List<Iterable<CryptoCurrency>> cryptoData = new ArrayList<>();
        fileParser.parse("src/main/resources/prices/BTC_values.txt").ifPresent(cryptoData::add);
        fileParser.parse("src/main/resources/prices/DOGE_values.csv").ifPresent(cryptoData::add);
        fileParser.parse("src/main/resources/prices/ETH_values.txt").ifPresent(cryptoData::add);
        fileParser.parse("src/main/resources/prices/LTC_values.csv").ifPresent(cryptoData::add);
        fileParser.parse("src/main/resources/prices/XRP_values.csv").ifPresent(cryptoData::add);

        final Date startPeriod = new Date(1641283200000L);
        final Date endPeriod = new Date(1642093200000L);

        DataAnalyzer<CryptoCurrency> dataAnalyzer = new DataAnalyzer<>();
        Selector<CryptoCurrency> selector = new Selector<>();

        System.out.println("\nGet min value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getMinValue(e, startPeriod, endPeriod).ifPresent(r ->
                System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        System.out.println("\nGet max value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getMaxValue(e, startPeriod, endPeriod).ifPresent(r ->
                System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        System.out.println("\nGet average value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getAverageValue(e, startPeriod, endPeriod).ifPresent(r ->
                System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        System.out.println("\nGet normalise value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getNormaliseValue(e, startPeriod, endPeriod).ifPresent(r ->
                System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        selector.selectByMinValue(cryptoData, startPeriod, endPeriod)
                .ifPresent(r -> System.out.println("\nBest crypto value by min\n" + r));
        selector.selectByMaxValue(cryptoData, startPeriod, endPeriod)
                .ifPresent(r -> System.out.println("\nBest crypto value by max\n" + r));
        selector.selectByAverageValue(cryptoData, startPeriod, endPeriod)
                .ifPresent(r -> System.out.println("\nBest crypto value by average\n" + r));
        selector.selectByNormaliseValue(cryptoData, startPeriod, endPeriod)
                .ifPresent(r -> System.out.println("\nBest crypto value by normalise\n" + r));

    }
}
