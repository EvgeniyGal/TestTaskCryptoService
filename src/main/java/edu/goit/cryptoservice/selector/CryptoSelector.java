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
    public Optional<CryptoCurrency> selectByMinValue(Iterable<Iterable<CryptoCurrency>> currencyData,
                                                     Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        TreeMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new TreeMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> analyzer.getMinValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e)));

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }

        return StreamSupport.stream(analiseData.firstEntry().getValue().spliterator(), false)
                .filter(e -> e.getPrice().equals(analiseData.firstKey())).findFirst();
    }

    @Override
    public Optional<CryptoCurrency> selectByMaxValue(Iterable<Iterable<CryptoCurrency>> currencyData,
                                                     Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        TreeMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new TreeMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> analyzer.getMaxValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e)));

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }
        return StreamSupport.stream(analiseData.lastEntry().getValue().spliterator(), false)
                .filter(e -> e.getPrice().equals(analiseData.lastKey())).findFirst();
    }

    @Override
    public Optional<CryptoCurrency> selectByAverageValue(Iterable<Iterable<CryptoCurrency>> currencyData,
                                                         Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        HashMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new HashMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> analyzer.getAverageValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e)));

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal average = getAverage(analiseData);

        BigDecimal closestToAverage = analiseData.keySet().stream()
                .min(Comparator.comparing(k -> k.subtract(average).abs())).orElse(BigDecimal.ZERO);

        return StreamSupport.stream(analiseData.get(closestToAverage).spliterator(), false)
                .min(Comparator.comparing(k -> k.getPrice().subtract(average).abs()));
    }

    @Override
    public Optional<CryptoCurrency> selectByNormaliseValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        HashMap<BigDecimal, Iterable<CryptoCurrency>> analiseData = new HashMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> analyzer.getNormaliseValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e)));

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal average = getAverage(analiseData);

        BigDecimal closestToNormalise = analiseData.keySet().stream()
                .min(Comparator.comparing(k -> k.subtract(average).abs())).orElse(BigDecimal.ZERO);

        return StreamSupport.stream(analiseData.get(closestToNormalise).spliterator(), false)
                .min(Comparator.comparing(k -> k.getPrice().subtract(average).abs()));
    }

    private BigDecimal getAverage(HashMap<BigDecimal, Iterable<CryptoCurrency>> analiseData) {
        BigDecimal sumAverage = analiseData.keySet().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sumAverage.divide(BigDecimal.valueOf(analiseData.size()), 2, RoundingMode.HALF_UP);
    }

}
