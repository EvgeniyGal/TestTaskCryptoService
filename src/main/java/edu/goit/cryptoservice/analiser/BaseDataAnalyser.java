package edu.goit.cryptoservice.analiser;

import edu.goit.cryptoservice.entity.DataAnalyserDto;
import edu.goit.cryptoservice.entity.PairResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/*
I removed the generic from this interface. 
it doesn't make sense and it's very similar to the soft code anti-pattern. 
this generic is tied to a very specific entity interface that describes its getters and setters. 
what other implementation of this interface can be implemented? 

besides, you don't use the rich domain model. it means that we dont need an interface to access the model from the service layers.
The interface of the model use generic, where the id extended of the Number class. What's the point of this? we don't use it at all. 
*/
public interface BaseDataAnalyser {
    
    Optional<PairResult> getMinValue(DataAnalyserDto dto);

    Optional<PairResult> getMaxValue(DataAnalyserDto dto);

    Optional<PairResult> getAverageValue(DataAnalyserDto dto);

    //its good solution (we use template method). 
    //there is only one problem. the interface is complex, we are unlikely to be able to add another implementation of it. 
    //and for a single implementation, the template method loses its meaning
    default Optional<PairResult> getNormaliseValue(DataAnalyserDto dto) {
        Optional<PairResult> minOptional = getMinValue(dto);
        Optional<PairResult> maxOptional = getMaxValue(dto);
        if (minOptional.isPresent() && maxOptional.isPresent()) {
            PairResult min = minOptional.get();
            PairResult max = maxOptional.get();
            BigDecimal result = max.getPrice().subtract(min.getPrice()).divide(min.getPrice(), 4, RoundingMode.HALF_UP);            
            return Optional.of(new PairResult(dto.getCryptoName(), result));
        } else {
            return Optional.empty();
        }
    }

}
