package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.analiser.CryptoDataAnalyzer;
import edu.goit.cryptoservice.entity.CryptoCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.StreamSupport;

public class CryptoSelector implements BaseSelector<CryptoCurrency> {

    private final CryptoDataAnalyzer analyzer = new CryptoDataAnalyzer();

    @Override
    public Optional<CryptoCurrency> selectByMinValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod) {

        TreeMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new TreeMap<>();

        for (Iterable<CryptoCurrency> currency : currencyData
        ) {
            BigDecimal minValue = analyzer.getMinValue(currency, startPeriod, endPeriod).orElse(BigDecimal.ZERO);
            analiseData.put(minValue, currency);
        }

        return StreamSupport.stream(analiseData.firstEntry().getValue().spliterator(),false)
                .filter(e -> e.getPrice().equals(analiseData.firstKey())).findFirst();
    }

    @Override
    public Optional<CryptoCurrency> selectByMaxValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod) {

        TreeMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new TreeMap<>();

        for (Iterable<CryptoCurrency> currency : currencyData
        ) {
            BigDecimal maxValue = analyzer.getMaxValue(currency, startPeriod, endPeriod).orElse(BigDecimal.ZERO);
            analiseData.put(maxValue, currency);
        }

        return StreamSupport.stream(analiseData.lastEntry().getValue().spliterator(),false)
                .filter(e -> e.getPrice().equals(analiseData.lastKey())).findFirst();
    }

    @Override
    public Optional<CryptoCurrency> selectByAverageValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod) {

        HashMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new HashMap<>();

        for (Iterable<CryptoCurrency> currency : currencyData
        ) {
            analiseData.put(analyzer.getAverageValue(currency, startPeriod, endPeriod).orElse(BigDecimal.ZERO),
                    currency);
        }

        BigDecimal sumAverage = analiseData.keySet().stream().reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalAverage = sumAverage.divide(BigDecimal.valueOf(analiseData.size()), 2, RoundingMode.HALF_UP);

        BigDecimal closestToAverage = analiseData.keySet().stream()
                .min(Comparator.comparing(k -> k.subtract(totalAverage).abs())).orElse(BigDecimal.ZERO);

        return StreamSupport.stream(analiseData.get(closestToAverage).spliterator(),false)
                .min(Comparator.comparing(k -> k.getPrice().subtract(totalAverage).abs()));
    }

    @Override
    public Optional<CryptoCurrency> selectByNormalyseValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod) {

        HashMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new HashMap<>();

        for (Iterable<CryptoCurrency> currency : currencyData
        ) {
            analiseData.put(analyzer.getNormalyseValue(currency, startPeriod, endPeriod).orElse(BigDecimal.ZERO),
                    currency);
        }

        BigDecimal sumNormalyse = analiseData.keySet().stream().reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalNormalyse = sumNormalyse.divide(BigDecimal.valueOf(analiseData.size()), 2, RoundingMode.HALF_UP);

        BigDecimal closestToNormalyse = analiseData.keySet().stream()
                .min(Comparator.comparing(k -> k.subtract(totalNormalyse).abs())).orElse(BigDecimal.ZERO);

        return StreamSupport.stream(analiseData.get(closestToNormalyse).spliterator(),false)
                .min(Comparator.comparing(k -> k.getPrice().subtract(totalNormalyse).abs()));
    }
}
