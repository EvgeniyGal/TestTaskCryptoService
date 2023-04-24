package edu.goit.cryptoservice.entity;

import java.util.Date;
import lombok.Value;

@Value
public class DataAnalyserDto {
    private Iterable<CryptoCurrency> iterable;
    private Date startPeriod;
    private Date endPeriod;
    
    //its a bad solution, but I can't do it in another way in this implementation
    public String getCryptoName() {
        return iterable.iterator().next().getSymbol();
    }
}
