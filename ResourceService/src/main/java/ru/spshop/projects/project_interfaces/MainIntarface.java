package ru.spshop.projects.project_interfaces;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;

public interface MainIntarface {
    Workbook getWorkbook(String filename1, InputStream file1InputStream,
                         String filename2, InputStream file2InputStream) throws IOException;

    String getExtension();
}
