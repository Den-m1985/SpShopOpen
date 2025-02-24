package ru.spshop.projects.ural_toys.find_articul.interfases;

import ru.spshop.projects.compare2files.modifyPrice.ReportData;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;

import java.util.List;

public interface IterateCsv {
    ReportData iteratorCsv(List<StructureCSV> data, String priceXls, String articularEXL);

}
