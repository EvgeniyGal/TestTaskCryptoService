package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.Date;
import java.util.Optional;

public interface BaseSelector<E extends BaseCurrency<? extends Number>> {
    Optional<E> selectByMinValue(Iterable<? extends Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
    Optional<E> selectByMaxValue(Iterable<? extends Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
    Optional<E> selectByAverageValue(Iterable<? extends Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
    Optional<E> selectByNormaliseValue(Iterable<? extends Iterable<E>> currencyData, Date startPeriod, Date endPeriod);
}
