package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.List;

public interface BaseFileTypeParser {
    <E extends BaseCurrency<? extends Number>> List<E> parseFile(Class<E> entityClass, String filePath);
}
