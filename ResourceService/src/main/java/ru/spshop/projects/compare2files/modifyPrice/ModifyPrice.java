package ru.spshop.projects.compare2files.modifyPrice;

import org.apache.poi.ss.usermodel.*;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;
import ru.spshop.projects.projects_services.excel.AddValueToRowImpl;

import java.util.*;

public class ModifyPrice {
    private final int numberSheet;
    private List<StructureCSV> data;
    private final Workbook workbook;
    private Sheet sheet;
    private final CellStyle yellowCellStyle;
    private final FindHeaderImpl findHeader;
    private final int offset = 2; // отступ после последнего столбца


    public ModifyPrice(List<StructureCSV> data, Workbook workbook, int numberSheet) {
        this.data = data;
        this.workbook = workbook;
        this.numberSheet = numberSheet;

        // Create a style for filling a cell with yellow color
        yellowCellStyle = workbook.createCellStyle();
        yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        findHeader = new FindHeaderImpl(workbook, numberSheet);

        sheet = workbook.getSheetAt(numberSheet);
    }


    public Workbook findCellEXEL() {
        //Row  строка      Cell столб
        //fillHeaderReport(sheet.getRow(findHeader.getHeaderRowIndex()));
        new FillHeader(sheet.getRow(findHeader.getHeaderRowIndex()), findHeader, offset);

        for (int i = findHeader.getHeaderRowIndex() + 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cellName = row.getCell(findHeader.getCellHeaderName());
                String nameEXL = cellRead(cellName);

                Cell cellSum = row.getCell(findHeader.getCellHeaderSum());
                if (nameEXL != null && isCellSum(cellSum)) {   //  если в ячейке есть цифра с суммой
                    // сравниваем строки
                    ReportData resultCompared = new ComparePriceWithCsv(data, nameEXL).compareWithCSV();
                    if (resultCompared != null) {
                        //addToSheet(row, resultCompared);
                        new AddValueToRowImpl().addValueToRow(row, resultCompared, findHeader, offset);
                    } else {
                        setCellColor(row, offset);  // закрашиваем ячейку
                    }
                }
            }
        }
        return workbook;
    }


    // закрашиваем пустую ячейку
    public void setCellColor(Row row, int offset) {
        int spaceProcentage = findHeader.getCellLast() + offset;
        Cell cell = row.getCell(spaceProcentage); // получаем  ячейку
        // если ячейка пустая, создаем ее
        if (cell == null) {
            cell = row.createCell(spaceProcentage);
        }
        cell.setCellStyle(yellowCellStyle);  // Применяем стиль к ячейке
    }


    private String cellRead(Cell cell) {
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


    private boolean isCellSum(Cell cell) {
        if (cell == null) {
            return false;
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            return true;
        }
        return false;
    }


    public List<StructureCSV> getNotUseArticle() {
        return data;
    }

}
