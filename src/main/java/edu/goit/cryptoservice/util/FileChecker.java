package edu.goit.cryptoservice.util;

import lombok.SneakyThrows;

import java.io.FileNotFoundException;

public class FileChecker {

    @SneakyThrows
    public static FileTypes getFileType(String filePath) {

        String strType = filePath.replaceAll("^.*\\.", "");

        for (FileTypes type : FileTypes.values()
        ) {
            if (strType.equals(type.name())) {
                return type;
            }
        }

        throw new FileNotFoundException("This file type doesn't support");
    }

}
