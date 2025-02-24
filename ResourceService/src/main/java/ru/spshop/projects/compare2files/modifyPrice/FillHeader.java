package ru.spshop.projects.compare2files.modifyPrice;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import ru.spshop.projects.project_interfaces.findHeader.FindHeader;

import java.lang.reflect.Field;

public class FillHeader {

    public FillHeader(Row row, FindHeader findHeader, int offset) {
        String[] nameReport = {"Совпадение в %", "Цена", "Кол-во", "Наименование"};
        Field[] fields = ReportData.class.getDeclaredFields(); // получаем все поля класса
        for (int i = 0; i < fields.length; i++) {
            int cellIndex = findHeader.getCellLast() + offset + i;  // отступ от последнего столба
            Cell cellReport = row.getCell(cellIndex); // получаем ячейку
            if (cellReport == null) { // если ячейка пустая, создаем ее
                cellReport = row.createCell(cellIndex);
            }
            cellReport.setCellValue(nameReport[i]);
        }
    }

}
