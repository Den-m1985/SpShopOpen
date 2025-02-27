package ru.spshop.projects.compare2files.modifyPrice;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HeaderPositionFinderTest {

    @Test
    public void testHeaderPositionFinder() throws IOException {
        Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet");
            Row headerRow = sheet.createRow(5);
            headerRow.createCell(0).setCellValue("№");
            headerRow.createCell(1).setCellValue("№");
            headerRow.createCell(2).setCellValue("Код");
            headerRow.createCell(3).setCellValue("");
            headerRow.createCell(4).setCellValue("Товары (работы, услуги)");
            headerRow.createCell(5).setCellValue("");
            headerRow.createCell(6).setCellValue("Количество");
            headerRow.createCell(7).setCellValue("Цена");
            headerRow.createCell(8).setCellValue("");
            headerRow.createCell(9).setCellValue("Сумма без скидки");
            headerRow.createCell(10).setCellValue("Скидка");
            headerRow.createCell(11).setCellValue("Ставка НДС");
            headerRow.createCell(12).setCellValue("");
            headerRow.createCell(13).setCellValue("");
            headerRow.createCell(14).setCellValue("Сумма");

        FindHeaderImpl headerPositionFinder = new FindHeaderImpl(workbook, 0);

        int expectedCellHeaderName = 4; // Индекс столбца "Товары (работы, услуги)"
        int expectedCellHeaderSum = 14;  // Индекс столбца "Сумма"
        int expectedCellLast = 15;      // Индекс последнего столбца + 1
        int expectedHeaderRowIndex = 5; // Индекс строки с заголовками

        workbook.close();

        Assertions.assertEquals(expectedCellHeaderName, headerPositionFinder.getCellHeaderName());
        Assertions.assertEquals(expectedCellHeaderSum, headerPositionFinder.getCellHeaderSum());
        Assertions.assertEquals(expectedCellLast, headerPositionFinder.getCellLast());
        Assertions.assertEquals(expectedHeaderRowIndex, headerPositionFinder.getHeaderRowIndex());
    }
}
