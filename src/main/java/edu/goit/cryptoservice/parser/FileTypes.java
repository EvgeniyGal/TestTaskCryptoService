package edu.goit.cryptoservice.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileTypes {

    //we have to use capital letters : CSV, TXT
    txt(new TXTFileParser()),
    csv(new CSVFileParser());
    
    private final BaseFileTypeParser fileTypeParser;

}
