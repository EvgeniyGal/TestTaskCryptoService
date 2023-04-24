package edu.goit.cryptoservice;

import edu.goit.cryptoservice.analiser.BaseDataAnalyser;
import edu.goit.cryptoservice.analiser.DataAnalyzer;
import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.entity.DataAnalyserDto;
import edu.goit.cryptoservice.parser.BaseFileParser;
import edu.goit.cryptoservice.parser.FileParserFactory;
import edu.goit.cryptoservice.selector.BaseSelector;
import edu.goit.cryptoservice.selector.Selector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        
        /*
        why is the client code so complicated??? 
        we didn't provide a convenient interface for using our library? 
        wasn't that the target of the our task? (please read the first two sentences in the assignment)   
        */
        
        FileParserFactory factory = FileParserFactory.getInstance();
        BaseFileParser<CryptoCurrency> fileParser = factory.of(CryptoCurrency.class);

        List<Iterable<CryptoCurrency>> cryptoData = new ArrayList<>();
        
        //we cant read files from resources in this way (for example, Application.class.getResourceAsStream(...))
        cryptoData.add(fileParser.parse("src/main/resources/prices/BTC_values.txt"));
        cryptoData.add(fileParser.parse("src/main/resources/prices/DOGE_values.csv"));
        cryptoData.add(fileParser.parse("src/main/resources/prices/ETH_values.txt"));
        cryptoData.add(fileParser.parse("src/main/resources/prices/LTC_values.csv"));
        cryptoData.add(fileParser.parse("src/main/resources/prices/XRP_values.csv"));

        //Date in fact its deprecated class. Please use LocalDate (and another classes from java.time package)
        final Date startPeriod = new Date(1641283200000L);
        final Date endPeriod = new Date(1642093200000L);

        //so client steel have link on real class : new DataAnalyzer() and  new Selector();
        BaseDataAnalyser dataAnalyzer = new DataAnalyzer();
        BaseSelector selector = new Selector();

        //do we really have to do this on the client side?? but why doesn't our library return the desired result???
        System.out.println("\nGet min value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getMinValue(new DataAnalyserDto(e, startPeriod, endPeriod))
                .ifPresent(r -> System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        System.out.println("\nGet max value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getMaxValue(new DataAnalyserDto(e, startPeriod, endPeriod))
                .ifPresent(r -> System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        System.out.println("\nGet average value from Data");
        System.out.println("\nGet max value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getAverageValue(new DataAnalyserDto(e, startPeriod, endPeriod))
                .ifPresent(r -> System.out.println(e.iterator().next().getSymbol() + " - " + r)));

        System.out.println("\nGet normalise value from Data");
        cryptoData.forEach(e -> dataAnalyzer.getNormaliseValue(new DataAnalyserDto(e, startPeriod, endPeriod))
                .ifPresent(r -> System.out.println(e.iterator().next().getSymbol() + " - " + r)));

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
