package ru.spshop.projects.compare2files.newExel;

import org.apache.poi.ss.usermodel.*;
import ru.spshop.projects.csvFilter.csv.StructureCSV;

import java.util.*;

public class AddToExelCompareCell {
    private final String fileNamePrice;
    private List<StructureCSV> data;
    private Integer cellNameHeader = null;
    private Integer cellSumHeader;
    private final String headerName = "Товары (работы, услуги)";
    private final String headerName2 = "Товар";
    private final String headerSum = "Сумма";
    private final int agreePercent = 40;
    private int lastRow;
    private CellStyle yellowCellStyle;


    public AddToExelCompareCell(String fileNamePrice, List<StructureCSV> data) {
        this.fileNamePrice = fileNamePrice;
        this.data = data;
    }


    public Workbook findCellEXEL(Workbook workbook, int numberSheet) {
        //Row  строка      Cell столб
        // Создаем стиль для закраски ячейки желтым цветом
        yellowCellStyle = workbook.createCellStyle();
        yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Sheet sheet = workbook.getSheetAt(numberSheet);

        for (Row row : sheet) {
            findNameHeader(row);  // find name goods header index
            String nameEXL = null;
            if (cellNameHeader != null) { // если заголовок с именем найден
                Cell cellName = row.getCell(cellNameHeader);
                nameEXL = cellRead(cellName);
            }
            if (cellSumHeader != null) {
                Cell cellSum = row.getCell(cellSumHeader);
                if (nameEXL != null && isCellSum(cellSum)) {   // если заголовок с суммой найден
                    String[] resultCompared = compareWithCSV(nameEXL);  // сравниваем строки
                    if (resultCompared != null) {
                        addToSheet(row, resultCompared);  // добавляем в ячейки если == условиям
                    } else {
                        setCellColor(row);  // закрашиваем ячейку
                    }
                }
            }
        }
        return workbook;
    }


    private String[] compareWithCSV(String nameEXL) {
        String[] tokens2 = tokenize(nameEXL);

        for (StructureCSV csv : data) {
            String nameCSV = csv.getName();
            String[] tokens1 = tokenize(nameCSV);
            int matchPercent = calculateMatchPercentage(tokens1, tokens2);
            if (matchPercent >= agreePercent) {
                String[] str = {String.valueOf(matchPercent), nameCSV};
                data.remove(csv);
                return str;
            }
        }
        return null;
    }


    private void addToSheet(Row row, String[] strArray) {
         int spaceProcentage = lastRow + 2;
         int spaceNameCSV = lastRow + 3;

        Cell cellProcentage = row.getCell(spaceProcentage); // получаем  ячейку
        Cell cellNameCSV = row.getCell(spaceNameCSV);

        if (cellProcentage == null) { // если ячейка пустая, создаем ее
            cellProcentage = row.createCell(spaceProcentage);
        }
        if (cellNameCSV == null) {
            cellNameCSV = row.createCell(spaceNameCSV);
        }
        //добавляем наши проценты в workbook
        cellProcentage.setCellValue(strArray[0]);
        cellNameCSV.setCellValue(strArray[strArray.length - 1]);
    }


    // закрашиваем пустую ячейку
    public void setCellColor(Row row) {
        int spaceProcentage = lastRow + 2;
        Cell cell = row.getCell(spaceProcentage); // получаем  ячейку
        // если ячейка пустая, создаем ее
        if (cell == null) {
            cell = row.createCell(spaceProcentage);
        }
        cell.setCellStyle(yellowCellStyle);  // Применяем стиль к ячейке
    }


    private int calculateMatchPercentage(String[] tokens1, String[] tokens2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(tokens1));
        Set<String> set2 = new HashSet<>(Arrays.asList(tokens2));

        /*
         * is used to find the intersection of two sets (set1 and set2).
         * It creates a new HashSet called intersection and initializes
         * it with the elements of set1. Then, it uses the retainAll method
         * to retain only the elements that are common between intersection and set2.
         * After this operation, intersection contains the elements that are
         * present in both set1 and set2.
         *
         * In the context of the previous code I provided, this is used to find
         * the common substrings between the two input strings str1 and str2.
         * The Jaccard similarity coefficient is calculated by dividing the size
         * of the intersection of the sets by the size of the union of the sets,
         * which gives you the proportion of common elements to the total unique
         * elements in both strings. This is a common way to measure
         * the similarity between sets of elements.
         */
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        /*
         *is used to find the union of two sets (set1 and set2).
         * It creates a new HashSet called union and initializes
         * it with the elements of set1. Then, it uses the addAll
         * method to add all the elements from set2 to union.
         * After this operation, union contains all the unique
         * elements from both set1 and set2.
         */
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        double jaccardSimilarity = (double) intersection.size() / union.size();
        return (int) (jaccardSimilarity * 100.0);
    }


    private String[] tokenize(String input) {
        String str = input.trim();
        String[] strSplit = str.split(" ");
        for (int i = 0; i < strSplit.length; i++) {
            strSplit[i] = deleteQuote(strSplit[i]);
        }
        return strSplit;
    }


    private String deleteQuote(String str) {
        return str.replace("\"", "");
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


    private void findNameHeader(Row row) {
        boolean a = false;
        for (Cell cell : row) {
            String tempEXL = cellRead(cell);
            if (tempEXL != null) {
                if (tempEXL.equals(headerName) || tempEXL.equals(headerName2)) {
                    cellNameHeader = cell.getColumnIndex();
                    lastRow = row.getLastCellNum();
                    a = true;
                }
            }
            if (a && tempEXL != null && tempEXL.equals(headerSum)) {
                cellSumHeader = cell.getColumnIndex();
                a = false;
            }
        }
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