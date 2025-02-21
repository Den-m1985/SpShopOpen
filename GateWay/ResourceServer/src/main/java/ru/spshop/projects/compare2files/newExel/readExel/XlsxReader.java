package ru.spshop.projects.compare2files.newExel.readExel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class XlsxReader {


    public Workbook xlsxRead(InputStream fileInputStream) {
        try {
            // Чтение .xlsx файла
            return new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
