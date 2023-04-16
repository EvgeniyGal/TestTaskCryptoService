package edu.goit.cryptoservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoCurrency implements BaseCurrency<BigDecimal> {
    private String symbol;
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal price;
    private Date timestamp;

}


