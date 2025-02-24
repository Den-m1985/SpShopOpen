package ru.spshop.projects.ural_toys.find_articul;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spshop.projects.compare2files.modifyPrice.FillHeader;
import ru.spshop.projects.compare2files.modifyPrice.ReportData;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;
import ru.spshop.projects.projects_services.excel.GetExcelCellData;
import ru.spshop.projects.projects_services.excel.SetCellColor;
import ru.spshop.projects.ural_toys.AddValueToRowImpl2;
import ru.spshop.projects.ural_toys.ural_toys_service.FindHeaderImpl;
import ru.spshop.projects.ural_toys.find_articul.interfases.IterateExcel;

import java.util.List;

public class IterateXlsxImpl implements IterateExcel {
    private final Logger log = LoggerFactory.getLogger(IterateXlsxImpl.class);
    private List<StructureCSV> data;
    private Workbook workbook;
    private final FindHeaderImpl findHeader;
    private GetExcelCellData getExcelCellData;
    private final CellStyle cellStyle;

    private final int numberSheet = 0;  // номер страницы в файле.
    private final int offset = 2; // отступ после последнего столбца

    @Override
    public Workbook iterateEXEL() {
        //log.info("findHeader.getCellArticular() " + findHeader.getCellArticular());
        // log.info("findHeader.getCellHeaderName() " + findHeader.getCellHeaderName());
        //log.info("findHeader.getCellHeaderSum() " + findHeader.getCellHeaderSum());
        //Row  строка
        //Cell столб
        Sheet sheet = workbook.getSheetAt(numberSheet);

        new FillHeader(sheet.getRow(findHeader.getHeaderRowIndex()), findHeader, offset);

        for (int i = findHeader.getHeaderRowIndex() + 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String articularEXL = getExcelCellData.getCellData(row, findHeader.getCellArticular());
                if (articularEXL != null) {
                    String cellPriceXlsDiscount = getExcelCellData.getCellData(row,findHeader.getCellHeaderSum());

                    ReportData reportData = new IteratorCsvImpl().iteratorCsv(data, cellPriceXlsDiscount, articularEXL);

                    if (reportData !=null){
                        new AddValueToRowImpl2(cellStyle).addValueToRow(row, reportData, findHeader, offset);
                    }else {
                        new SetCellColor().setCellColor(row, findHeader.getCellLast() + offset, cellStyle);
                    }
                }
            }
        }
        return workbook;
    }


    public IterateXlsxImpl(List<StructureCSV> data, Workbook workbook) {
        this.data = data;
        this.workbook = workbook;
        getExcelCellData = new GetExcelCellData();
        findHeader = new FindHeaderImpl(workbook, numberSheet);

        // Create a style for filling a cell with yellow color
        cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    }

}
