package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

public interface BaseFileParser<E extends BaseCurrency<? extends Number>> extends BaseParser<E> {
    BaseFileParser<E> changeFile(String filePath);
}
