package edu.goit.cryptoservice.selector;

import edu.goit.cryptoservice.analiser.BaseDataAnalyser;
import edu.goit.cryptoservice.analiser.DataAnalyzer;
import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.entity.DataAnalyserDto;
import edu.goit.cryptoservice.entity.PairResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Selector implements BaseSelector {
    
    /*
    I tried to simplify the implementation as much as possible.
    According to the condition, the formula for calculating the normalized value is (max-min)/min
    The implementation for some reason used the AVG. i fixed it
    
    The next problem is that the assignment required us to provide the client with the ability to combine a analyzer and selector. 
    For example, the analyzer selects the maximum values for each cryptocurrency, and the selector among these values looks for something close to the normalized one. 
    This implementation does not allow this.
    
    You need to pay attention to the use of the stream api in this class. the implementation that was provided looks very odd
    */

    private final BaseDataAnalyser analyzer = new DataAnalyzer();

    @Override
    public Optional<PairResult> selectByMinValue(Iterable<Iterable<CryptoCurrency>> currencyData, Date startPeriod, Date endPeriod) {
        return getResultStream(currencyData, startPeriod, endPeriod, analyzer::getMinValue).min((o1, o2) -> o1.compareTo(o2));
    }

    @Override
    public Optional<PairResult> selectByMaxValue(Iterable<Iterable<CryptoCurrency>> currencyData,
            Date startPeriod, Date endPeriod) {
        return getResultStream(currencyData, startPeriod, endPeriod, analyzer::getMaxValue).max((o1, o2) -> o1.compareTo(o2));
    }
    
    @Override
    public Optional<PairResult> selectByAverageValue(Iterable<Iterable<CryptoCurrency>> currencyData,
            Date startPeriod, Date endPeriod) {        
        TreeSet<PairResult> analiseData = getResultStream(currencyData, startPeriod, endPeriod, analyzer::getAverageValue)
                .collect(Collectors.toCollection(TreeSet::new));
        if (analiseData.isEmpty()) return Optional.empty();
        BigDecimal sumAverage = analiseData.stream().map(PairResult::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        
        /*
        we round the value. this is acceptable for our task, but in this case the use of BigDecimal makes no sense. 
        with the same success, you can use double. in the end, BigDecimal only made the code more complicated and did not bring any benefit.
        */
        BigDecimal average = sumAverage.divide(BigDecimal.valueOf(analiseData.size()), 2, RoundingMode.HALF_UP);
        
        return analiseData.stream().min(Comparator.comparing(k -> k.getPrice().subtract(average).abs()));
    }

    @Override
    public Optional<PairResult> selectByNormaliseValue(Iterable<Iterable<CryptoCurrency>> currencyData,
            Date startPeriod, Date endPeriod) {
        TreeSet<PairResult> analiseData = getResultStream(currencyData, startPeriod, endPeriod, analyzer::getNormaliseValue)
                .collect(Collectors.toCollection(TreeSet::new));
        if (analiseData.isEmpty()) return Optional.empty();
        BigDecimal min = analiseData.first().getPrice();
        BigDecimal max = analiseData.last().getPrice();
        BigDecimal normValue = max.subtract(min).divide(min, RoundingMode.HALF_UP);
        return analiseData.stream().min(Comparator.comparing(k -> k.getPrice().subtract(normValue).abs()));
    }
    
    private Stream<PairResult> getResultStream(Iterable<Iterable<CryptoCurrency>> currencyData, 
            Date startPeriod, Date endPeriod,  Function <DataAnalyserDto, Optional<PairResult>> function) {
        if (currencyData == null || startPeriod == null || endPeriod == null) return Stream.empty();
        else return StreamSupport.stream(currencyData.spliterator(), false)
                .filter(e -> e != null)
                .map(e -> function.apply(new DataAnalyserDto(e, startPeriod, endPeriod)))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

}
