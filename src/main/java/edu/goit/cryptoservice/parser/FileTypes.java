package edu.goit.cryptoservice.parser;

public enum FileTypes {

    txt(new TXTFileParser()),
    csv(new CSVFileParser());

    private final BaseFileTypeParser fileTypeParser;

    FileTypes(BaseFileTypeParser fileTypeParser) {
        this.fileTypeParser = fileTypeParser;
    }

    public BaseFileTypeParser getFileTypeParser() {
        return fileTypeParser;
    }

}
