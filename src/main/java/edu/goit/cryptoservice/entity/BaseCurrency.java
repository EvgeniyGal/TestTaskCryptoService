package edu.goit.cryptoservice.entity;

import java.util.Date;

public interface BaseCurrency<E extends Number> {
    E getPrice();
    void setPrice(E price);
    Date getTimestamp();
}
