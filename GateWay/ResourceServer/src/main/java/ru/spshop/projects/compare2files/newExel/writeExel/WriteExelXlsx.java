package ru.spshop.projects.compare2files.newExel.writeExel;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExelXlsx {

    public WriteExelXlsx(Workbook workbook, String fileNamePrice) {
        try (FileOutputStream outputStream = new FileOutputStream(fileNamePrice)) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
