package edu.goit.cryptoservice.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileParserFactory {
    private final Map<String, FileParser> Parsers;

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

    public synchronized <E> FileParser of(Class<E> entityClass, String filePath) {
        if (!Parsers.containsKey(entityClass.getName())) {
            FileParser fileParser = new FileParser(entityClass, filePath);
            Parsers.put(entityClass.getName(), fileParser);
        }
        return Parsers.get(entityClass.getName());
    }

}
