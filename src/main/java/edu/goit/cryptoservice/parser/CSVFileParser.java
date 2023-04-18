package edu.goit.cryptoservice.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import edu.goit.cryptoservice.entity.BaseCurrency;
import edu.goit.cryptoservice.entity.CryptoCurrency;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CSVFileParser implements BaseFileTypeParser {

    @Override
    public <E extends BaseCurrency<? extends Number>> Optional<List<E>> parseFile(Class<E> entityClass, String filePath) {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .setUseHeader(true)
                .build();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new MillisecondsDateDeserializer());
        mapper.registerModule(module);

        try (MappingIterator<E> it = mapper.readerFor(CryptoCurrency.class).with(schema).readValues(new File(filePath))) {
            return Optional.of(it.readAll());
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}


