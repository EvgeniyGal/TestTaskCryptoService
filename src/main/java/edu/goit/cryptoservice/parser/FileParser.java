package edu.goit.cryptoservice.parser;

import edu.goit.cryptoservice.entity.BaseCurrency;

import java.util.List;
import java.util.Optional;

public class FileParser<E extends BaseCurrency<? extends Number>> implements BaseFileParser<E> {

    private final Class<E> entityClass;

    public FileParser(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<List<E>> parse(String filePath) {

        Optional<FileTypes> currentFileType = getFileType(filePath);

        return currentFileType.isPresent() ? currentFileType.get().getFileTypeParser()
                .parseFile(entityClass, filePath) : Optional.empty();

    }


    private Optional<FileTypes> getFileType(String filePath) {

        String strFileType = filePath.replaceAll("^.*\\.", "");

        for (FileTypes type : FileTypes.values()
        ) {
            if (strFileType.equals(type.name())) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

}


