package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.List;
import java.util.Optional;

public interface BaseFileTypeParser {
    <E extends BaseCurrency<? extends Number>> Optional<List<E>> parseFile(Class<E> entityClass, String filePath);
}
