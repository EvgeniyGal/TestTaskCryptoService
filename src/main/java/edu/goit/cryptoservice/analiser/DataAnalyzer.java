package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.StreamSupport;

public class DataAnalyzer<E extends BaseCurrency<? extends Number>> implements BaseDataAnalyser<E> {

    @Override
    public Optional<BigDecimal> getMinValue(Iterable<E> cryptoData, Date startPeriod, Date endPeriod) {

        if (cryptoData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        return StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .map(E::getPrice)
                .map(e -> BigDecimal.valueOf(e.doubleValue()))
                .min(BigDecimal::compareTo);
    }

    @Override
    public Optional<BigDecimal> getMaxValue(Iterable<E> cryptoData, Date startPeriod, Date endPeriod) {

        if (cryptoData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        return StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .map(E::getPrice)
                .map(e -> BigDecimal.valueOf(e.doubleValue()))
                .max(BigDecimal::compareTo);
    }

    @Override
    public Optional<BigDecimal> getAverageValue(Iterable<E> cryptoData, Date startPeriod, Date endPeriod) {

        if (cryptoData == null || startPeriod == null || endPeriod == null) {
            return Optional.empty();
        }

        BigDecimal size = BigDecimal.valueOf(StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .count());
        return Optional.of(StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .map(E::getPrice)
                .map(e -> BigDecimal.valueOf(e.doubleValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add).divide(size, 4, RoundingMode.HALF_UP));
    }

}
