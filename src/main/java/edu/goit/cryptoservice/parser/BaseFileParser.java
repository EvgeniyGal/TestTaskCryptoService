package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.List;

public interface BaseFileParser<E extends BaseCurrency<? extends Number>> {
    List<E> parse(String filePath);
}
