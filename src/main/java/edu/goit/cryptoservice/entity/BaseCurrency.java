package edu.goit.cryptoservice.entity;

import java.math.BigDecimal;

public interface BaseCurrency {
    BigDecimal getPrice();
    void setPrice(BigDecimal price);
}
