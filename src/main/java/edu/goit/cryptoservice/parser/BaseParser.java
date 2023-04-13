package edu.goit.cryptoservice.parser;

import java.util.List;

public interface BaseParser<E> {
    List<E> fileParse(String filePath);
}
