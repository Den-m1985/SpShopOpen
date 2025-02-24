package ru.spshop.projects.projects_services.excel.oldExel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteOldExel {

    public WriteOldExel(HSSFWorkbook workbook, String fileNamePrice) {
        try (FileOutputStream outputStream = new FileOutputStream(fileNamePrice)) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
