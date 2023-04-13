package edu.goit.cryptoservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {
    private String symbol;
    private BigInteger price;
    private Date timestamp;
}


