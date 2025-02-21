package ru.spshop.projects.csvFilter;

import ru.spshop.projects.csvFilter.csv.StructureCSV;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CsvFilter {

    List<StructureCSV> csvFilter(InputStream fileInputStream) throws IOException;

    List<String[]> getError();

}
