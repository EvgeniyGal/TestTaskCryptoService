package edu.goit.cryptoservice;

import edu.goit.cryptoservice.analiser.CryptoDataAnalyzer;
import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.parser.FileParser;
import edu.goit.cryptoservice.parser.FileParserFactory;
import edu.goit.cryptoservice.selector.CryptoSelector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        FileParserFactory factory = FileParserFactory.getInstance();
        FileParser<CryptoCurrency> fileParser = factory.of(CryptoCurrency.class, "src/main/resources/prices/BTC_values.txt");

        List<Iterable<CryptoCurrency>> cryptoData = new ArrayList<>();
        fileParser.parse().ifPresent(cryptoData::add);
        fileParser.changeFile("src/main/resources/prices/DOGE_values.csv").parse().ifPresent(cryptoData::add);
        fileParser.changeFile("src/main/resources/prices/ETH_values.txt").parse().ifPresent(cryptoData::add);
        fileParser.changeFile("src/main/resources/prices/LTC_values.csv").parse().ifPresent(cryptoData::add);
        fileParser.changeFile("src/main/resources/prices/XRP_values.csv").parse().ifPresent(cryptoData::add);

        final Date startPeriod = new Date(1641283200000L);
        final Date endPeriod = new Date(1642093200000L);

        CryptoDataAnalyzer dataAnalyzer = new CryptoDataAnalyzer();
        CryptoSelector selector = new CryptoSelector();

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
