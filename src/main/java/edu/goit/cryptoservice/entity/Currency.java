package edu.goit.cryptoservice.entity;

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
public class Currency implements BaseCurrency{
    private String symbol;
    private BigDecimal price;
    private Date timestamp;
}


