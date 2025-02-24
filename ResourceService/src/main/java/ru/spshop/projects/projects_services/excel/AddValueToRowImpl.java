package ru.spshop.projects.projects_services.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import ru.spshop.projects.compare2files.modifyPrice.ReportData;
import ru.spshop.projects.project_interfaces.add_value_to_row.AddValueToRow;
import ru.spshop.projects.project_interfaces.findHeader.FindHeader;

import java.lang.reflect.Field;

public class AddValueToRowImpl implements AddValueToRow {
    private final  SetCellColor setCellColor;

    @Override
    public void addValueToRow(Row row, ReportData reportData, FindHeader findHeader, int offset) {
        // получаем все поля класса
        Field[] fields = ReportData.class.getDeclaredFields();
        int length = reportData.countFields();
        for (int i = 0; i < length; i++) {
            int cellIndex = findHeader.getCellLast() + offset + i;
            Cell cellReport = row.getCell(cellIndex); // получаем ячейку
            if (cellReport == null) { // если ячейка пустая, создаем ее
                cellReport = row.createCell(cellIndex);
            }
            try {
                // Получаем значение поля с использованием рефлексии
                Object value = fields[i].get(reportData);
                // Устанавливаем значение в ячейку
                if (value != null) {
                    //setCellColor.setCellColor(row, cellReport, cellStyle);
                    cellReport.setCellValue(value.toString());
                }
            } catch (IllegalAccessException e) {
                // Обработка ошибок доступа к полю
                e.printStackTrace();
            }
        }
    }

    public AddValueToRowImpl() {
        this.setCellColor = new SetCellColor();
    }

}
