package edu.goit.cryptoservice.util;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileCheckerTest {


    @Test
    void getFileTypeTest() {
        assertEquals("txt", FileChecker.getFileType("myfile.txt").name());
        assertEquals("txt", FileChecker.getFileType("myfvd.dfbdfb.bile.txt").name());
        assertEquals("csv", FileChecker.getFileType("newfile.csv").name());
        assertEquals("csv", FileChecker.getFileType("wsd/wdwd/wdwd/dwd/newfile.csv").name());
        assertThrows(FileNotFoundException.class, () -> FileChecker.getFileType("wsd/wdwd/wdwd/dwd/newfile.tewfxt"));
    }


}