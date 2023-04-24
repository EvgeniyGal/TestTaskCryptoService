package edu.goit.cryptoservice.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import edu.goit.cryptoservice.entity.CryptoCurrency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVFileParser implements BaseFileTypeParser {

    @Override
    public <E> List<E> parseFile(Class<E> entityClass, String filePath) {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .setUseHeader(true)
                .build();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new MillisecondsDateDeserializer());
        mapper.registerModule(module);

        try (MappingIterator<E> it = mapper.readerFor(CryptoCurrency.class).with(schema).readValues(new File(filePath))) {
            return it.readAll();
        } catch (IOException e) {
            //its incorrect. i want to now about exception when reading source files. 
            //if I can't read everything, then further work is pointless.
            //and this despite the fact that the TXTFileParser throws an exception (@SneakyThrows). different behavior for classes that implements the same interface?!
            return new ArrayList<>();
        }
    }
}
