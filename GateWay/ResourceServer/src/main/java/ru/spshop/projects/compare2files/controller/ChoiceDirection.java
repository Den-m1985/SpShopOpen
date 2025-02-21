package ru.spshop.projects.compare2files.controller;

import org.apache.poi.ss.usermodel.Workbook;
import ru.spshop.projects.compare2files.newExel.readExel.XlsxReader;
import ru.spshop.projects.compare2files.oldExel.XlsReader;

import java.io.InputStream;

public class ChoiceDirection {
    private final String pathExel;
    private String extension;
    private String oldExel = "xls";
    private String newExel = "xlsx";


    public ChoiceDirection(String pathXLS) {
        this.pathExel = pathXLS;
        extension = separateString(pathExel);
    }

    private String separateString(String pathFile) {
        if (pathFile != null) {
            return pathFile.substring(pathFile.lastIndexOf('.') + 1);
        } else {
            throw new RuntimeException("Не смог извлечь путь из имени файла. Полное имя: " + pathFile);
        }
    }

    public Workbook choiceOldNewExel(InputStream file2InputStream) {
        if (extension.equals(oldExel)) {
            return new XlsReader().xlsRead(file2InputStream);
        } else if (extension.equals(newExel)) {
            return new XlsxReader().xlsxRead(file2InputStream);
        } else {
            throw new RuntimeException("Не удалось определить расширение: " + extension);
        }
    }

    public String getExtension() {
        return extension;
    }

}
