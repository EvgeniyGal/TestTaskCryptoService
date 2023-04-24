package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.entity.PairResult;

import java.util.Date;
import java.util.Optional;

// please see comments for interface BaseDataAnalyser
public interface BaseSelector {
    //Iterable<Iterable<CryptoCurrency>>  - iterable of iteravble? is it really necessary? 
    //this greatly complicates the implementation. we need a simpler solution
    Optional<PairResult> selectByMinValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod);
    Optional<PairResult> selectByMaxValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod);
    Optional<PairResult> selectByAverageValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod);
    Optional<PairResult> selectByNormaliseValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod);
}
