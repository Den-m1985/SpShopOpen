package ru.spshop.projects.ural_toys;

import org.apache.poi.ss.usermodel.*;
import ru.spshop.projects.compare2files.modifyPrice.ReportData;
import ru.spshop.projects.project_interfaces.add_value_to_row.AddValueToRow;
import ru.spshop.projects.project_interfaces.findHeader.FindHeader;
import ru.spshop.projects.projects_services.excel.SetCellColor;

import java.lang.reflect.Field;

public class AddValueToRowImpl2 implements AddValueToRow {
    private final CellStyle cellStyle;
    private final SetCellColor setCellColor;

    @Override
    public void addValueToRow(Row row, ReportData reportData, FindHeader findHeader, int offset) {
        // получаем все поля класса
        Field[] fields = ReportData.class.getDeclaredFields();
        int length = reportData.countFields();
        for (int i = 0; i < length; i++) {
            int cellIndex = findHeader.getCellLast() + offset + i;
            try {
                // Получаем значение поля с использованием рефлексии
                Object value = fields[i].get(reportData);
                // Устанавливаем значение в ячейку
                if (value != null) {
                    //cellStyle.setFillForegroundColor(IndexedColors.AQUA.index);
                    setCellColor.setCellWithColor(row, cellIndex, value.toString(), cellStyle);
                }
            } catch (IllegalAccessException e) {
                // Обработка ошибок доступа к полю
                e.printStackTrace();
            }
        }
    }

    public AddValueToRowImpl2(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
        this.setCellColor = new SetCellColor();
    }

}
