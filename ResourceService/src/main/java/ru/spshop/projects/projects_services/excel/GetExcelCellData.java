package ru.spshop.projects.projects_services.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GetExcelCellData {

    public String getCellData(Row row, int cellNumber) {
        Cell cell = row.getCell(cellNumber);
        return new ExcelCellRead().cellRead(cell);
    }

}
