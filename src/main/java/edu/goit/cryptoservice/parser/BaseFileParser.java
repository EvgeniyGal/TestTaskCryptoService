package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.List;
import java.util.Optional;

public interface BaseFileParser<E extends BaseCurrency<? extends Number>> {
    Optional<List<E>> parse(String filePath);
}
