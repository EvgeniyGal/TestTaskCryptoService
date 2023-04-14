package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class Analyzer implements BaseAnalyser<Currency> {


    @Override
    public Optional<BigDecimal> getMinValue(Iterable<Currency> cryptoData) {
        if (cryptoData != null) {
            return StreamSupport.stream(cryptoData.spliterator(), false)
                    .map(Currency::getPrice).min(BigDecimal::compareTo);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getMaxValue(Iterable<Currency> cryptoData) {
        if (cryptoData != null) {
            return StreamSupport.stream(cryptoData.spliterator(), false)
                    .map(Currency::getPrice).max(BigDecimal::compareTo);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getAverageValue(Iterable<Currency> cryptoData) {
        if (cryptoData != null) {
            BigDecimal size = BigDecimal.valueOf(StreamSupport.stream(cryptoData.spliterator(),false).count());
            return Optional.of(StreamSupport.stream(cryptoData.spliterator(),false).map(Currency::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add).divide(size, 4, RoundingMode.HALF_UP));
        } else {
            return Optional.empty();
        }
    }
}
