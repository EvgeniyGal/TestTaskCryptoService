package edu.goit.cryptoservice.parser;

import java.util.List;
import java.util.Optional;

public interface BaseParser<E> {
    Optional<List<E>> parse();
}
