package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public interface BaseAnalyser<E extends BaseCurrency> {
    Optional<BigDecimal> getMinValue(Iterable<E> cryptoData);

    Optional<BigDecimal> getMaxValue(Iterable<E> cryptoData);

    Optional<BigDecimal> getAverageValue(Iterable<E> cryptoData);

    default Optional<BigDecimal> getNormalyseValue(Iterable<E> cryptoData) {
        Optional<BigDecimal> minOptional = getMinValue(cryptoData);
        Optional<BigDecimal> maxOptional = getMaxValue(cryptoData);
        if (minOptional.isPresent() && maxOptional.isPresent()) {
            BigDecimal min = minOptional.get();
            BigDecimal max = maxOptional.get();
            return Optional.of(max.subtract(min).divide(min, 4, RoundingMode.HALF_UP));
        } else {
            return Optional.empty();
        }
    }


}
