package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.entity.BaseCurrency;
import edu.goit.cryptoservice.entity.Currency;

import java.util.Optional;

public interface BaseSelector<E extends BaseCurrency> {
    Optional<E> selectByMinValue(Iterable<Iterable<E>> currencyData);
    Optional<E> selectByMaxValue(Iterable<Iterable<E>> currencyData);
    Optional<E> selectByAverageValue(Iterable<Iterable<E>> currencyData);
    Optional<E> selectByNormalyseValue(Iterable<Iterable<E>> currencyData);
}
