package ru.spshop.projects.projects_services.csvFilter.csv;

import java.util.ArrayList;
import java.util.List;

public class OnlyGoods {
    private List<String[]> reportCSV;
    private List<StructureCSV> dataWithItem;
    int nameGoods = 0;
    int articular = 1;
    int cellPrice = 2;
    int cellItem = 3;

    int price;
    int item;


    public List<StructureCSV> findOnlyGoods(List<String[]> rows) {
        reportCSV = new ArrayList<>();
        dataWithItem = new ArrayList<>();
        for (String[] row : rows) {
            iteratorRow(row);
        }
        return dataWithItem;
    }


    private void iteratorRow(String[] row) {
        // устанавливаем значения для полей
        //setRowIndex(row);

        // Если в ячейке price и item число, то эту строку добавляем для дальнейшей работы.
        boolean rowCellItem = isFigure(row[cellItem]);
        boolean rowCellPrice = isFigure(row[cellPrice]);
        if (rowCellItem && rowCellPrice) {
            price = floatToInt(row[cellPrice]);
            item = Integer.parseInt(row[cellItem]);
            addGoodsToData(row);
        }

    }

    private void setRowIndex(String[] row) {
        if (row.length != cellItem+1) {
            nameGoods = 0;
            articular = 0;
            cellPrice = 0;
            cellItem = 0;
        }
        String nameCSV = "Название";
        String artuculCSV = "Артикул";
        String priceCSV = "Цена";
        String itemCSV = "Количество";
        for (int i = 0; i < row.length; i++) {
            String str = row[i];
            if (str.equals(nameCSV)) {
                nameGoods = i;
                articular = 0;
                cellPrice = 0;
                cellItem = 0;
            } else if (str.equals(artuculCSV)) {
                articular = i;
            } else if (str.equals(priceCSV)) {
                cellPrice = i;
            } else if (str.equals(itemCSV)) {
                cellItem = i;
            }
        }
    }


    private void addGoodsToData(String[] row) {
        if (articular == 0)
            dataWithItem.add(new StructureCSV(row[nameGoods], price, item));
        else
            dataWithItem.add(new StructureCSV(row[nameGoods], row[articular], price, item));
    }


    private boolean isFigure(String str) {
        if (str == null) {
            return false;
        }
        try {
            floatToInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private int floatToInt(String str) {
        if (str.contains(",")) {
            String str2 = str.replace(",", ".");
            return (int) Float.parseFloat(str2);
        }
        return (int) Float.parseFloat(str);
    }


    public List<String[]> reportCSV() {
        return reportCSV;
    }

}
