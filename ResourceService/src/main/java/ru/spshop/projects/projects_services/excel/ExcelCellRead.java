package ru.spshop.projects.projects_services.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelCellRead {

    // Получение содержимого ячейки в зависимости от типа данных
    public String cellRead(Cell cell) {
        if (cell != null) {
            CellType cellType = cell.getCellType();
            if (cellType == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cellType == CellType.NUMERIC) {
                double numericValue = cell.getNumericCellValue();
                int intValue = (int) numericValue;  // Преобразование к целому числу
                return String.valueOf(intValue);
            } else if (cellType == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            }
        }
        return null;
    }

}
