package edu.goit.cryptoservice.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class TXTFileParser implements BaseFileTypeParser {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    @Override
    public <E> List<E> parseFile(Class<E> entityClass, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> mapToEntity(line, entityClass))
                    .collect(Collectors.toList());
        }
    }

    @SneakyThrows
    private <E> E mapToEntity(String line, Class<E> entityClass) {
        return MAPPER.readValue(line, entityClass);
    }
}
