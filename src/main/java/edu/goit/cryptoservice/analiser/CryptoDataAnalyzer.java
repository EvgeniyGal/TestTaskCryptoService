package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.CryptoCurrency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.StreamSupport;

public class CryptoDataAnalyzer implements BaseDataAnalyser<CryptoCurrency> {

    @Override
    public Optional<BigDecimal> getMinValue(Iterable<CryptoCurrency> cryptoData, Date startPeriod, Date endPeriod) {
        return StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .map(CryptoCurrency::getPrice).min(BigDecimal::compareTo);
    }

    @Override
    public Optional<BigDecimal> getMaxValue(Iterable<CryptoCurrency> cryptoData, Date startPeriod, Date endPeriod) {
        return StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .map(CryptoCurrency::getPrice).max(BigDecimal::compareTo);
    }

    @Override
    public Optional<BigDecimal> getAverageValue(Iterable<CryptoCurrency> cryptoData, Date startPeriod, Date endPeriod) {

        BigDecimal size = BigDecimal.valueOf(StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .count());
        return Optional.of(StreamSupport.stream(cryptoData.spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(startPeriod))
                .filter(cur -> cur.getTimestamp().before(endPeriod))
                .map(CryptoCurrency::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add).divide(size, 4, RoundingMode.HALF_UP));
    }

}
