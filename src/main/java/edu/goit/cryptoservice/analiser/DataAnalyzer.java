package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.CryptoCurrency;
import edu.goit.cryptoservice.entity.DataAnalyserDto;
import edu.goit.cryptoservice.entity.PairResult;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DataAnalyzer implements BaseDataAnalyser {
    
    /*
    I tried to simplify the implementation as much as possible.
    I did not set the task of optimizing performance, but this does not mean that you need to perform useless actions. 
    For example, if a client wants to receive several recommendations for the same time period, then you need to iterate over all the values every time. this is weird
    */

    @Override
    public Optional<PairResult> getMinValue(DataAnalyserDto dto) {        
        if (dto.getIterable() == null || dto.getStartPeriod() == null || dto.getEndPeriod() == null) return Optional.empty();
        else return filterByDates(dto)
                .min((o1, o2) -> Double.compare(o1.getPrice().doubleValue(), o2.getPrice().doubleValue()))
                .map(PairResult::new);
    }

    @Override
    public Optional<PairResult> getMaxValue(DataAnalyserDto dto) {
        if (dto.getIterable() == null || dto.getStartPeriod() == null || dto.getEndPeriod() == null) return Optional.empty();
        else return filterByDates(dto)
                .max((o1, o2) -> Double.compare(o1.getPrice().doubleValue(), o2.getPrice().doubleValue()))
                .map(PairResult::new);
    }

    @Override
    public Optional<PairResult> getAverageValue(DataAnalyserDto dto) {
        if (dto.getIterable() == null || dto.getStartPeriod() == null || dto.getEndPeriod() == null) return Optional.empty();
        OptionalDouble average = filterByDates(dto)
                .mapToDouble(e -> e.getPrice().doubleValue())
                .average();
        return average.isPresent() ? 
                Optional.of(new PairResult(dto.getCryptoName(), BigDecimal.valueOf(average.getAsDouble())))
                        : Optional.empty();
    }
    
    private Stream<CryptoCurrency> filterByDates(DataAnalyserDto dto) {
        return StreamSupport.stream(dto.getIterable().spliterator(), false)
                .filter(cur -> cur.getTimestamp().after(dto.getStartPeriod()))
                .filter(cur -> cur.getTimestamp().before(dto.getEndPeriod()));
    }

}
