package edu.goit.cryptoservice.entity;

import java.math.BigDecimal;

public interface BaseCurrency<E extends Number> {
    E getPrice();
    void setPrice(E price);
}
