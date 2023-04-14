package edu.goit.cryptoservice.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParserFactory {
    private final Map<String, BaseFileParser> baseParsers;

    private static ParserFactory parserFactory;

    public static ParserFactory getInstance() {
        if (parserFactory == null) {
            parserFactory = new ParserFactory();
        }
        return parserFactory;
    }

    public ParserFactory() {
        this.baseParsers = new ConcurrentHashMap<>();
    }

    public synchronized <E> BaseFileParser<E> of(Class<E> entityClass) {
        if (!baseParsers.containsKey(entityClass.getName())) {
            BaseFileParser fileParser = new FileParser(entityClass);
            baseParsers.put(entityClass.getName(), fileParser);
        }
        return baseParsers.get(entityClass.getName());
    }

}
