package edu.goit.cryptoservice.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import edu.goit.cryptoservice.entity.CryptoCurrency;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FileParser<E> implements BaseParser<E> {

    private final Class<E> entityClass;

    private File file;
    private final FileParser<E> instance;

    public FileParser<E> changeFile(String filePath) {
        this.file = new File(filePath);
        return instance;
    }

    public FileParser(Class<E> entityClass, String filePath) {
        this.file = new File(filePath);
        this.entityClass = entityClass;
        instance = this;
    }

    @Override
    public Optional<List<E>> parse() {

        switch (getFileType(file.getPath())) {
            case csv:
                return parseCSVFile();
            case txt:
                return parseTXTFile();
            default:
                return Optional.empty();
        }

    }

    private Optional<List<E>> parseCSVFile() {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .setUseHeader(true)
                .build();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new MillisecondsDateDeserializer());
        mapper.registerModule(module);

        try (MappingIterator<E> it = mapper.readerFor(CryptoCurrency.class).with(schema).readValues(file)) {
            return Optional.of(it.readAll());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<List<E>> parseTXTFile() {

        ObjectMapper objectMapper = new ObjectMapper();

        try (var reader = new BufferedReader(new FileReader(file))) {
            List<E> data = new ArrayList<>();
            while (reader.ready()) {
                data.add(objectMapper.readValue(reader.readLine(), entityClass));
            }
            return Optional.of(data);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private FileTypes getFileType(String filePath) {

        String strFileType = filePath.replaceAll("^.*\\.", "");

        for (FileTypes type : FileTypes.values()
        ) {
            if (strFileType.equals(type.name())) {
                return type;
            }
        }
        return FileTypes.wrongType;
    }

}


