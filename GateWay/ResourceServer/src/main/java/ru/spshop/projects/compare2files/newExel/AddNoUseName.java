package ru.spshop.projects.compare2files.newExel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.spshop.projects.csvFilter.csv.StructureCSV;

import java.util.List;

public class AddNoUseName {

    public AddNoUseName(Workbook workbook, List<StructureCSV> noFindData, int numberSheet, String pathCSV, String pathXLS) {
        Sheet sheet = workbook.getSheetAt(numberSheet);
        int lastRow = sheet.getLastRowNum();
        int startReport = lastRow + 5;

        Row row1 = sheet.createRow(startReport);
        row1.createCell(0).setCellValue("Отчет. Сравнивались 2 файла:");
        Row row2 = sheet.createRow(startReport + 1);
        row2.createCell(0).setCellValue(pathCSV);
        Row row3 = sheet.createRow(startReport + 2);
        row3.createCell(0).setCellValue(pathXLS);

        // create topic
        Row rowTop = sheet.createRow(startReport + 4);
        rowTop.createCell(0).setCellValue("Не найденные наименование из csv");

        for (int i = 0; i < noFindData.size(); i++) {
            Row row5 = sheet.createRow(i + startReport + 5);
            String name = noFindData.get(i).getName();
            row5.createCell(5).setCellValue(name);
        }
    }

}
