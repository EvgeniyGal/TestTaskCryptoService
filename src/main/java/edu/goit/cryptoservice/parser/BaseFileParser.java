package edu.goit.cryptoservice.parser;

import java.util.List;
import java.util.Optional;

public interface BaseFileParser<E> {
    Optional<List<E>> parseFile(String filePath);
}
