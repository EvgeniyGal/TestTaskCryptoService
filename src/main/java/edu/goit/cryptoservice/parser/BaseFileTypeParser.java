package edu.goit.cryptoservice.parser;

import java.util.List;

//we get file name like string in parametrs. this severely limits functionality for no reason. will be better to work with InputStream
public interface BaseFileTypeParser {
    <E> List<E> parseFile(Class<E> entityClass, String filePath);
}
