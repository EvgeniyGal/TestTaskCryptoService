package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

public interface BaseDataAnalyser<E extends BaseCurrency> {
    Optional<BigDecimal> getMinValue(Iterable<E> iterable, Date startPeriod, Date endPeriod);

    Optional<BigDecimal> getMaxValue(Iterable<E> iterable, Date startPeriod, Date endPeriod);

    Optional<BigDecimal> getAverageValue(Iterable<E> iterable, Date startPeriod, Date endPeriod);

    default Optional<BigDecimal> getNormaliseValue(Iterable<E> iterable, Date startPeriod, Date endPeriod) {
        Optional<BigDecimal> minOptional = getMinValue(iterable, startPeriod, endPeriod);
        Optional<BigDecimal> maxOptional = getMaxValue(iterable, startPeriod, endPeriod);
        if (minOptional.isPresent() && maxOptional.isPresent()) {
            BigDecimal min = minOptional.get();
            BigDecimal max = maxOptional.get();
            return Optional.of(max.subtract(min).divide(min, 4, RoundingMode.HALF_UP));
        } else {
            return Optional.empty();
        }
    }

}
