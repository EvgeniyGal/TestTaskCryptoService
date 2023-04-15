package edu.goit.cryptoservice.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileParserFactory {
    private final Map<String, BaseParser> Parsers;

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

    public synchronized <E> BaseParser<E> of(Class<E> entityClass, String filePath) {
        if (!Parsers.containsKey(entityClass.getName())) {
            BaseParser fileParser = new FileParser(entityClass, filePath);
            Parsers.put(entityClass.getName(), fileParser);
        }
        return Parsers.get(entityClass.getName());
    }

}
