package edu.goit.cryptoservice.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goit.cryptoservice.entity.BaseCurrency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TXTFileParser implements BaseFileTypeParser {

    @Override
    public <E extends BaseCurrency<? extends Number>> List<E> parseFile(Class<E> entityClass, String filePath) {
               ObjectMapper objectMapper = new ObjectMapper();

        List<E> data = new ArrayList<>();

        try (var reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.ready()) {
                data.add(objectMapper.readValue(reader.readLine(), entityClass));
            }
            return data;
        } catch (IOException e) {
            return data;
        }
    }
}


