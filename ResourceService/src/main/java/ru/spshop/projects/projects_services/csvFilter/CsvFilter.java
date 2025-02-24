package ru.spshop.projects.projects_services.csvFilter;

import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CsvFilter {

    List<StructureCSV> csvFilter(InputStream fileInputStream) throws IOException;

    List<String[]> getError();

}
