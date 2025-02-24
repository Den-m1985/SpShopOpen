package ru.spshop.projects.projects_services.excel;

import org.apache.poi.ss.usermodel.*;

public class SetCellColor {

    public void setCellColor(Row row, int cellIndex, CellStyle cellStyle) {
        Cell cell = row.getCell(cellIndex); // получаем  ячейку
        // если ячейка пустая, создаем ее
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        //cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        cell.setCellStyle(cellStyle);  // Применяем стиль к ячейке
    }

    public void setCellWithColor(Row row, int cellIndex, String value, CellStyle cellStyle) {
        Cell cell = row.getCell(cellIndex); // получаем  ячейку
        // если ячейка пустая, создаем ее
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        //cell.setCellStyle(cellStyle);  // Применяем стиль к ячейке
        cell.setCellValue(value);
    }

}
