package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.Date;
import java.util.Optional;

public interface BaseSelector<E extends BaseCurrency> {
    Optional<E> selectByMinValue(Iterable<Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
    Optional<E> selectByMaxValue(Iterable<Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
    Optional<E> selectByAverageValue(Iterable<Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
    Optional<E> selectByNormaliseValue(Iterable<Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
}
