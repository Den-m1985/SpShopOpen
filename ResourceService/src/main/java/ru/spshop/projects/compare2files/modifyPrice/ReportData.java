package ru.spshop.projects.compare2files.modifyPrice;

import ru.spshop.projects.projects_services.report_data.ColorExcelCell;

import java.lang.reflect.Field;

public class ReportData {
    public final String matchPercent;
    public final String priceCSV;
    public final String itemCSV;
    public final String nameCSV;

    public ReportData(String matchPercent, String priceCSV, String itemCSV, String nameCSV) {
        this.matchPercent = matchPercent;
        this.priceCSV = priceCSV;
        this.itemCSV = itemCSV;
        this.nameCSV = nameCSV;
    }

    public int countFields(){
        Field[] fields = ReportData.class.getDeclaredFields();
        return fields.length;
    }

    public void setPriceColor(ColorExcelCell colorExcelCell){

    }

    public String getMatchPercent() {
        return matchPercent;
    }

    public String getPriceCSV() {
        return priceCSV;
    }

    public String getItemCSV() {
        return itemCSV;
    }

    public String getNameCSV() {
        return nameCSV;
    }

}
