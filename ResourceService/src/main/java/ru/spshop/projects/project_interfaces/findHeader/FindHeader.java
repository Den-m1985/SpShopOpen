package ru.spshop.projects.project_interfaces.findHeader;

import org.apache.poi.ss.usermodel.Workbook;

public interface FindHeader {

    void findHeaderRow(Workbook workbook, int numberSheet);

    int getCellLast();

}
