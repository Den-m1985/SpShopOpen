package ru.spshop.projects.projects_services.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelCellWrite {

    public void cellWrite(Row row, String itemString, int cellLast) {
        /*
        to do
        redo line:  cellLast + 5
         */
        Cell cell = row.getCell(cellLast + 5); // get cell
        if (cell == null) { //create cell if it null
            cell = row.createCell(cellLast);
        }
        cell.setCellValue(itemString);  // write data
    }

}
