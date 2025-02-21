package ru.spshop.projects.compare2files.oldExel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class XlsReader {

    public HSSFWorkbook xlsRead(InputStream fileInputStream) {
        try {
            // Чтение .xls файла
            return new HSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
