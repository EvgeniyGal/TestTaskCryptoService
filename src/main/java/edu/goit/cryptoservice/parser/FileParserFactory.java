package edu.goit.cryptoservice.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileParserFactory {
    private final Map<String, BaseFileParser> Parsers;

    private static FileParserFactory fileParserFactory;

    public static FileParserFactory getInstance() {
        if (fileParserFactory == null) {
            fileParserFactory = new FileParserFactory();
        }
        return fileParserFactory;
    }

    private FileParserFactory() {
        this.Parsers = new ConcurrentHashMap<>();
    }

    public synchronized <E> BaseFileParser of(Class<E> entityClass) {
        if (!Parsers.containsKey(entityClass.getName())) {
            BaseFileParser fileParser = new FileParser(entityClass);
            Parsers.put(entityClass.getName(), fileParser);
        }
        return Parsers.get(entityClass.getName());
    }

}
