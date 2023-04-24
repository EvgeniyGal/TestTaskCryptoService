package edu.goit.cryptoservice.parser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FileParser<E> implements BaseFileParser<E> {

    private final Class<E> entityClass;

    public FileParser(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<E> parse(String filePath) {

        Optional<FileTypes> currentFileType = getFileType(filePath);

        /*
        we return a blank list for unsupported formats. 
        in this case, you need to throw an exception. 
        the client should know that we cannot process the specific document.
        he expects a correct solution, and this is possible only with complete initial data. 
        the client will use this libary and will not even know that we are not analyzing the entire amount of data!!!
        */
        return currentFileType.isPresent() ? currentFileType.get().getFileTypeParser()
                .parseFile(entityClass, filePath) : Collections.EMPTY_LIST;

    }

    private Optional<FileTypes> getFileType(String filePath) {

        String strFileType = filePath.replaceAll("^.*\\.", "");

        for (FileTypes type : FileTypes.values()) {
            if (strFileType.equals(type.name())) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

}
