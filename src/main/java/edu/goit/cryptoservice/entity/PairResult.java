package edu.goit.cryptoservice.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PairResult implements Comparable<PairResult> {

    private String symbol;

    private BigDecimal price;

    public PairResult(CryptoCurrency cryptoCurrency) {
        this.symbol = cryptoCurrency.getSymbol();
        this.price = cryptoCurrency.getPrice();
    }

    @Override
    public int compareTo(PairResult o) {
        return this.price.compareTo(o.getPrice());
    }

}
