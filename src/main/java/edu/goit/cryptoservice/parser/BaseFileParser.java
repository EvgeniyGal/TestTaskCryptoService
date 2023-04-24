package edu.goit.cryptoservice.parser;

import java.util.List;

public interface BaseFileParser<E> {
    List<E> parse(String filePath);
}
