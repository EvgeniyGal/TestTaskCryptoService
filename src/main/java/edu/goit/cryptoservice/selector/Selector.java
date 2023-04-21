package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.analiser.BaseDataAnalyser;
import edu.goit.cryptoservice.analiser.DataAnalyzer;
import edu.goit.cryptoservice.entity.BaseCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.StreamSupport;

public class Selector<E extends BaseCurrency<? extends Number>> implements BaseSelector<E> {

    private final BaseDataAnalyser<E> analyzer = new DataAnalyzer<>();

    @Override
    public Optional<E> selectByMinValue(Iterable<? extends Iterable<E>> currencyData,
                                        Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        TreeMap<BigDecimal, Iterable<E>> analiseData = new TreeMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> {
                    if (e != null)
                        analyzer.getMinValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e));
                });

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }

        return StreamSupport.stream(analiseData.firstEntry().getValue().spliterator(), false)
                .filter(e -> e.getPrice().equals(analiseData.firstKey())).findFirst();
    }

    @Override
    public Optional<E> selectByMaxValue(Iterable<? extends Iterable<E>> currencyData,
                                        Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        TreeMap<BigDecimal, Iterable<E>> analiseData = new TreeMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> {
                    if (e != null)
                        analyzer.getMaxValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e));
                });

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }
        return StreamSupport.stream(analiseData.lastEntry().getValue().spliterator(), false)
                .filter(e -> e.getPrice().equals(analiseData.lastKey())).findFirst();
    }

    @Override
    public Optional<E> selectByAverageValue(Iterable<? extends Iterable<E>> currencyData,
                                            Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        HashMap<BigDecimal, Iterable<E>> analiseData = new HashMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> {
                    if (e != null)
                        analyzer.getAverageValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e));
                });

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal average = getAverage(analiseData);

        BigDecimal closestToAverage = analiseData.keySet().stream()
                .min(Comparator.comparing(k -> k.subtract(average).abs())).orElse(BigDecimal.ZERO);

        return StreamSupport.stream(analiseData.get(closestToAverage).spliterator(), false)
                .min(Comparator.comparing(k -> BigDecimal.valueOf(k.getPrice().doubleValue()).subtract(average).abs()));
    }

    @Override
    public Optional<E> selectByNormaliseValue(Iterable<? extends Iterable<E>> currencyData, Date startPeriod, Date endPeriod) {

        if (currencyData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        HashMap<BigDecimal, Iterable<E>> analiseData = new HashMap<>();

        StreamSupport.stream(currencyData.spliterator(), false)
                .forEach(e -> {
                    if (e != null)
                        analyzer.getNormaliseValue(e, startPeriod, endPeriod).ifPresent(bD -> analiseData.put(bD, e));
                });

        if (analiseData.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal average = getAverage(analiseData);

        BigDecimal closestToNormalise = analiseData.keySet().stream()
                .min(Comparator.comparing(k -> k.subtract(average).abs())).orElse(BigDecimal.ZERO);

        return StreamSupport.stream(analiseData.get(closestToNormalise).spliterator(), false)
                .min(Comparator.comparing(k -> BigDecimal.valueOf(k.getPrice().doubleValue()).subtract(average).abs()));
    }

    private BigDecimal getAverage(HashMap<BigDecimal, Iterable<E>> analiseData) {
        BigDecimal sumAverage = analiseData.keySet().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sumAverage.divide(BigDecimal.valueOf(analiseData.size()), 2, RoundingMode.HALF_UP);
    }

}
