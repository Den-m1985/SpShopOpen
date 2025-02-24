package ru.spshop.projects.ural_toys.find_articul;

import ru.spshop.projects.compare2files.modifyPrice.ReportData;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;
import ru.spshop.projects.projects_services.report_data.ColorExcelCell;
import ru.spshop.projects.ural_toys.find_articul.check.CheckArticular;
import ru.spshop.projects.ural_toys.find_articul.check.CheckPrice;
import ru.spshop.projects.ural_toys.find_articul.interfases.IterateCsv;

import java.util.List;

public class IteratorCsvImpl implements IterateCsv {
    private CheckArticular checkArticular;

    @Override
    public ReportData iteratorCsv(List<StructureCSV> data, String priceXls, String articularEXL) {
        for (StructureCSV csv : data) {
            String articularCSV = csv.getArtucul();  // артикул с csv
            String itemCsv = "";
            boolean checkArticXLSX_CSV = checkArticular.checkArticular(articularEXL, articularCSV);

            if (checkArticXLSX_CSV) {
                int priceCSV = csv.getPrice();  // price с csv

                boolean checkPriceXLSX = new CheckPrice().checkPrice(priceXls, priceCSV);

                String matchPercent = "";
                String priceCSv = String.valueOf(csv.getPrice());
                itemCsv = String.valueOf(csv.getItem()); // получаем кол-во, переводим в строку
                if (checkPriceXLSX) {
                    data.remove(csv);
                    return new ReportData(matchPercent, priceCSv, itemCsv, csv.getName());
                }else {
                    /*
                    add red color
                     */
                    return new ReportData(matchPercent, priceCSv, itemCsv, csv.getName());
                }
            }
        }
        return null;
    }

    public IteratorCsvImpl() {
        this.checkArticular = new CheckArticular();
    }

}
