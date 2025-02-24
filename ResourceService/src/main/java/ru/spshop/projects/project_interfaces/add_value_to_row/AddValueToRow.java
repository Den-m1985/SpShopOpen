package ru.spshop.projects.project_interfaces.add_value_to_row;

import org.apache.poi.ss.usermodel.Row;
import ru.spshop.projects.compare2files.modifyPrice.ReportData;
import ru.spshop.projects.project_interfaces.findHeader.FindHeader;

public interface AddValueToRow {

    void addValueToRow(Row row, ReportData reportData, FindHeader findHeader, int offset);

}
