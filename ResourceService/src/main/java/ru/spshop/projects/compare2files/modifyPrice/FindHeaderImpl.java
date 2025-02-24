package ru.spshop.projects.compare2files.modifyPrice;

import org.apache.poi.ss.usermodel.*;
import ru.spshop.projects.projects_services.excel.ExcelCellRead;
import ru.spshop.projects.project_interfaces.findHeader.FindHeader;

public class FindHeaderImpl implements FindHeader {
    private final String headerName = "Товары (работы, услуги)";
    private final String headerName2 = "Товар";
    private final String headerSum = "Сумма";
    private int cellHeaderName;
    private int cellHeaderSum;
    private int cellLast;
    private int headerRowIndex;
    private boolean findRowWithHeader;
    private final ExcelCellRead excelCellRead;


    public FindHeaderImpl(Workbook workbook, int numberSheet) {
        excelCellRead = new ExcelCellRead();

        findHeaderRow(workbook, numberSheet);
    }

    @Override
    public void findHeaderRow(Workbook workbook, int numberSheet) {
        Sheet sheet = workbook.getSheetAt(numberSheet);
        for (Row row : sheet) {
            findNameHeader(row);
            if (findRowWithHeader) {
                break;
            }
        }
    }


    private void findNameHeader(Row row) {
        for (Cell cell : row) {
            String tempEXL = excelCellRead.cellRead(cell);
            if (tempEXL != null) {
                String str = tempEXL.trim();
                // поменять на массив и запрашивать - есть ли такая строка в массиве?
                if (str.equals(headerName) || str.equals(headerName2)) {
                    cellHeaderName = cell.getColumnIndex();
                    headerRowIndex = row.getRowNum();
                    cellLast = row.getLastCellNum();
                    findRowWithHeader = true;
                }
            }
            if (tempEXL != null) {
                String str = tempEXL.trim();
                if (findRowWithHeader && str.equals(headerSum)) {
                    cellHeaderSum = cell.getColumnIndex();
                    return;
                }
            }
        }
    }


    public int getCellHeaderName() {
        return cellHeaderName;
    }

    public int getCellHeaderSum() {
        return cellHeaderSum;
    }

    @Override
    public int getCellLast() {
        return cellLast;
    }

    public int getHeaderRowIndex() {
        return headerRowIndex;
    }

}
